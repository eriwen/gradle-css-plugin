# Quick Start [![Build Status](https://secure.travis-ci.org/eriwen/gradle-css-plugin.png)](http://travis-ci.org/eriwen/gradle-css-plugin)

Managing your CSS in a [Gradle](http://gradle.org) build is super easy now! Just add this to your *build.gradle* file:

### Gradle 2.1+
```groovy
plugins {
  id "com.eriwen.gradle.css" version "2.14.0"
}
```

### Gradle 2.0-
```groovy
// Grab the plugin from a Maven Repo automatically
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.eriwen:gradle-css-plugin:1.11.0'
    }
}

// Invoke the plugin
apply plugin: 'css'

// Declare your sources
css.source {
    dev {
        css {
            srcDir "app/styles"
            include "*.css"
            exclude "*.min.css"
        }
    }
}

// Specify a collection of files to be combined, then minified and finally GZip compressed.
combineCss {
    source = css.source.dev.css.files
    dest = "${buildDir}/all.css"
}

minifyCss {
    source = combineCss
    dest = "${buildDir}/all-min.css"
    yuicompressor { // Optional
        lineBreakPos = -1
        charset = 'UTF-8'
    }
}

gzipCss {
    source = minifyCss
    dest = "${buildDir}/all.2.0.4.css"
}
```

**[LESS Support](http://lesscss.org)**
```groovy
css.source {
    dev {
        css {
            srcDir "app/styles"
            include "*.less"
        }
    }
}

lesscss {
    source = css.source.dev.css.asFileTree
    dest = "${buildDir}/styles"
}
```

**Supports [CSS Lint v0.9.10](http://csslint.net)**
```groovy
csslint {
    source = css.source.dev.css.files
    dest = "${buildDir}/csslint.out"
}
```

# Available Tasks and Options
### combineCss
- source = Collection of file paths of files to merge
- dest = File/String Path for combined output

### minifyCss (Uses the [YUI Compressor](http://developer.yahoo.com/yui/compressor/))
- source = File to minify
- dest = File for minified output
- *(Optional)* yuicompressor.lineBreakPos = -1 (default) Insert a line break after the specified column number
- *(Optional)* yuicompressor.charset = 'UTF-8' (default) Read the input file using specified charset (e.g. 'UTF-8', 'ISO-8859-1', etc.)

### gzipCss
- source = File to compress
- dest = File/String path for compressed output

### less
- source = Files to transpile
- dest = File/String output directory

### csslint ###
- source = Collection of file paths of files to analyze
- dest = File for output
- *(Optional)* format = 'compact' (default), 'text', 'lint-xml', or 'checkstyle-xml'
- *(Optional)* warnings = (default is all) Collection of string ids for checks. Try `csslint --list-rules` to see all possible IDs
- *(Optional)* errors = (default is none) Collection of string ids for checks. CAUTION: These cause a non-zero exit code and _fail the build!_

What, you want more? [Let me know!](https://github.com/eriwen/gradle-css-plugin/issues)

# See Also #
The [Gradle JS Plugin](https://github.com/eriwen/gradle-js-plugin)!
