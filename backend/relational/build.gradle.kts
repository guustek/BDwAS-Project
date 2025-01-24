dependencies {
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation(project(":shared"))
    testImplementation(project(path = ":shared", configuration = "testArtifacts"))
}


