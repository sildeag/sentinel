plugins {
    `java-gradle-plugin`
    kotlin("jvm")
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
gradlePlugin {
    plugins {
        create("sentinel") {
            id = "com.sildeag.sentinel"
            implementationClass = "com.sildeag.sentinel.architecture.SentinelPlugin"
        }
    }
}
dependencies {
    // If your scanners live in another module, reference them here.
    // Otherwise, scanners can live inside this module.
}
