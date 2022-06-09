FROM openjdk:8 AS BUILD_IMAGE
ENV APP_HOME=/root/dev/snacky
RUN mkdir -p $APP_HOME/src/main/java
WORKDIR $APP_HOME
COPY build.gradle gradlew gradlew.bat $APP_HOME
COPY gradle $APP_HOME/gradle
COPY ./src $APP_HOME/src
RUN ./gradlew clean build -x test

FROM openjdk:8-jre-alpine
WORKDIR /root/
COPY --from=BUILD_IMAGE /root/dev/snacky/build/libs/snacky* ./snacky.jar
EXPOSE 8080
CMD ["java","-jar","snacky.jar"]
