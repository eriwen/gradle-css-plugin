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
        file.withWriter { w ->
            w << addSpacesToOperatorsInCalcFunc(text)
        }
    }

    /**
     * Adds spaces to the operators for CSS calc functions
     * @param string Input string
     * @return Result
     */
    private String addSpacesToOperatorsInCalcFunc(String string) {
        StringBuffer sb = new StringBuffer()
        Pattern p = ~/calc\([^\)]*\)/
        Matcher m = p.matcher(string)
        while (m.find()) {
            String s = m.group()

            s = s.replaceAll("(?<=[%|px|em|rem|vw|\\d]+)\\+", " + ")
            s = s.replaceAll("(?<=[%|px|em|rem|vw|\\d]+)\\-", " - ")
            s = s.replaceAll("(?<=[%|px|em|rem|vw|\\d]+)\\*", " * ")
            s = s.replaceAll("(?<=[%|px|em|rem|vw|\\d]+)\\/", " / ")

            m.appendReplacement(sb, s)
        }
        m.appendTail(sb)
        sb.toString()
    }
}
