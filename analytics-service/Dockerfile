# --- Stage 1: Build (Standard Linux for Protobuf/gRPC support) ---
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy pom.xml and source
COPY pom.xml .
COPY src ./src

# Build the package
RUN mvn clean package -DskipTests

# --- Stage 2: Run (Alpine for tiny image size) ---
FROM eclipse-temurin:21-jre-alpine AS runner
WORKDIR /app

# Copy the jar from the builder
COPY --from=builder /app/target/*.jar ./analytics-service.jar

# Expose the REST port (even if you don't use it yet)
EXPOSE 4002

# Security: Run as non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ENTRYPOINT ["java", "-jar", "analytics-service.jar"]