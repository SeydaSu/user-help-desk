FROM openjdk:17-slim

WORKDIR /app
COPY target/*.jar app.jar
COPY .env .env
RUN addgroup --system spring && adduser --system spring --ingroup spring
RUN chown -R spring:spring /app
USER spring:spring
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "app.jar"]
