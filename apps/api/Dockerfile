FROM eclipse-temurin:20 as install-gradle

WORKDIR /app

COPY gradlew settings.gradle.kts /app/
COPY gradle/ /app/gradle/

RUN /app/gradlew wrapper

FROM eclipse-temurin:20 as builder

WORKDIR /app

COPY . /app/
COPY --from=install-gradle /app/.gradle/ /app/.gradle/
COPY --from=install-gradle /root/.gradle /root/.gradle

RUN /app/gradlew bootJar

FROM eclipse-temurin:20 as runner

WORKDIR /app

COPY --from=builder /app/.gradle /app/
COPY --from=builder /app/build/libs/carres-0.0.1-SNAPSHOT.jar /app/carres.jar

EXPOSE 8080/tcp

HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=2 CMD curl -f http://localhost:8080/actuator/health || exit 1

CMD ["java", "-jar", "/app/carres.jar"]