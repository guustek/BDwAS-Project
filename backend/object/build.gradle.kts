repositories {
    maven {
        url = uri("https://m2.objectdb.com")
        name = "ObjectDB Repository"
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.objectdb:objectdb-jk:2.9.2")
}

tasks.test {
    maxHeapSize = "1g"
}