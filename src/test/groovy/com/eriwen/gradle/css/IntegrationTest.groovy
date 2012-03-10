package com.eriwen.gradle.css

import org.gradle.GradleLauncher
import org.gradle.StartParameter
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.tasks.TaskState
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class IntegrationTest extends Specification {
    
    @Rule final TemporaryFolder dir = new TemporaryFolder()

    static class ExecutedTask {
        Task task
        TaskState state
    }
    
    List<ExecutedTask> executedTasks = []

    GradleLauncher launcher(String... args) {
        StartParameter startParameter = GradleLauncher.createStartParameter(args)
        startParameter.setProjectDir(dir.root)
        GradleLauncher launcher = GradleLauncher.newInstance(startParameter)
        executedTasks.clear()
        launcher.addListener(new TaskExecutionListener() {
            void beforeExecute(Task task) {
                executedTasks << new ExecutedTask(task: task)
            }

            void afterExecute(Task task, TaskState taskState) {
                executedTasks.last().state = taskState
                taskState.metaClass.upToDate = taskState.skipMessage == "UP-TO-DATE"
            }
        })
        launcher
    }
    
    File getBuildFile() {
        file("build.gradle")
    }
    
    File file(String path) {
        def parts = path.split("/")
        if (parts.size() > 1) {
            dir.newFolder(*parts[0..-2])
        }
        dir.newFile(path)
    }

    ExecutedTask task(String name) {
        executedTasks.find { it.task.name == name }
    }

    def setup() {
        buildFile << """
            CssPlugin = project.class.classLoader.loadClass("com.eriwen.gradle.css.CssPlugin")
        """
    }

    def "basic processing chain"() {
        given:        
        buildFile << """
            apply plugin: CssPlugin

            class TestTask extends SourceTask {
                
                @OutputDirectory
                File destination
                
                @TaskAction
                void doit() {
                    project.copy {
                        from getSource()
                        into destination
                    }
                }
            }

            css {
                source {
                    custom {
                        css {
                            srcDir "src/custom/css"    
                        }
                        processing {
                            task(TestTask) {
                                destination project.file("build/\$name")
                            }
                            task("secondTask", TestTask) {
                                destination project.file("build/\$name")
                            }
                        }
                    }
                }
            }

            task copyProcessed(type: Copy) {
                from css.source.custom.processed
                into "build/out"
            }
        """
        and:
        file("src/custom/css/stuff.css") << ""

        when:
        launcher("copyProcessed").run()

        then:
        task("customTest").state.executed
        task("secondTask").state.executed

        when:
        launcher("copyProcessed").run()

        then:
        task("customTest").state.upToDate
        task("secondTask").state.upToDate
    }

    def "combine, minify, gzip"() {
        given:
        buildFile << """
            apply plugin: CssPlugin

            css {
                source {
                    custom {
                        css {
                            srcDir "src/custom/css"
                        }
                    }
                }
            }

            task combine(type: CombineCssTask) {
                //css.source.custom.css.files
                inputs.files fileTree(dir: css.source.custom.css.srcDir, includes: ['file2.css'])
                outputs.file file("build/all.css")
            }
        """
        and:
        file("src/custom/css/file1.css") << "#id1 { color: red; }"
        and:
        file("src/custom/css/file2.css") << "#id2 { color: green; }"

        when:
        launcher('combine').run()

        then:
        file('build/all.css').exists()
    }
}
