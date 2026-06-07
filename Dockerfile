# ─── Build Stage ────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-25 AS builder

WORKDIR /build

# Baixa dependências em cache separado do código-fonte
COPY pom.xml .
RUN mvn dependency:go-offline -B -q

COPY src src
RUN mvn package -DskipTests -B -q

# ─── Runtime Stage ───────────────────────────────────────────────────────────
FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder --chown=appuser:appgroup /build/target/*.jar app.jar

USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", \
    "-XX:+UseZGC", \
    "-Xmx512m", \
    "-jar", "app.jar"]
