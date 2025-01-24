dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    implementation(project(":shared"))
    testImplementation(project(path = ":shared", configuration = "testArtifacts"))
}
