# Etapa de Build
FROM maven:3.8.4-openjdk-17-slim AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo pom.xml para a imagem
COPY pom.xml .

# Baixe as dependências do Maven (sem compilar o código, só para aproveitar o cache do Docker)
RUN mvn dependency:go-offline

# Agora copie o restante do código-fonte
COPY src ./src

# Execute o Maven para compilar e empacotar o projeto
RUN mvn clean install -DskipTests

# Etapa de Execução
FROM openjdk:17-jdk-slim

# Exponha a porta em que o aplicativo vai rodar
EXPOSE 8080

# Copie o arquivo .jar da etapa de build para a imagem final
COPY --from=build /app/target/SF-0.0.1-SNAPSHOT.jar /app.jar

# Defina o comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]