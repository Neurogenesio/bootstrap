# Spine bootstrap fork

[![Build Status][badge]](https://travis-ci.com/SpineEventEngine/bootstrap)

The fork of [Spine bootstrap plugin][bootstrap-gh-link] which accommodates changes that couldn't be 
applied to the plugin itself but are needed by Neurogenesio projects.

[badge]: https://travis-ci.com/SpineEventEngine/bootstrap.svg?branch=master
[bootstrap-gh-link]: https://github.com/SpineEventEngine/bootstrap

## Applying to the project

To apply the fork to a Gradle project, in `build.gralde` add the following config:

```gradle
buildscript() {
	repositories {
		maven {
			url 'https://jitpack.io'
		}
	}
	dependencies {
		classpath 'com.github.Neurogenesio:bootstrap:${projectDeps.versions.spineBootstrap}'
	}
}

apply plugin: "io.spine.tools.gradle.bootstrap"
```
