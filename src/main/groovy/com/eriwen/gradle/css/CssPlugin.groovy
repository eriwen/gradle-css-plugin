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

class CssPlugin implements Plugin<Project> {
	private Project project
	private Logger logger
	private CssPluginConvention cssPluginConvention
	
	void apply(final Project project) {
		this.project = project
		this.logger = logger
		this.cssPluginConvention = new CssPluginConvention()

		project.convention.plugins.css = cssPluginConvention
		applyTasks(project)
	}
	
	void applyTasks(final Project project) {
		project.task('minifyCss', type: com.eriwen.gradle.css.tasks.MinifyCssTask) {
			inputFile = project.convention.plugins.css.inputFile
			outputFile = project.convention.plugins.css.outputFile
		}
	}
}