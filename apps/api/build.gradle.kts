plugins {
  java
  id("org.springframework.boot") version "3.0.2"
  id("io.spring.dependency-management") version "1.1.0"
  id("io.freefair.lombok") version "6.6.1"

  id("org.liquibase.gradle") version "2.1.1"
  id("org.sonarqube") version "3.5.0.2730"
  jacoco
}

group = "com.nikitades"

version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_19

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

  implementation("org.liquibase:liquibase-core:4.19.0")
  implementation("org.liquibase.ext:liquibase-hibernate6:4.19.0")
  liquibaseRuntime("org.postgresql:postgresql")
  liquibaseRuntime("info.picocli:picocli:4.7.0")
  liquibaseRuntime(sourceSets.getByName("main").compileClasspath)
  liquibaseRuntime(sourceSets.getByName("main").runtimeClasspath)
  liquibaseRuntime(sourceSets.getByName("main").output)

  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("org.postgresql:r2dbc-postgresql")

  runtimeOnly("com.h2database:h2")
  runtimeOnly("io.r2dbc:r2dbc-h2")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
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
    property("sonar.sources", "src/main/")
    property("sonar.tests", "src/test/")
    property("sonar.java.source", "19")
    property("sonar.projectName", "Cars Reservation API")
    property("sonar.host.url", "https://sonarcloud.io")
  }
}

liquibase {
  activities.register("main")
  runList = "main"
}
