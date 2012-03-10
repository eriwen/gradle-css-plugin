package com.eriwen.gradle.css

class SourcesTest extends ProjectTest {
    
    def setup() {
        apply plugin: CssPlugin
    }

    def createSource(String name = "main") {
        def srcRoot = newFolder("src", name, "css")
        (new File(srcRoot, "source.css") << "").canonicalFile
    }

    def "can add to the source set container"() {
        given:
        def src = createSource("custom")

        when:
        css {
            source {
                custom {
                    css {
                        srcDir "src/custom/css"
                    }
                }
            }
        }

        then:
        css.source.custom.name == "custom"
        css.source.custom.css.files.toList() == [src]
    }
}

