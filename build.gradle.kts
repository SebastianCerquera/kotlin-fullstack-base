import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("multiplatform") version kotlinVersion
    id("io.spring.dependency-management") version System.getProperty("dependencyManagementPluginVersion")
    id("org.springframework.boot") version System.getProperty("springBootVersion")
    kotlin("plugin.spring") version kotlinVersion

    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
}

version = "1.0.0-SNAPSHOT"
group = "com.nutcrackers"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    add("kspCommonMainMetadata", "dev.fritz2:lenses-annotation-processor:1.0-RC4")
}

// Versions
val kotlinVersion: String by System.getProperties()
val coroutinesVersion: String by project
val r2dbcPostgresqlVersion: String by project
val r2dbcH2Version: String by project
val e4kVersion: String by project

extra["kotlin.version"] = kotlinVersion
extra["kotlin-coroutines.version"] = coroutinesVersion

val webDir = file("src/frontendMain/web")
val mainClassName = "com.example.MainKt"

kotlin {
    jvm("backend") {
        withJava()
        compilations.all {
            java {
                targetCompatibility = JavaVersion.VERSION_17
            }
            kotlinOptions {
                jvmTarget = "17"
                freeCompilerArgs = listOf("-Xjsr305=strict")
            }
        }
    }
    js("frontend") {
        browser {
            runTask {
                outputFileName = "main.bundle.js"
                sourceMaps = false
                devServer = KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 3000,
                    //proxy = mutableMapOf(
                    //    "/kv/*" to "http://localhost:8080",
                    //    "/login" to "http://localhost:8080",
                    //    "/logout" to "http://localhost:8080",
                    //    "/kvws/*" to mapOf("target" to "ws://localhost:8080", "ws" to true)
                    //),
                    static = mutableListOf("$buildDir/processedResources/frontend/main")
                )
            }
            webpackTask {
                outputFileName = "main.bundle.js"
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("dev.fritz2:core:1.0-RC4")
                implementation("dev.fritz2:lenses-annotation-processor:1.0-RC4")
                implementation("org.springframework.boot:spring-boot-starter-data-jpa")
                implementation("org.hibernate:hibernate-core:6.2.0.CR2")
                compileOnly("org.projectlombok:lombok:1.18.26")
                //create("kspCommonMainMetadata", )
            }
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val backendMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk7"))
                implementation(kotlin("reflect"))
                //web
                implementation("org.springframework.boot:spring-boot-starter-web")

                //web
                implementation("org.springframework.boot:spring-boot-starter-validation")

                //kotlin
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
                implementation("org.jetbrains.kotlin:kotlin-reflect")
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

                //validator
                implementation("org.springframework.boot:spring-boot-starter-validation")

                //logging
                implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")


                //db
                implementation("org.springframework.boot:spring-boot-starter-data-jpa")
                runtimeOnly("com.h2database:h2")
//    runtimeOnly("org.postgresql:postgresql")

                // https://mvnrepository.com/artifact/org.projectlombok/lombok
                compileOnly("org.projectlombok:lombok:1.18.26")



            }
        }
        val backendTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("org.springframework.boot:spring-boot-starter-test")
            }
        }
        val frontendMain by getting {
            resources.srcDir(webDir)
            dependencies {
                implementation("dev.fritz2:core:1.0-RC4")
                //implementation("dev.fritz2:lenses-annotation-processor:1.0-RC4")
            }
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
        val frontendTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

afterEvaluate {
    tasks {
        create("frontendArchive", Jar::class).apply {
            dependsOn("frontendBrowserProductionWebpack")
            group = "package"
            archiveAppendix.set("frontend")
            val distribution =
                project.tasks.getByName("frontendBrowserProductionWebpack", KotlinWebpack::class).destinationDirectory!!
            from(distribution) {
                include("*.*")
            }
            from(webDir)
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            into("/public")
            inputs.files(distribution, webDir)
            outputs.file(archiveFile)
            manifest {
                attributes(
                    mapOf(
                        "Implementation-Title" to rootProject.name,
                        "Implementation-Group" to rootProject.group,
                        "Implementation-Version" to rootProject.version,
                        "Timestamp" to System.currentTimeMillis()
                    )
                )
            }
        }
        getByName("backendProcessResources", Copy::class) {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
        getByName("bootJar", BootJar::class) {
            dependsOn("frontendArchive", "backendMainClasses")
            classpath = files(
                kotlin.targets["backend"].compilations["main"].output.allOutputs +
                        project.configurations["backendRuntimeClasspath"] +
                        (project.tasks["frontendArchive"] as Jar).archiveFile
            )
        }
        getByName("jar", Jar::class).apply {
            dependsOn("bootJar")
        }
        getByName("bootRun", BootRun::class) {
            dependsOn("backendMainClasses")
            classpath = files(
                kotlin.targets["backend"].compilations["main"].output.allOutputs +
                        project.configurations["backendRuntimeClasspath"]
            )
        }
        create("backendRun") {
            dependsOn("bootRun")
            group = "run"
        }
    }
}


