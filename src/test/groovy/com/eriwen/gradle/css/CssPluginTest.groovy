package com.eriwen.gradle.css

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.After
import org.junit.Test

import static org.junit.Assert.*
import org.gradle.api.tasks.TaskExecutionException

class CssPluginTest {
    Project project = ProjectBuilder.builder().build()

    def setup() {
        project.apply(plugin: JsPlugin)
    }

    def "extensions are installed"() {
        expect:
        project.extensions.getByName("csslint") instanceof CssExtension
        project.extensions.getByName("yuicompressor") instanceof CssExtension
    }
}
