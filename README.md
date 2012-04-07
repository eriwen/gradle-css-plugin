# Quick Start #
Managing your CSS in a [Gradle](http://gradle.org) build is super easy now! Just add this to your *build.gradle* file:

```groovy
// Grab the plugin from a Maven Repo automatically
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.eriwen:gradle-css-plugin:0.3'
    }
}
// Invoke the plugin
apply plugin: 'css'

// Specify a collection of files to be combined, then minified and finally GZip compressed.
task combinecss(type: com.eriwen.gradle.css.tasks.CombineCssTask) {
	source = ["${projectDir}/css/file1.css", "${projectDir}/css/file2.css"]
	dest = file("${buildDir}/all.css")
}

task minifycss(type: com.eriwen.gradle.css.tasks.MinifyCssTask, dependsOn: 'combinecss') {
	source = file("${buildDir}/all.css")
	dest = file("${buildDir}/all-min.css")
	warningLevel = 'QUIET'
}

task gzipcss(type: com.eriwen.gradle.css.tasks.GzipCssTask, dependsOn: 'minifycss') {
	source = file("${buildDir}/all-min.css")
	dest = file("${buildDir}/all-min.css")
}
```

**Supports [CSS Lint](http://csslint.net)**
```groovy
csslint {
	source = fileTree(dir: "${projectDir}/css", include: "**/*.css").collect { it.canonicalPath }
	dest = file("${buildDir}/csslint.xml")
	options = ["--rules=adjoining-classes,grouping", '--format=lint-xml']
}
```

# Available Tasks and Options #
### combineCss ###
- source = Collection of file paths of files to merge
- dest = File for combined output

### minifyCss (Uses the [YUI Compressor](http://developer.yahoo.com/yui/compressor/)) ###
- source = File to minify
- dest = File for minified output
- *(Optional)* charset = 'UTF-8' (default) Read the input file using given charset
- *(Optional)* lineBreakPos = -1 (default) Insert a line break after the specified column number

### gzipCss ###
- source = File to compress
- dest = File for compressed output

### css (DEPRECATED, will be removed in v0.4) ###
- input.files [FileCollection](http://gradle.org/current/docs/javadoc/org/gradle/api/file/FileCollection.html) of files to merge
- output.file File for minified output
- *(Optional)* charset = 'UTF-8' (default) Read the input file using given charset
- *(Optional)* lineBreakPos = -1 (default) Insert a line break after the specified column number

### csslint ###
- source = Collection of file paths of files to merge
- dest = File for output
- *(Optional)* options = command-line options. See [CSS Lint CLI Details](https://github.com/stubbornella/csslint/wiki/Command-line-interface)

What, you want more? [Let me know!](https://github.com/eriwen/gradle-css-plugin/issues)

# See Also #
The [Gradle JS Plugin](https://github.com/eriwen/gradle-js-plugin)!
