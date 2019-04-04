package com.eriwen.gradle.css.source.internal;

import com.eriwen.gradle.css.source.CssSourceSet;
import com.eriwen.gradle.css.source.CssSourceSetContainer;
import org.gradle.api.Project;
import org.gradle.api.internal.AbstractNamedDomainObjectContainer;
import org.gradle.api.internal.CollectionCallbackActionDecorator;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.internal.reflect.Instantiator;

public class DefaultCssSourceSetContainer extends AbstractNamedDomainObjectContainer<CssSourceSet> implements CssSourceSetContainer {

    private final Project project;
    private final Instantiator instantiator;
    private final FileResolver fileResolver;

    public DefaultCssSourceSetContainer(Project project, Instantiator instantiator, FileResolver fileResolver) {
        super(CssSourceSet.class, instantiator, CollectionCallbackActionDecorator.NOOP);
        this.project = project;
        this.instantiator = instantiator;
        this.fileResolver = fileResolver;
    }

    @Override
    protected CssSourceSet doCreate(String name) {
        return getInstantiator().newInstance(DefaultCssSourceSet.class, name, project, instantiator, fileResolver);
    }
}
