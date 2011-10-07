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
        classpath 'com.eriwen:gradle-css-plugin:0.1'
    }
}
// Invoke the plugin
apply plugin: 'css'

// Specify a collection of files to be combined, then minified and finally GZip compressed.
processCss {
    input = fileTree(dir: "${projectDir}/css", include: "**/*.css")
    output = file("${buildDir}/combinedMinifiedAndGzipped.css")
}
```

**Need more than 1 set of files generated? Just add another *processCss* block:**

```groovy
processCss {
    input = fileTree(dir: "${projectDir}/otherdir", includes: ["file1.css", "file2.css"])
    output = file("${buildDir}/teenytiny.css")
}
```

**Want more fine-grained control or just want to combine, minify or zip your files?**

```groovy
// Combine CSS files
combineCss {
    input = fileTree(dir: "${projectDir}/css", include: "**/*.css")
    output = file("${buildDir}/all.css")
}

// Minify with YUI Compressor
minifyCss {
    input = file("${buildDir}/all.css")
    output = file("${buildDir}/all-min.css")
    lineBreakPos = 120
}

// GZip
gzipCss {
    input = file("${buildDir}/all-min.css")
    output = input
}
```

# Available Tasks and Options #
 - combineCss
input = [FileCollection](http://gradle.org/current/docs/javadoc/org/gradle/api/file/FileCollection.html) of files to merge
output = File for combined output
 - minifyCss Uses the [YUI Compressor](http://developer.yahoo.com/yui/compressor/)
input = File to minify
output = File for minified output
*(Optional)* charset = 'UTF-8' (default) Read the input file using given charset
*(Optional)* lineBreakPos = -1 (default) Insert a line break after the specified column number
 - gzipCss
input = File to compress
output = File for compressed output
 - processCss
input = File to minify
output = File for minified output
*(Optional)* charset = 'UTF-8' (default) Read the input file using given charset
*(Optional)* lineBreakPos = -1 (default) Insert a line break after the specified column number
 - What, you want more? [Let me know!](https://github.com/eriwen/gradle-css-plugin/issues)

# See Also #
The [Gradle JS Plugin](https://github.com/eriwen/gradle-js-plugin)!
