plugins {
    java
    kotlin("jvm") version "1.3.72"
    application
    id( "com.github.johnrengelman.shadow") version "5.2.0"
}

group = "dev.defvs"
version = "1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6")
    implementation("com.melloware:jintellitype:1.3.9")
}

application {
    mainClassName = "dev.defvs.edclipboard.Main"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    
    shadowJar {
        archiveFileName.set("EDClipboardTool.jar")
    }
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
}