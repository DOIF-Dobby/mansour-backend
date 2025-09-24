dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.kafka:spring-kafka")

    testImplementation("org.springframework.kafka:spring-kafka-test")


    runtimeOnly("org.postgresql:postgresql")

    implementation(project(MansourModules.SHARED_DOMAIN))
    implementation(project(MansourModules.SYSTEM_CORE))
    implementation(project(MansourModules.SYSTEM_WEB))
    implementation(project(MansourModules.SYSTEM_WEBMVC))
    implementation(project(MansourModules.SYSTEM_DATA_JPA))
    implementation(project(MansourModules.SYSTEM_FEIGN_SUPPORT))
    implementation(project(MansourModules.SYSTEM_KAFKA))
    implementation(project(MansourModules.CONTRACTS_ACTIVITY_SERVICE))
    implementation(project(MansourModules.CONTRACTS_ASSET_SERVICE))
    implementation(project(MansourModules.CONTRACTS_MARKET_DATA_SERVICE))
}
