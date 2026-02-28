# Utiliser Tomcat 10.1 avec JDK 21
FROM tomcat:10.1-jdk21-slim

# Supprimer les applications par défaut de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copier le fichier WAR généré dans le dossier webapps de Tomcat sous le nom ROOT.war
# (ROOT.war permet d'accéder à l'app via / au lieu de /forum-jee)
COPY target/forum-jee.war /usr/local/tomcat/webapps/ROOT.war

# Exposer le port 8080
EXPOSE 8080

# Lancer Tomcat
CMD ["catalina.sh", "run"]
