# Hello, Gradle CSS Plugin #
Aiming to be the *simplest* way to manage your CSS in a build.

# Quick Start #
Managing your CSS in a [Gradle](http://gradle.org) build is super easy now! Just add this to your *build.gradle* file:

```groovy
// Grab the plugin from a Maven Repo automatically
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.eriwen:gradle-css-plugin:0.2'
    }
}
// Invoke the plugin
apply plugin: 'css'

// Specify a collection of files to be combined, then minified and finally GZip compressed.
css {
    input.files fileTree(dir: "${projectDir}/css", include: "**/*.css")
    output.file file("${buildDir}/combinedMinifiedAndGzipped.css")
}
```

**Need more than 1 set of files generated? Just add another *css* block:**

```groovy
css {
    input.files fileTree(dir: "${projectDir}/otherdir", includes: ["file1.css", "file2.css"])
    output.file file("${buildDir}/teenytiny.css")
}
```

**Supports [CSS Lint](http://csslint.net)**
```groovy
csslint {
	inputs.files fileTree(dir: "${projectDir}/css", include: "**/*.css")
	outputs.file file("${buildDir}/csslint.xml")
	options = ["--rules=adjoining-classes,grouping", '--format=lint-xml']
}
```

**Want more fine-grained control or just want to combine, minify or zip your files?**

```groovy
// Combine CSS files
combineCss {
    input.files fileTree(dir: "${projectDir}/css", include: "**/*.css")
    output.file file("${buildDir}/all.css")
}

// Minify with YUI Compressor
minifyCss {
    input.file file("${buildDir}/all.css")
    output.file file("${buildDir}/all-min.css")
    lineBreakPos = 120
}

// GZip
gzipCss {
    input.file file("${buildDir}/all-min.css")
    output.file input
}
```

# Available Tasks and Options #
### combineCss ###
 - input.files [FileCollection](http://gradle.org/current/docs/javadoc/org/gradle/api/file/FileCollection.html) of files to merge
 - output.file File for combined output

### minifyCss (Uses the [YUI Compressor](http://developer.yahoo.com/yui/compressor/)) ###
 - input.file File to minify
 - output.file File for output
 - *(Optional)* charset = 'UTF-8' (default) Read the input file using given charset
 - *(Optional)* lineBreakPos = -1 (default) Insert a line break after the specified column number

### gzipCss ###
 - input.file File to compress
 - output.file File for compressed output 

### css ###
 - input.files [FileCollection](http://gradle.org/current/docs/javadoc/org/gradle/api/file/FileCollection.html) of files to merge
 - output.file File for minified output
 - *(Optional)* charset = 'UTF-8' (default) Read the input file using given charset
 - *(Optional)* lineBreakPos = -1 (default) Insert a line break after the specified column number

## csslint ##
 - input = [FileCollection](http://gradle.org/current/docs/javadoc/org/gradle/api/file/FileCollection.html) of files to assess
 - output = File for output
 - *(Optional)* options = command-line options. See [CSS Lint CLI Details](https://github.com/stubbornella/csslint/wiki/Command-line-interface)

What, you want more? [Let me know!](https://github.com/eriwen/gradle-css-plugin/issues)

# See Also #
The [Gradle JS Plugin](https://github.com/eriwen/gradle-js-plugin)!
