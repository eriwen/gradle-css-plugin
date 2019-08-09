package com.eriwen.gradle.css

import com.eriwen.gradle.css.source.CssSourceSet
import com.eriwen.gradle.css.source.internal.DefaultCssSourceSet
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Project
import org.gradle.api.internal.file.FileResolver
import org.gradle.internal.reflect.Instantiator
import org.gradle.util.ConfigureUtil

class CssExtension {

    public static final NAME = "css"

    final NamedDomainObjectContainer<CssSourceSet> source

    CssExtension(Project project, Instantiator instantiator, FileResolver fileResolver) {
        source = project.container(CssSourceSet.class, new NamedDomainObjectFactory<CssSourceSet>() {
            @Override
            CssSourceSet create(String name) {
                return instantiator.newInstance(DefaultCssSourceSet.class, name, project, instantiator, fileResolver)
            }
        })
    }

    void source(Closure closure) {
        ConfigureUtil.configure(closure, source)
    }
}
