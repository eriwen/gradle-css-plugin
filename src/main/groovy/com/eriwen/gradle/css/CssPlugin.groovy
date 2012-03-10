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

import org.gradle.api.logging.Logger
import org.gradle.api.Project
import org.gradle.api.Plugin

import com.eriwen.gradle.css.tasks.*
import com.eriwen.gradle.css.source.internal.InternalGradle

class CssPlugin implements Plugin<Project> {
    private Project project
    private Logger logger
    private CssPluginConvention cssPluginConvention

    void apply(final Project project) {
        this.project = project
        this.logger = logger
        this.cssPluginConvention = new CssPluginConvention()

        project.convention.plugins.css = cssPluginConvention
        project.extensions.add(CssExtension.NAME, InternalGradle.toInstantiator(project).newInstance(CssExtension, project))
        configureDependencies()
        applyTasks(project)
    }

    void applyTasks(final Project project) {
        project.task('minifyCss', type: MinifyCssTask) {
            lineBreakPos = project.convention.plugins.css.lineBreakPos
        }

        project.task('combineCss', type: CombineCssTask) {}

        project.task('gzipCss', type: GzipCssTask) {}

        project.task('csslint', type: CssLintTask) {
            options = project.convention.plugins.css.options
        }

        project.task('css', type: CssTask) {
            lineBreakPos = project.convention.plugins.css.lineBreakPos
        }
    }

    void configureDependencies() {
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