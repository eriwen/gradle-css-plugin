package com.eriwen.gradle.css

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class LessPluginTest extends Specification {
    @Rule TemporaryFolder dir = new TemporaryFolder()

    Project project = ProjectBuilder.builder().build()
    def task
    File src
    File outputFile
    String expectedOutput

    def setup() {
        project.apply(plugin: CssPlugin)
        task = project.tasks.lesscss
        src = dir.newFolder()
        task.source = src
        task.dest = dir.newFolder()
        outputFile = new File(task.dest, "sample.css")
        expectedOutput = new File("src/test/resources/expectedsampleoutput.css").text
    }

    def "runWithDefaults"() {
        given:
        addSampleFile()

        when:
        task.run()

        then:
        notThrown Exception
        outputFile.exists()
        outputFile.text.contentEquals(expectedOutput)
    }

    def addSampleFile() {
        addFile("sample.less", new File("src/test/resources/sample.less").text)
    }

    def addFile(name,contents) {
        def file = new File(src as String, name)
        file << contents
    }
}
