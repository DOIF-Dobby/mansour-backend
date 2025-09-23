dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    implementation("io.jsonwebtoken:jjwt-api")
    runtimeOnly("io.jsonwebtoken:jjwt-impl")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson")

    implementation(project(MansourModules.SHARED_DOMAIN))
    implementation(project(MansourModules.SYSTEM_CORE))
    implementation(project(MansourModules.SYSTEM_WEB))
    implementation(project(MansourModules.SYSTEM_WEBMVC))
    implementation(project(MansourModules.SYSTEM_FEIGN_SUPPORT))

    implementation(project(MansourModules.CONTRACTS_USER_SERVICE))
}
