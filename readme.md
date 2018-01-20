[![Build Status](https://travis-ci.org/dkandalov/kotlincommon.svg?branch=master)](https://travis-ci.org/dkandalov/kotlincommon)

### Step 1
Add dependency to `build.gradle`:
```
repositories {
	mavenCentral()
	ivy {
		layout "pattern"
		artifactPattern "http://raw.githubusercontent.com/dkandalov/kotlin-common/master/[organisation]-[artifact]-[revision](-[classifier])(.[ext])"
	}
}

dependencies {
	compile "dkandalov:kotlin-common:0.1"
}
```

### Step 2
Use code from `kotlincommon` package, e.g.
```
(1 + 2).printed()
```

### Step 3
Profit! 💸
