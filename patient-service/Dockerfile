# --- Stage 1: Build ---
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copy only the pom.xml first to cache dependencies (Faster builds)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
# Use -DskipTests to make the build faster
RUN mvn clean package -DskipTests

# --- Stage 2: Run (The Small Part) ---
# We use JRE (Runtime only) and Alpine (Tiny Linux)
FROM eclipse-temurin:21-jre-alpine AS runner
WORKDIR /app

# Use a wildcard (*.jar) so you don't have to change this 
# every time you change the version in your pom.xml
COPY --from=builder /app/target/*.jar ./patient-service.jar

EXPOSE 4000

# Security tip: Run as a non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ENTRYPOINT ["java", "-jar", "patient-service.jar"]