dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.springframework.data:spring-data-jpa")
    implementation("org.hibernate.orm:hibernate-core")

    api(project(MansourModules.SYSTEM_DATA))
    implementation(project(MansourModules.SYSTEM_CORE))
}
