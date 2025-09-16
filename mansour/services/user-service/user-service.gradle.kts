dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    runtimeOnly("org.postgresql:postgresql")

    implementation(project(":mansour:shared-domain"))
    implementation(project(":mansour:systems:system-core"))
    implementation(project(":mansour:systems:system-api"))

    implementation(project(":mansour:contracts:user-service-contract"))
}
