plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jlleitschuh.gradle.ktlint") version "13.1.0"
    id("idea")
}

idea {
    module {
        isDownloadSources = true
    }
}
// 서브 모듈이지만 directory인 모듈들
val directoryModules =
    arrayOf(
        ":mansour",
        ":mansour:infrastructure",
        ":mansour:services",
        ":mansour:systems",
        ":mansour:contracts",
    )

allprojects {
    group = "org.mj.mansour"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("io.spring.dependency-management")
    }

    kotlin {
        jvmToolchain(21)
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.5.5")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2025.0.0")
        }

        dependencies {
            dependency("io.github.oshai:kotlin-logging-jvm:7.0.13")
            dependency("net.javacrumbs.shedlock:shedlock-spring:6.10.0")
            dependency("net.javacrumbs.shedlock:shedlock-provider-redis-spring:6.10.0")
            dependency("com.influxdb:influxdb-client-java:7.3.0")
        }

        dependencies {
            dependencySet("io.jsonwebtoken:0.13.0") {
                entry("jjwt-api")
                entry("jjwt-impl")
                entry("jjwt-jackson")
            }
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    // jar 태스크 비활성화
    if (project.path in directoryModules) {
        tasks.jar {
            enabled = false
        }
    }

    // 스프링 부트 애플리케이션이 될 모듈들의 부모 경로 목록 정의
    val bootModuleParentPaths =
        listOf(
            ":mansour:services",
            ":mansour:infrastructure",
        )

    // 스프링 부트 모듈들에 대해 스프링 부트 플러그인 적용
    if (bootModuleParentPaths.any { parentPath -> project.path.startsWith("$parentPath:") }) {
        apply(plugin = "org.springframework.boot")

        tasks.jar {
            enabled = false
        }
    }
}

// 빌드 시 루트 프로젝트 jar 생성 되지 않게
tasks.jar {
    enabled = false
}
// 빌드 시 루트 프로젝트 bootJar 생성 되지 않게
tasks.bootJar {
    enabled = false
}
