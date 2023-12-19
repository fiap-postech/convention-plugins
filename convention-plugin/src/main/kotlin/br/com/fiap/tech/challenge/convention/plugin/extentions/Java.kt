package br.com.fiap.tech.challenge.convention.plugin.extentions


import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion

internal fun Project.configureJava(javaPluginExtension: JavaPluginExtension) {
    javaPluginExtension.apply {
        withSourcesJar()

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}