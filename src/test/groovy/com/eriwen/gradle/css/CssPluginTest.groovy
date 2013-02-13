package com.eriwen.gradle.css

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class CssPluginTest extends Specification  {
    Project project = ProjectBuilder.builder().build()

    def setup() {
        project.apply(plugin: CssPlugin)
    }

    def "extensions are installed"() {
        expect:
        project.extensions.getByName("csslint") instanceof CssLintExtension
        project.extensions.getByName("yuicompressor") instanceof YuiCompressorExtension
        project.extensions.getByName("less") instanceof LessExtension
        project.extensions.getByName("css") instanceof CssExtension
    }
}
