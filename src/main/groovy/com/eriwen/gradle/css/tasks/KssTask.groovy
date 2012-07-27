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

import org.gradle.api.tasks.TaskAction
import com.eriwen.gradle.css.ResourceUtil
import com.eriwen.gradle.css.RhinoExec
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.jruby.Main as JRuby
import javax.script.ScriptEngineManager
import javax.script.ScriptEngine

class KssTask extends SourceTask {
    @InputDirectory File sourceDir
    @OutputFile File dest

    File getSourceDir() {
        project.file(sourceDir)
    }

    File getDest() {
        project.file(dest)
    }

    @TaskAction
    def run() {
        String engineName = "jruby"
        ScriptEngine jRubyEngine = new ScriptEngineManager().getEngineByName(engineName)

        jRubyEngine.put("engine", engineName)

        def gemsDir = project.buildDir
        if (!new File("${gemsDir}/gems").exists()) {
            runJRuby("gem install -i ${gemsDir} --no-rdoc --no-ri kss")
        }

        jRubyEngine.eval("""
require 'rubygems'
require 'kss'
styleguide = Kss::Parser.new("${sourceDir.canonicalPath}")
puts styleguide.to_s
#puts styleguide.section('0.1.2').description
        """)
        // https://github.com/RobertFischer/gradle-plugins/blob/master/src/main/groovy/RunJRubyPlugin.groovy
        // http://tommy.chheng.com/2010/06/20/call-a-jruby-method-from-java/
    }

    def runJRuby(cmdArg) {
        Thread.currentThread().setContextClassLoader(JRuby.class.classLoader)
        println "Running JRuby: $cmdArg"
        JRuby.main("-S $cmdArg".split())
    }
}
