#FROM eclipse-temurin:21 AS builder
#WORKDIR /app
#COPY . .
#RUN ./mvnw clean package -DskipTests
#FROM eclipse-temurin:21-jre
#WORKDIR /app
#COPY --from=builder /app/target/*.jar room-reservation.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "room-reservation.jar"]