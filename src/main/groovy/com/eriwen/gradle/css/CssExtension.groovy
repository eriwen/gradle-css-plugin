package com.eriwen.gradle.css

import com.eriwen.gradle.css.source.CssSourceSetContainer
import com.eriwen.gradle.css.source.internal.DefaultCssSourceSetContainer
import org.gradle.api.Project
import org.gradle.api.internal.file.FileResolver
import org.gradle.internal.reflect.Instantiator
import org.gradle.util.ConfigureUtil

class CssExtension {

    public static final NAME = "css"

    final CssSourceSetContainer source

    CssExtension(Project project, Instantiator instantiator, FileResolver fileResolver) {
        source = instantiator.newInstance(DefaultCssSourceSetContainer, project, instantiator, fileResolver)
    }

    void source(Closure closure) {
        ConfigureUtil.configure(closure, source)
    }
}
