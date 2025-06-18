# Temel imaj
FROM openjdk:17
WORKDIR /app

# Maven ile build ettiğin jar dosyasını kopyala
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "app.jar"]
