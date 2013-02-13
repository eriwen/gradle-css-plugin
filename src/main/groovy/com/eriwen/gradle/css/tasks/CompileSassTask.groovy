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
/*package com.eriwen.gradle.css.tasks

import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction
import javax.script.ScriptEngineManager
import groovy.io.FileVisitResult
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.Input

class CompileSassTask extends SourceTask {
    @InputDirectory File sourceDir
    @OutputDirectory File destDir
    @Input File cacheLocation

    String driverScript = '''
        require 'rubygems'
        require 'sass'

        $mappings.each do |src, dst|
            engine = Sass::Engine.for_file(src, {:cache_location => $cacheLocation})
            css = engine.render
            File.open(dst, 'w') {|f| f.write(css) }
            puts "#{src} -> #{dst}"
        end
    '''

    @TaskAction
    def run() {
        def mappings = new TreeMap()

        sourceDir.traverse(preDir: {if (it.isHidden()) return FileVisitResult.SKIP_SUBTREE}) { inputFile ->
            if (inputFile.isFile() && (inputFile.name.endsWith('.sass') || inputFile.name.endsWith('.scss'))) {
                def outputFile = new File(destDir, relativePath(sourceDir, inputFile) + '.css')
                outputFile.parentFile.mkdirs()
                mappings.put(inputFile.path, outputFile.path)
            }
        }

        def engine = new ScriptEngineManager().getEngineByName("jruby")
        engine.put("cacheLocation", cacheLocation.path)
        engine.put("mappings", mappings)
        engine.eval(driverScript)
    }

    def relativePath(File root, File descendant) {
        def rootPath = root.absolutePath
        def descendantPath = descendant.absolutePath
        assert descendantPath.startsWith(rootPath)
        return descendantPath.substring(rootPath.length() + 1)
    }
}        */
