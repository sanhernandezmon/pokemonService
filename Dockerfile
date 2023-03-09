FROM fundingsocietiesdocker/openjdk19-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/pokemonService-0.0.1-SNAPSHOT.jar
EXPOSE 8080

EXPOSE 5432
# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar


# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]