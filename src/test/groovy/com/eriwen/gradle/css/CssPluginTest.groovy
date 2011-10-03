package com.eriwen.gradle.css

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.After
import org.junit.Test

import static org.junit.Assert.*

class CssPluginTest {
    private static final String TEST_SOURCE_PATH = new File('.', 'src/test/resources').absolutePath

	private Project project
    private CssPlugin plugin

    @Before
    void setUp() {
        project = ProjectBuilder.builder().build()
        plugin = new CssPlugin()
    }

    @After
    void tearDown() {
        project = null
        plugin = null
    }

    @Test
    void shouldApplyCssTasks() {
        plugin.apply(project)

        assertEquals 1, project.getTasksByName('combineCss', false).size()
        assertEquals 1, project.getTasksByName('minifyCss', false).size()
        assertEquals 1, project.getTasksByName('gzipCss', false).size()
        assertEquals 0, project.getTasksByName('bogus', false).size()
    }
}