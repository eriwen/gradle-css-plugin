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
package com.eriwen.gradle.css

import org.gradle.api.Project
import org.gradle.api.Plugin
import com.eriwen.gradle.css.tasks.*

class CssPlugin implements Plugin<Project> {

    void apply(final Project project) {
        project.extensions.create(CssExtension.NAME, CssExtension, project)
        project.extensions.create(CssLintExtension.NAME, CssLintExtension)
        project.extensions.create(YuiCompressorExtension.NAME, YuiCompressorExtension)
        project.extensions.create(LessExtension.NAME, LessExtension)

        configureDependencies(project)
        applyTasks(project)
    }

    void applyTasks(final Project project) {
        project.task('minifyCss', type: MinifyCssTask, group: 'Build', description: 'Minify CSS using YUI Minifier') {}
        project.task('combineCss', type: CombineCssTask, group: 'Build', description: 'Combine many CSS files into one') {}
        project.task('gzipCss', type: GzipCssTask, group: 'Build', description: 'GZip a given CSS file') {}
        project.task('csslint', type: CssLintTask, group: 'Verification', description: 'Analyze CSS sources with CSS Lint') {}
        project.task('less', type: LessTask, group: 'Build', description: 'Compiles LESS files into CSS')
    }

    void configureDependencies(final Project project) {
        project.configurations {
            rhino
        }
        project.repositories {
            mavenCentral()
        }
        project.dependencies {
            rhino 'org.mozilla:rhino:1.7R3'
        }
    }
}
