plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "mansour-backend"

val parentProjectName = "mansour"
val baseDir = File(settingsDir, parentProjectName)
val projectGroupDirs = listOf("infrastructure", "services", "systems", "contracts")

projectGroupDirs.forEach { groupName ->
    val groupDir = File(baseDir, groupName)

    if (groupDir.exists() && groupDir.isDirectory) {
        groupDir.listFiles()?.forEach { projectDir ->
            if (projectDir.isDirectory) {
                val projectName = projectDir.name

                val includePath = "$parentProjectName:$groupName:$projectName" // e.g., "mansour:services:user-service"
                val projectRefPath = ":$includePath" // e.g., ":mansour:services:user-service"
                val buildFileName = "$projectName.gradle.kts"

                include(includePath)
                project(projectRefPath).buildFileName = buildFileName

                println("✅ Discovered and configured project -> path: '$projectRefPath', buildFile: '$buildFileName'")
            }
        }
    }
}

include(parentProjectName)

include("mansour:shared-domain")
project(":mansour:shared-domain").buildFileName = "shared-domain.gradle.kts"
