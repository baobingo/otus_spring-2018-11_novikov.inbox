FROM maven:3.6.0-jdk-11-slim

ENV PROJECT_DIR=/home/app
ENV JAR_FILENAME=spring17

COPY pom.xml package.json package-lock.json webpack.config.js $PROJECT_DIR/
COPY src $PROJECT_DIR/src
COPY libs $PROJECT_DIR/libs

RUN mvn -f $PROJECT_DIR/pom.xml -DfinalName=$JAR_FILENAME clean package

FROM openjdk:11.0-jre-slim

ENV JAR_FILENAME=spring17
ENV PROJECT_DIR=/home/app
ENV DB_HOST=$DB_HOST
ENV DB_PORT=$DB_PORT

RUN mkdir -p $PROJECT_DIR

COPY --from=0 $PROJECT_DIR/target/$JAR_FILENAME.jar /usr/local/lib/$JAR_FILENAME.jar
RUN ls -l /usr/local/lib/
EXPOSE 8080

ENTRYPOINT ["java","-Dspring.data.mongodb.host=${DB_HOST}","-Dspring.data.mongodb.port=${DB_PORT}","-jar","/usr/local/lib/spring17.jar"]