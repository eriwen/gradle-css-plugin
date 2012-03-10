package com.eriwen.gradle.css

import com.eriwen.gradle.css.source.CssSourceSetContainer
import com.eriwen.gradle.css.source.internal.DefaultCssSourceSetContainer
import com.eriwen.gradle.css.source.internal.InternalGradle
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

class CssExtension {

    public static final NAME = "css"
    
    final CssSourceSetContainer source

    CssExtension(Project project) {
        source = InternalGradle.toInstantiator(project).newInstance(DefaultCssSourceSetContainer, project)
    }

    void source(Closure closure) {
        ConfigureUtil.configure(closure, source)
    }
}
