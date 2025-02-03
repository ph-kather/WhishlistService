# --- Build Stage ---
FROM gradle:8.4-jdk21 AS build
WORKDIR /app

# Kopiere alle Dateien
COPY --chown=gradle:gradle . .

# OpenAPI Code generieren
RUN gradle generateServerStub --no-daemon

# Erstelle das Spring Boot Jar-File
RUN gradle bootJar --no-daemon

# --- Runtime Stage ---
FROM eclipse-temurin:21-jdk
WORKDIR /app

# PostgreSQL-Umgebungsvariablen
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/wunschzettel
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=secret
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

# Kopiere das gebaute Jar-File aus dem vorherigen Stage
COPY --from=build /app/build/libs/*.jar app.jar

# Port freigeben (Spring Boot Default: 8080)
EXPOSE 8080

# Spring Boot Anwendung starten
ENTRYPOINT ["java", "-jar", "app.jar"]
