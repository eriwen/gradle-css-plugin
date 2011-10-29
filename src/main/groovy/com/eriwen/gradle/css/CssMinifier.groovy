package com.eriwen.gradle.css

import com.yahoo.platform.yui.compressor.CssCompressor

/**
 * Utility class that houses minification logic.
 *
 * @author Eric Wendelin
 * @date 10/29/11
 */
class CssMinifier {
    private static final String CHARSET = 'UTF-8'

    /**
     * Given input and output files, minify input file and output to given output file
     * @param inputFile File to be minified
     * @param outputFile File for minified output
     */
    void minifyCssFile(final File inputFile, final File outputFile, final Integer lineBreakPos) {
        Reader reader = null
        CssCompressor compressor = null
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

        Writer writer = null
        try {
            writer = new OutputStreamWriter(new FileOutputStream(outputFile), CHARSET)
            compressor.compress(writer, lineBreakPos)
        } finally {
            if (writer != null) {
                writer.close()
            }
        }
    }
}
