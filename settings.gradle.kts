pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()

        maven {
            url = uri("https://maven.pkg.github.com/fiap-postech/gradle-version-catalog")
            credentials {
                val gprUser: String? by settings
                val gprKey: String? by settings

                username = gprUser ?: System.getenv("GITHUB_ACTOR")
                password = gprKey ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }

    versionCatalogs {
        create("sharedLibs") {
            from("br.com.fiap.tech.challenge:gradle-version-catalog:1.0.0")
        }
    }
}

rootProject.name = "gradle-plugins"
include("convention-plugin")
