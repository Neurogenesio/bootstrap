# Spine bootstrap fork

The fork of [Spine bootstrap plugin][bootstrap-gh-link] which accommodates changes that couldn't be
applied to the plugin itself but are needed by Neurogenesio projects.

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

## CI/CD

We use GitHub actions to perform automated builds of this repository. Actions that are coming
from the forked repository are present in the `./github/workflows` directory but manually
disabled in the settings of the corresponding GitHub [repository][repo-actions].
This approach is recommended for the forked repositories, see the corresponding
the [documentation][disabling-workflows].

[repo-actions]: https://github.com/Neurogenesio/bootstrap/actions
[disabling-workflows]: https://docs.github.com/en/actions/managing-workflow-runs/disabling-and-enabling-a-workflow
