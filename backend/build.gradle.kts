plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

allprojects {
    group = "bdwas"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(23)
        }
    }

    dependencies {
        //implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-web")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
        implementation("net.datafaker:datafaker:2.4.2")
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.security:spring-security-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        implementation("org.openjdk.jmh:jmh-core:1.37")
        annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
