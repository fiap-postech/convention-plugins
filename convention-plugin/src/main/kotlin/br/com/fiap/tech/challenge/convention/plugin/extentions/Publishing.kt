package br.com.fiap.tech.challenge.convention.plugin.extentions

import br.com.fiap.tech.challenge.convention.plugin.ENV_GITHUB_PASSWORD
import br.com.fiap.tech.challenge.convention.plugin.ENV_GITHUB_USERNAME
import br.com.fiap.tech.challenge.convention.plugin.GRADLE_PROPERTIES_USERNAME
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register

internal fun Project.configurePublishing(publishingExtension: PublishingExtension, project: Project) {
    publishingExtension.apply {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/fiap-postech/${project.name}")
                credentials {
                    username = findProperty(GRADLE_PROPERTIES_USERNAME) as String? ?: System.getenv(ENV_GITHUB_USERNAME)
                    password = findProperty(GRADLE_PROPERTIES_USERNAME) as String? ?: System.getenv(ENV_GITHUB_PASSWORD)
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