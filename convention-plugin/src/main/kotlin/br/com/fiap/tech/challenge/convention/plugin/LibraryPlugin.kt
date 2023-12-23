package br.com.fiap.tech.challenge.convention.plugin

import br.com.fiap.tech.challenge.convention.plugin.extentions.configureJava
import br.com.fiap.tech.challenge.convention.plugin.extentions.configurePublishing
import br.com.fiap.tech.challenge.convention.plugin.extentions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
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

            var githubUsername = System.getProperty(GRADLE_PROPERTIES_USERNAME)

            if (githubUsername.isNullOrBlank() || githubUsername.isEmpty()) {
                githubUsername = System.getenv(ENV_GITHUB_USERNAME)
            }

            var githubPassword = System.getProperty(GRADLE_PROPERTIES_PASSWORD)

            if (githubPassword.isNullOrBlank() || githubPassword.isEmpty()){
                githubPassword = System.getenv(ENV_GITHUB_PASSWORD);
            }

            repositories {
                mavenLocal()
                mavenCentral()

                maven {
                    url = uri("https://maven.pkg.github.com/fiap-postech/domain-common")
                    credentials {
                        username = githubUsername
                        password = githubPassword
                    }
                }

                maven {
                    url = uri("https://maven.pkg.github.com/fiap-postech/rest-common")
                    credentials {
                        username = githubUsername
                        password = githubPassword
                    }
                }

                maven {
                    url = uri("https://maven.pkg.github.com/fiap-postech/gradle-version-catalog")
                    credentials {
                        username = githubUsername
                        password = githubPassword
                    }
                }
            }

            dependencies {
                add("testImplementation", libs.findLibrary("assertj.core").get())
                add("testImplementation", libs.findLibrary("junit-jupiter.api").get())
                add("testRuntimeOnly", libs.findLibrary("junit-jupiter.engine").get())

                add("implementation", libs.findLibrary("mapstruct").get())
                add("compileOnly", libs.findLibrary("lombok").get())

                add("annotationProcessor", libs.findLibrary("lombok").get())
                add("annotationProcessor", libs.findLibrary("mapstruct.processor").get())
                add("annotationProcessor", libs.findLibrary("lombok.mapstruct.binding").get())
            }
        }
    }
}