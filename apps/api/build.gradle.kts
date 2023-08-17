plugins {
  java
  id("org.springframework.boot") version "3.1.2"
  id("io.spring.dependency-management") version "1.1.0"
  id("io.freefair.lombok") version "8.0.1"

  id("org.sonarqube") version "4.3.0.3225"
  jacoco
}

group = "com.nikitades"

version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_20

repositories { mavenCentral() }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-devtools")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")

  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

  implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0-rc2")

  // keycloak admin client
  implementation("org.keycloak:keycloak-admin-client:21.1.1")

  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("com.h2database:h2")

  runtimeOnly("io.micrometer:micrometer-registry-prometheus")

  implementation("org.flywaydb:flyway-core:9.15.1")

  annotationProcessor("org.projectlombok:lombok")
  compileOnly("org.projectlombok:lombok")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("org.instancio:instancio-junit:2.11.0")

  testAnnotationProcessor("org.projectlombok:lombok")
  testCompileOnly("org.projectlombok:lombok")
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.jacocoTestReport {
  dependsOn(tasks.test)
  reports {
    xml.required.set(true)
    csv.required.set(false)
    html.required.set(false)
  }
}

sonarqube {
  properties {
    property("sonar.projectKey", "leathermen_carres_api")
    property("sonar.organization", "leathermen")
    property("sonar.exclusions", "**/main/resources/db/**")
    property("sonar.coverage.exclusions", "**/*Config.java")
    property("sonar.sources", "src/main/")
    property("sonar.tests", "src/test/")
    property("sonar.java.source", "20")
    property("sonar.projectName", "Cars Reservation API")
    property("sonar.host.url", "https://sonarcloud.io")
  }
}
