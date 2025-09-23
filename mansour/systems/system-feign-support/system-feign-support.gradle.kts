dependencies {
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-web")
    implementation("io.github.openfeign:feign-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation(project(MansourModules.SYSTEM_CORE))
    implementation(project(MansourModules.SYSTEM_WEB))
}
