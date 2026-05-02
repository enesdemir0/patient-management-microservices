# --- Stage 1: Build (Changed to standard Linux to support gRPC compilation) ---
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy only the pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Run (Keeping Alpine for tiny image size) ---
FROM eclipse-temurin:21-jre-alpine AS runner
WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/target/*.jar ./billing-service.jar

# REST and gRPC ports
EXPOSE 4001
EXPOSE 9001

# Security: Run as non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ENTRYPOINT ["java", "-jar", "billing-service.jar"]