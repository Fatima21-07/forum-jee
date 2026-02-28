# Étape 1 : Build
FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Tomcat
FROM tomcat:10.1-jdk21-corretto
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/forum-jee.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
