# Usa una imagen base de Maven con JDK 17 para el proceso de construcción
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia todos los archivos del proyecto desde el directorio actual al contenedor
COPY . .

# Descarga el wrapper de Maven para asegurar compatibilidad de versiones
RUN mvn -N io.takari:maven:wrapper

# Da permisos de ejecución al script mvnw (wrapper de Maven)
RUN chmod +x mvnw

# Ejecuta la construcción del proyecto Maven, omitiendo los tests para ahorrar tiempo
RUN mvn clean install -DskipTests

# Usa una imagen base más ligera de JRE 17 para ejecutar la aplicación
FROM eclipse-temurin:17-jre

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado desde la fase de construcción al contenedor final
COPY --from=build /app/outcome-curr-mgmt/target/outcome-curr-mgmt-1.0-SNAPSHOT.jar /app/outcome-curr-mgmt.jar


# Expone el puerto 8088 para permitir conexiones a la aplicación
EXPOSE 8088

# Comando predeterminado para ejecutar la aplicación
CMD ["java", "-jar", "/app/outcome-curr-mgmt.jar"]
