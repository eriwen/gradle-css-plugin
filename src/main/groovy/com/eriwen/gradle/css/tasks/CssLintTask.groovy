/**
 * Copyright 2011 Eric Wendelin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.eriwen.gradle.css.tasks

import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask

class CssLintTask extends DefaultTask {
    private static final String CSSLINT_PATH = 'csslint-rhino.js'
    private static final String TMP_DIR = 'tmp/css'
    Iterable<String> options = []

    @TaskAction
    def run() {
        File csslintJsFile = loadCssLintJs()
        String outputPath = (getOutputs().files.files.toArray()[0] as File).canonicalPath
        ant.java(jar: project.configurations.rhino.asPath, fork: true, output: outputPath) {
            arg(value: csslintJsFile.canonicalPath)
            options.each {
                arg(value: it)
            }
            getInputs().files.files.each {
                arg(value: it.canonicalPath)
            }
        }
    }

    File loadCssLintJs() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CSSLINT_PATH)
        File tempDir = new File(project.buildDir, TMP_DIR)
        tempDir.mkdirs()
        File csslintJsFile = new File(tempDir, CSSLINT_PATH)
        if (!csslintJsFile.exists()) {
            csslintJsFile << inputStream
        }
        inputStream.close()
        return csslintJsFile
    }
}
