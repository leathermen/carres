plugins {
  java
  id("org.springframework.boot") version "3.0.1"
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
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-devtools")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
  implementation("org.springframework.boot:spring-boot-starter-validation")

  implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.2")

  liquibaseRuntime("org.liquibase:liquibase-core:4.19.0")
  liquibaseRuntime("org.liquibase.ext:liquibase-hibernate6:4.19.0")
  liquibaseRuntime("info.picocli:picocli:4.7.0")
  liquibaseRuntime("org.yaml:snakeyaml:1.33")
  liquibaseRuntime("org.postgresql:postgresql")
  liquibaseRuntime(sourceSets.getByName("main").compileClasspath)
  liquibaseRuntime(sourceSets.getByName("main").runtimeClasspath)
  liquibaseRuntime(sourceSets.getByName("main").output)
  implementation("io.github.daggerok:liquibase-r2dbc-spring-boot-starter-parent:2.1.6")

  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("org.postgresql:r2dbc-postgresql")

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
