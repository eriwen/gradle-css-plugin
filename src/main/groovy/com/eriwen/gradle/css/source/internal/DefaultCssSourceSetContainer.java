package com.eriwen.gradle.css.source.internal;

import com.eriwen.gradle.css.source.CssSourceSet;
import com.eriwen.gradle.css.source.CssSourceSetContainer;
import org.gradle.api.Project;
import org.gradle.api.internal.AbstractNamedDomainObjectContainer;

public class DefaultCssSourceSetContainer extends AbstractNamedDomainObjectContainer<CssSourceSet> implements CssSourceSetContainer {

    private final Project project;

    public DefaultCssSourceSetContainer(Project project) {
        super(CssSourceSet.class, InternalGradle.toInstantiator(project));
        this.project = project;
    }

    @Override
    protected CssSourceSet doCreate(String name) {
        return getInstantiator().newInstance(DefaultCssSourceSet.class, name, project);
    }
}
