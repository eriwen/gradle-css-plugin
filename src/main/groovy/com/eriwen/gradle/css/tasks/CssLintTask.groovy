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

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import com.eriwen.gradle.css.ResourceUtil
import com.eriwen.gradle.css.RhinoExec
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.OutputFile

class CssLintTask extends SourceTask {
    private static final String CSSLINT_PATH = 'csslint-rhino.js'
    private static final String TMP_DIR = "tmp${File.separator}css"
    private static final ResourceUtil RESOURCE_UTIL = new ResourceUtil()
    private final RhinoExec rhino = new RhinoExec(project)

    @OutputFile def dest
    @Input String format = 'compact'
    @Input Iterable<String> errors = []
    @Input Iterable<String> warnings = ['important','adjoining-classes','known-properties','box-sizing','box-model','outline-none','duplicate-background-images','compatible-vendor-prefixes','display-property-grouping','qualified-headings','fallback-colors','duplicate-properties','empty-rules','errors','shorthand','ids','gradients','font-sizes','font-faces','floats','underscore-property-hack','overqualified-elements','import','regex-selectors','rules-count','star-property-hack','text-indent','unique-headings','universal-selector','unqualified-attributes','vendor-prefix','zero-units']

    File getDest() {
        project.file(dest)
    }

    @TaskAction
    def run() {
        final File csslintJsFile = RESOURCE_UTIL.extractFileToDirectory(
                new File(project.buildDir, TMP_DIR), CSSLINT_PATH)
        final List<String> args = [csslintJsFile.canonicalPath]
        args.addAll(source.files.collect { it.canonicalPath })
        args.add("--format=${format}")
        args.add("--errors=${errors.join(',')}")
        args.add("--warnings=${warnings.join(',')}")
        rhino.execute(args, [out: new FileOutputStream(dest as File)])
    }
}
