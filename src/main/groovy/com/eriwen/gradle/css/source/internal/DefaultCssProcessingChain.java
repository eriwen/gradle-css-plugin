package com.eriwen.gradle.css.source.internal;

import com.eriwen.gradle.css.source.CssProcessingChain;
import com.eriwen.gradle.css.source.CssSourceSet;
import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.DefaultNamedDomainObjectList;
import org.gradle.api.tasks.SourceTask;
import org.gradle.internal.reflect.Instantiator;

import java.util.Collections;
import java.util.concurrent.Callable;

public class DefaultCssProcessingChain extends DefaultNamedDomainObjectList<SourceTask> implements CssProcessingChain {

    private final DefaultCssSourceSet source;
    private final Project project;

    public DefaultCssProcessingChain(Project project, DefaultCssSourceSet source, Instantiator instantiator) {
        super(SourceTask.class, instantiator, new Task.Namer());
        this.source = source;
        this.project = project;
        wireChain();
    }

    public CssSourceSet getSource() {
        return source;
    }

    protected void wireChain() {
        all(new Action<SourceTask>() {
            public void execute(final SourceTask sourceTask) {
                sourceTask.source(new Callable<FileCollection>() {
                    public FileCollection call() throws Exception {
                        int index = indexOf(sourceTask);
                        if (index == -1) {
                            return null; // task has been removed, noop
                        } else if (index == 0) {
                            return getSource().getCss();
                        } else {
                            SourceTask previous = get(index - 1);
                            return previous.getOutputs().getFiles();
                        }
                    }
                });
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends SourceTask> T task(Class<T> type) {
        return task(calculateName(type), type);
    }

    public <T extends SourceTask> T task(String name, Class<T> type) {
        return task(name, type, null);
    }

    public <T extends SourceTask> T task(Class<T> type, Closure closure) {
        return task(calculateName(type), type, closure);
    }

    public <T extends SourceTask> T task(String name, Class<T> type, Closure closure) {
        @SuppressWarnings("unchecked")
        T task = (T)project.task(Collections.singletonMap("type", type), name, closure);
        add(task);
        return task;
    }

    protected String calculateName(Class<? extends SourceTask> type) {
        String name = type.getName();
        if (name.endsWith("Task")) {
            name = name.substring(0, name.length() - 4);
        }

        return source.getName() + name;
    }
}
