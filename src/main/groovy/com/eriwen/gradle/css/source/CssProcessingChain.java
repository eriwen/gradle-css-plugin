package com.eriwen.gradle.css.source;

import groovy.lang.Closure;
import org.gradle.api.NamedDomainObjectList;
import org.gradle.api.tasks.SourceTask;

public interface CssProcessingChain extends NamedDomainObjectList<SourceTask> {

    CssSourceSet getSource();

    <T extends SourceTask> T task(Class<T> type);
    <T extends SourceTask> T task(String name, Class<T> type);
    <T extends SourceTask> T task(Class<T> type, Closure closure);
    <T extends SourceTask> T task(String name, Class<T> type, Closure closure);
}
