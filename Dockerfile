# Étape 1 : Build du projet avec Maven
FROM maven:3-eclipse-temuri-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Déploiement avec Tomcat
FROM tomcat:10-jdk21-slim
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/forum-jee.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
