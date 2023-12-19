import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "tech.challenge"
version = "1.0.0"

plugins {
    `kotlin-dsl`
    `maven-publish`
    id("com.gradle.plugin-publish") version("0.16.0")

}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("libraryPlugin") {
            id = "tech.challenge.library"
            implementationClass = "br.com.fiap.tech.challenge.convention.plugin.LibraryPlugin"
        }
        register("microservicePlugin") {
            id = "tech.challenge.microservice"
            implementationClass = "br.com.fiap.tech.challenge.convention.plugin.MicroservicePlugin"
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
