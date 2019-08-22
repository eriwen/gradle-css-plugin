/**
 * Copyright 2012 Eric Wendelin
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

import com.eriwen.gradle.css.CssMinifier
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

class MinifyCssTask extends SourceTask {
    private static final CssMinifier MINIFIER = new CssMinifier()

    @OutputFile def dest

    File getDest() {
        project.file(dest)
    }

    @TaskAction
    def run() {
        MINIFIER.minifyCssFile(source.singleFile, dest as File, project.yuicompressor.lineBreakPos, project.yuicompressor.charset)
    }
}
