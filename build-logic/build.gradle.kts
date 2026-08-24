plugins {
    alias(libs.plugins.kotlinJvm)
    `java-gradle-plugin`
}
gradlePlugin {
    plugins {
        create("sentinel") {
            id = "com.sildeag.sentinel"
            implementationClass = "com.sildeag.sentinel.SentinelPlugin"
        }
    }
}
