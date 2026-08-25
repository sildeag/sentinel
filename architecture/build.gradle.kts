plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.sildeag.sentinel.architecture.MainKt")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    // scanners, shared logic, etc.
}

dependencies {
    // If your scanners live in another module, reference them here.
    // Otherwise, scanners can live inside this module.
}
