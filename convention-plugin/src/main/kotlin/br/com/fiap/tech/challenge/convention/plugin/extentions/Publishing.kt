package br.com.fiap.tech.challenge.convention.plugin.extentions

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register

internal fun Project.configurePublishing(publishingExtension: PublishingExtension) {
    publishingExtension.apply {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/fiap-postech/${name}")
                credentials {
                    username = findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                    password = findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }

        publications {
            register<MavenPublication>("gpr") {
                from(components["java"])
            }
        }
    }
}