[![Build Status](https://travis-ci.org/dkandalov/nonstdlib.svg?branch=master)](https://travis-ci.org/dkandalov/nonstdlib)

### Examples
```
(1 + 2).printed()
```

### Adding Gradle dependency
Add dependency to `build.gradle`:
```
repositories {
    mavenCentral()
    ivy {
        artifactPattern("https://raw.githubusercontent.com/dkandalov/nonstdlib/master/jars/[artifact]-[revision](.[ext])")
    }
}
dependencies {
    compile "nonstdlib:nonstdlib:0.1"
}
```
