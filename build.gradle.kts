
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Project.dependencies.androidGradlePlugin)
        classpath(Project.dependencies.kotlin.plugin)
        classpath(Project.dependencies.androidX.navigation.safeArgsPlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
