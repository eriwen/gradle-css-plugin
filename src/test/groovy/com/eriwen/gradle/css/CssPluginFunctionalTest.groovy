package com.eriwen.gradle.css

import com.eriwen.gradle.css.util.FunctionalSpec

class CssPluginFunctionalTest extends FunctionalSpec {

    def setup() {
        buildFile << applyPlugin(CssPlugin)
    }

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
        file("src/custom/css/file1.css") << "#id1 { color: green; }"
        and:
        file("src/custom/css/file2.css") << ".class1 { color: red; }"

        when:
        run "minifyCss"

        then:
        file("build/all-min.css").text.indexOf('#id1{color:green}') > -1
        file("build/all-min.css").text.indexOf('.class1{color:red}') > -1

        and:
        wasExecuted ":combineCss" //Test dependency inference
        wasExecuted ":minifyCss"

        when:
        run "minifyCss"

        then:
        wasUpToDate ":combineCss" //Test proper sourceSet detection
        wasUpToDate ":minifyCss"

        when:
        file("src/custom/css/file3.css") << "selector { color: blue; }"

        and:
        run "minifyCss"

        then:
        !wasUpToDate(":minifyCss")

        and:
        // NOTE: File order is not guaranteed
        file("build/all-min.css").text.indexOf('#id1{color:green}') > -1
        file("build/all-min.css").text.indexOf('.class1{color:red}') > -1
        file("build/all-min.css").text.indexOf('selector{color:blue}') > -1
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
        file("src/custom/css/file1.css") << "#id1 { color: green; }"
        and:
        file("src/custom/css/file2.css") << ".class1 { color: red; }"

        when:
        run "csslint"

        then:
        wasExecuted ":csslint"
    }
}
