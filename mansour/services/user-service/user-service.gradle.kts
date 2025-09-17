dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    runtimeOnly("org.postgresql:postgresql")

    implementation(project(MansourModules.SHARED_DOMAIN))
    implementation(project(MansourModules.SYSTEM_CORE))
    implementation(project(MansourModules.SYSTEM_WEB))
    implementation(project(MansourModules.SYSTEM_WEBMVC))

    implementation(project(MansourModules.CONTRACTS_USER_API))
}
