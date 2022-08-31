package com.eriwen.gradle.css

import com.yahoo.platform.yui.compressor.CssCompressor

import java.util.regex.Matcher
import java.util.regex.Pattern
/**
 * Utility class that houses minification logic.
 *
 * @author Eric Wendelin
 */
class CssMinifier {
    private static final String CHARSET = 'UTF-8'

    /**
     * Given input and output files, minify input file and output to given output file
     * @param inputFile File to be minified
     * @param outputFile File for minified output
     */
    void minifyCssFile(final File inputFile, final File outputFile, final Integer lineBreakPos) {
        Reader reader
        CssCompressor compressor
        try {
            reader = new InputStreamReader(new FileInputStream(inputFile), CHARSET)
            compressor = new CssCompressor(reader)
        } finally {
            // Close the input stream first, and then open the output stream,
            // in case the output file should override the input file.
            if (reader != null) {
                reader.close()
            }
        }

        Writer writer
        try {
            writer = new OutputStreamWriter(new FileOutputStream(outputFile), CHARSET)
            compressor.compress(writer, lineBreakPos)
        } finally {
            if (writer != null) {
                writer.close()
            }
        }

        fixCalcFunc(outputFile)
    }

    /**
     * Fix issues related to calc function that came after CSS compression
     * @param file File where function calc shall be fixed
     */
    private void fixCalcFunc(File file) {
        String text = file.text
        file.withWriter(CHARSET) { writer ->
            writer << addSpacesToOperatorsInCalcFunc(text)
        }
    }

    /**
     * Adds spaces to the operators for CSS calc functions
     * @param string Input string
     * @return Result
     */
    private String addSpacesToOperatorsInCalcFunc(String string) {
        StringBuffer stringBuffer = new StringBuffer()
        Pattern pattern = ~/calc\([^\)]*\)/
        Matcher matcher = pattern.matcher(string)
        while (matcher.find()) {
            String calcStr = matcher.group()

            calcStr = calcStr.replaceAll(~/(?<=[%|px|em|rem|vw|\d]+)\+/, " + ")
            calcStr = calcStr.replaceAll(~/(?<=[%|px|em|rem|vw|\d]+)-/ , " - ")
            calcStr = calcStr.replaceAll(~/(?<=[%|px|em|rem|vw|\d]+)\*/, " * ")
            calcStr = calcStr.replaceAll(~/(?<=[%|px|em|rem|vw|\d]+)\//, " / ")

            matcher.appendReplacement(stringBuffer, calcStr)
        }
        matcher.appendTail(stringBuffer)
        stringBuffer.toString()
    }
}
