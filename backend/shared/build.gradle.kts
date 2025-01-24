configurations {
    create("testArtifacts") {
        isCanBeResolved = false
        isCanBeConsumed = true
        extendsFrom(configurations.testImplementation.get())
    }
}

// Publish the `test` classes and resources
tasks.register<Jar>("testJar") {
    from(sourceSets["test"].output)
    archiveClassifier.set("tests")
}

artifacts {
    add("testArtifacts", tasks["testJar"])
}
