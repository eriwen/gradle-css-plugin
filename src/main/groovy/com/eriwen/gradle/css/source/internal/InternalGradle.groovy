package com.eriwen.gradle.css.source.internal;

import org.gradle.api.Project;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.api.internal.project.ProjectInternal;

/**
 * Centralises access to internal Gradle API
 */
public abstract class InternalGradle {

   public static Instantiator toInstantiator(Project project) {
      return project.gradle.services.get(Instantiator)
   }

   public static FileResolver toFileResolver(Project project) {
      return project.fileResolver
   }
}
