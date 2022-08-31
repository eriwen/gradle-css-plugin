package com.eriwen.gradle.css

import com.eriwen.gradle.css.util.FunctionalSpec
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.TaskOutcome

class CssPluginFunctionalTest extends FunctionalSpec {
    def "test tasks operation"() {
        given:
        buildFile << """
            css.source {
                custom {
                    css {
                        srcDir "src/custom/css"
                    }
                }
            }

            combineCss {
                source = css.source.custom.css.files
                dest = file("\$buildDir/all.css")
            }

            minifyCss {
                source = combineCss
                dest = "\$buildDir/all-min.css" //Test flexible outputs
            }
        """
        and:
        tempProjectDir.newFolder('src', 'custom', 'css')
        tempProjectDir.newFile("src/custom/css/file1.css") << "#id1 { color: green; }"
        tempProjectDir.newFile("src/custom/css/file2.css") << ".class1 { color: red; width: calc(90% + 50px); }"

        when:
        BuildResult result = build('minifyCss')

        then:
        File minifiedCss = new File(projectDir, 'build/all-min.css')
        minifiedCss.exists()
        minifiedCss.text.indexOf('#id1{color:green}') > -1
        minifiedCss.text.indexOf('.class1{color:red;width:calc(90% + 50px)}') > -1

        and:
        result.task(':combineCss').outcome == TaskOutcome.SUCCESS
        result.task(':minifyCss').outcome == TaskOutcome.SUCCESS

        when:
        BuildResult secondResult = build('minifyCss')

        then:
        secondResult.task(':combineCss').outcome == TaskOutcome.UP_TO_DATE
        secondResult.task(':minifyCss').outcome == TaskOutcome.UP_TO_DATE

        when:
        tempProjectDir.newFile("src/custom/css/file3.css") << "selector { color: blue; }"

        and:
        BuildResult thirdResult = build('minifyCss')

        then:
        thirdResult.task(':minifyCss').outcome == TaskOutcome.SUCCESS
    }

    def "test csslint task"() {
        given:
        buildFile << """
            css.source {
                custom {
                    css {
                        srcDir "src/custom/css"
                    }
                }
            }
            csslint {
               source = css.source.custom.css.files
               dest = file("\$buildDir/csslint.out")
            }
        """
        and:
        tempProjectDir.newFolder('src', 'custom', 'css')
        tempProjectDir.newFile("src/custom/css/file1.css") << "#id1 { color: green; }"
        tempProjectDir.newFile("src/custom/css/file2.css") << ".class1 { color: red; }"

        when:
        BuildResult result = build('csslint')

        then:
        result.task(':csslint').outcome == TaskOutcome.SUCCESS
    }

    def "test lesscss task"() {
        given:
        buildFile << """
            css {
                source {
                    main {
                        css {
                            srcDir 'src/custom/less'
                            include '**/*.less'
                        }
                    }
                }
            }
            lesscss {
                source = css.source.main.css.asFileTree
                dest = file("\$buildDir/less/")
            }
        """
        and:
        tempProjectDir.newFolder('src', 'custom', 'less')
        tempProjectDir.newFile("src/custom/less/vars.less") << '@color: #4d926f; '
        tempProjectDir.newFile("src/custom/less/page.less") << "@import './vars.less'; " +
                'h1 { color: @color; }'

        when:
        BuildResult result = build('lesscss')

        then:
        result.task(':lesscss').outcome == TaskOutcome.SUCCESS

        and:
        new File(projectDir, 'build/less/page.css').text.indexOf('color: #4d926f;') > -1
    }
}
