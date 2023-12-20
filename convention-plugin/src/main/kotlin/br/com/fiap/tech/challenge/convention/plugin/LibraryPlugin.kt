package br.com.fiap.tech.challenge.convention.plugin

import br.com.fiap.tech.challenge.convention.plugin.extentions.configureJava
import br.com.fiap.tech.challenge.convention.plugin.extentions.configurePublishing
import br.com.fiap.tech.challenge.convention.plugin.extentions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.repositories


class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("java-library")
                apply("maven-publish")
            }

            extensions.configure<PublishingExtension> {
                configurePublishing(this, target)
            }

            extensions.configure<JavaPluginExtension> {
                configureJava(this)
            }

            tasks.named("test", org.gradle.api.tasks.testing.Test::class.java) {
                useJUnitPlatform()
            }


            repositories {
                mavenLocal()
                mavenCentral()
                maven {
                    url = uri("https://maven.pkg.github.com/fiap-postech/domain-common")
                    credentials {
                        username = findProperty(GRADLE_PROPERTIES_USERNAME) as String? ?: System.getenv(ENV_GITHUB_USERNAME)
                        password = findProperty(GRADLE_PROPERTIES_PASSWORD) as String? ?: System.getenv(ENV_GITHUB_PASSWORD)
                    }
                }

                maven {
                    url = uri("https://maven.pkg.github.com/fiap-postech/gradle-version-catalog")
                    credentials {
                        username = findProperty(GRADLE_PROPERTIES_USERNAME) as String? ?: System.getenv(ENV_GITHUB_USERNAME)
                        password = findProperty(GRADLE_PROPERTIES_PASSWORD) as String? ?: System.getenv(ENV_GITHUB_PASSWORD)
                    }
                }
            }

            dependencies {
                add("testImplementation", libs.findLibrary("assertj.core").get())
                add("testImplementation", libs.findLibrary("jupiter.api").get())
                add("testRuntimeOnly", libs.findLibrary("jupiter.engine").get())

                add("implementation", libs.findLibrary("mapstruct").get())
                add("compileOnly", libs.findLibrary("lombok").get())

                add("annotationProcessor", libs.findLibrary("lombok").get())
                add("annotationProcessor", libs.findLibrary("mapstruct.processor").get())
                add("annotationProcessor", libs.findLibrary("lombok.mapstruct.binding").get())
            }
        }
    }
}