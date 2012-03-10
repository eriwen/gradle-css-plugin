package com.eriwen.gradle.css.source.internal

import com.eriwen.gradle.css.source.CssProcessingChain
import com.eriwen.gradle.css.source.CssSourceSet
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.util.ConfigureUtil
import org.gradle.util.GUtil

class DefaultCssSourceSet implements CssSourceSet {

    private final String name
    private final String displayName
    private final DefaultSourceDirectorySet css
    private final CssProcessingChain processing
    private final FileCollection processed
    
    DefaultCssSourceSet(String name, Project project) {
        this.name = name
        this.displayName = GUtil.toWords(name)
        this.css = new DefaultSourceDirectorySet(name, String.format("%s Css source", displayName), InternalGradle.toFileResolver(project))
        this.processing = InternalGradle.toInstantiator(project).newInstance(DefaultCssProcessingChain, project, this)
        this.processed = project.files({ processing.empty ? css : processing.last().outputs.files })
    }

    String getName() {
        name
    }
    
    SourceDirectorySet getCss() {
        css
    }

    SourceDirectorySet css(Action<SourceDirectorySet> action) {
        action.execute(css)
        css
    }
    
    CssSourceSet configure(Closure closure) {
        ConfigureUtil.configure(closure, this, false)
    }

    CssProcessingChain getProcessing() {
        processing
    }

    CssProcessingChain processing(Action<CssProcessingChain> action) {
        action.execute(processing)
        processing
    }

    FileCollection getProcessed() {
        processed
    }
}
