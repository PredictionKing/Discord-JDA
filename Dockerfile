FROM gradle:6.7.0-jdk11 AS GRADLE_BUILD
COPY . /app/source
WORKDIR /app/source
RUN gradle shadowJar

FROM openjdk:16-jdk-alpine

COPY --from=GRADLE_BUILD /app/source/build/libs/*.jar /app/discordbot.jar

ENTRYPOINT ["java", "-jar", "/app/discordbot.jar"]
