# pahilo stage: code build garne (jar file banaune)
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
# Windows ko line-ending le Linux ma jhanjhat nagarun bhanera \r hataune, ani build garne
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw && ./mvnw -q -DskipTests package

# dosro stage: sano image ma matra jar chalaune
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# Render le PORT dinchha, application.properties ma server.port=${PORT:8080} le tyo linchha
ENTRYPOINT ["java", "-jar", "app.jar"]