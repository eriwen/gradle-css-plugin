package com.eriwen.gradle.css.source.internal

import com.eriwen.gradle.css.source.CssProcessingChain
import com.eriwen.gradle.css.source.CssSourceSet
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.api.internal.file.FileResolver
import org.gradle.internal.reflect.Instantiator
import org.gradle.util.ConfigureUtil
import org.gradle.util.GUtil
import org.gradle.util.GradleVersion

class DefaultCssSourceSet implements CssSourceSet {

    private final String name
    private final String displayName
    private final DefaultSourceDirectorySet css
    private final CssProcessingChain processing
    private final FileCollection processed

    DefaultCssSourceSet(String name, Project project, Instantiator instantiator, FileResolver fileResolver) {
        this.name = name
        this.displayName = GUtil.toWords(name)
        if (GradleVersion.current().compareTo(GradleVersion.version("2.12")) >= 0) {
            Class fileTreeFactory = Class.forName("org.gradle.api.internal.file.collections.DefaultDirectoryFileTreeFactory")
            def directoryFileTreeFactory = fileTreeFactory.getConstructor().newInstance()
            this.css = new DefaultSourceDirectorySet(name, String.format("%s CSS source", displayName), fileResolver, directoryFileTreeFactory)
        } else {
            this.css = new DefaultSourceDirectorySet(name, String.format("%s CSS source", displayName), fileResolver)
        }

        this.processing = instantiator.newInstance(DefaultCssProcessingChain, project, this, instantiator)
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
        if (GradleVersion.current().compareTo(GradleVersion.version("2.14")) >= 0) {
            ConfigureUtil.configureSelf(closure, this)
        } else {
            ConfigureUtil.configure(closure, this, false)
        }
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
