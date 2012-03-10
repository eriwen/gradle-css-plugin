package com.eriwen.gradle.css.source

import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.file.FileCollection
import org.gradle.api.file.SourceDirectorySet
import org.gradle.util.Configurable

interface CssSourceSet extends Named, Configurable<CssSourceSet> {

    SourceDirectorySet getCss()

    SourceDirectorySet css(Action<SourceDirectorySet> action)

    CssProcessingChain getProcessing()

    CssProcessingChain processing(Action<CssProcessingChain> action)
    
    FileCollection getProcessed()
}
