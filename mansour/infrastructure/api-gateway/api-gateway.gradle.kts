dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway-server-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    implementation("io.jsonwebtoken:jjwt-api")
    runtimeOnly("io.jsonwebtoken:jjwt-impl")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson")

    runtimeOnly(
        group = "io.netty",
        name = "netty-resolver-dns-native-macos",
        version = "4.2.6.Final",
        classifier = "osx-aarch_64",
    )

    implementation(project(MansourModules.SYSTEM_WEB))
}
