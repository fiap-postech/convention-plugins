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

            repositories {
                mavenLocal()
                mavenCentral()
                maven {
                    url = uri("https://maven.pkg.github.com/fiap-postech/domain-common")
                    credentials {
                        username = findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                        password = findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
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