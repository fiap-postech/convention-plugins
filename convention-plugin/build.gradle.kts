import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "tech.challenge"
version = "1.0.2"

plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
}

gradlePlugin {
    website = "https://github.com/fiap-postech/gradle-plugins"
    vcsUrl = "https://github.com/fiap-postech/gradle-plugins"

    plugins {
        register("libraryPlugin") {
            id = "tech.challenge.library"
            displayName = "Tech Challenge Library Plugin"
            description = "Plugin to convention Java Library Structure"
            tags = listOf("java", "library", "convention")
            implementationClass = "br.com.fiap.tech.challenge.convention.plugin.LibraryPlugin"
        }
        register("microservicePlugin") {
            id = "tech.challenge.microservice"
            displayName = "Tech Challenge Microservice Plugin"
            description = "Plugin to convention Java Microservice Structure"
            tags = listOf("java", "microservice", "convention")
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

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/fiap-postech/gradle-plugins")
            credentials {
                username = findProperty("gprUser") as String? ?: System.getenv("GITHUB_ACTOR")
                password = findProperty("gprKey") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
