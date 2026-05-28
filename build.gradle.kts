plugins {
    kotlin("jvm") version "2.0.21"
    id("io.ktor.plugin") version "3.0.3"
    kotlin("plugin.serialization") version "2.0.21"
    application
}

group = "com.example"
version = "1.0.0"

application {
    mainClass.set("com.example.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor server
    implementation("io.ktor:ktor-server-core-jvm:3.0.3")
    implementation("io.ktor:ktor-server-netty-jvm:3.0.3")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:3.0.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:3.0.3")
    implementation("io.ktor:ktor-server-auth-jvm:3.0.3")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:3.0.3")
    implementation("io.ktor:ktor-server-call-logging-jvm:3.0.3")
    implementation("io.ktor:ktor-server-status-pages-jvm:3.0.3")
    implementation("io.ktor:ktor-server-cors-jvm:3.0.3")
    // JWT
    implementation("com.auth0:java-jwt:4.4.0")
    // Exposed ORM
    implementation("org.jetbrains.exposed:exposed-core:0.55.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.55.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.55.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.55.0")
    // PostgreSQL driver
    implementation("org.postgresql:postgresql:42.7.4")
    // HikariCP connection pool
    implementation("com.zaxxer:HikariCP:6.0.0")
    implementation("org.mindrot:jbcrypt:0.4")
    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.6")
    // Testing
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host-jvm:3.0.3")
}

kotlin {
    jvmToolchain(21)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
