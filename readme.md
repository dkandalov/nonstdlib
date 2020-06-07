[![Build Status](https://travis-ci.org/dkandalov/nonstdlib.svg?branch=master)](https://travis-ci.org/dkandalov/nonstdlib)

### Step 1
Add dependency to `build.gradle`:
```
repositories {
    maven { url "https://dl.bintray.com/dkandalov/maven" }
}
dependencies {
    compile "nonstdlib:nonstdlib:0.1"
}
```

### Step 2
Use code from `nonstdlib` package, e.g.
```
(1 + 2).printed()
```

### Step 3
Profit! 💸
