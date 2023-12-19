package br.com.fiap.tech.challenge.convention.plugin

import br.com.fiap.tech.challenge.convention.plugin.extentions.configurePublishing
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure

class MicroservicePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("java-library")
                apply("maven-publish")
            }

            extensions.configure<PublishingExtension> {
                configurePublishing(this)
            }
        }
    }
}