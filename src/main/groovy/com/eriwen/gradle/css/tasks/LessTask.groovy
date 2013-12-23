/**
 * Copyright 2013 Joe Fitzgerald
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

import com.asual.lesscss.LessEngine
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

class LessTask extends SourceTask {
    @OutputDirectory def dest

    File getDest() {
        project.file(dest)
    }

    @TaskAction
    def run() {
        LessEngine engine = new LessEngine();
        logger.debug "Processing ${source.files.size()} files"

        source.visit { visitDetail ->
            if(visitDetail.directory){
                visitDetail.relativePath.getFile(getDest()).mkdir()
            } else {
                if(visitDetail.name.endsWith(".less")){
                    def relativePathToCss = visitDetail.relativePath.replaceLastName(visitDetail.name.replace(".less", ".css"))
                    compileLess(engine, visitDetail.file, relativePathToCss.getFile(getDest()))
                } else {
                    logger.debug("Copying non-less resource ${visitDetail.file.absolutePath} to ${getDest().absolutePath}")
                    visitDetail.copyTo(visitDetail.relativePath.getFile(getDest()))
                }
            }
        }
    }

    def compileLess(engine, src, target){
        logger.debug "Processing ${src.canonicalPath} to ${target.canonicalPath}"
        String output = engine.compile(src.absoluteFile)
        if (target.exists()) {
            target.delete()
        }
        String cleansedOutput = output.replace("\\n", System.getProperty("line.separator"))
        target << cleansedOutput
    }
}
