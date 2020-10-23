FROM gradle:6.7.0-jdk11 AS GRADLE_BUILD
COPY ./ ./
RUN gradle shadowJar

FROM openjdk:16-jdk-alpine

COPY --from=GRADLE_BUILD /build/libs/Discord-JDA-1.0-SNAPSHOT-all.jar /discordbot.jar

ENTRYPOINT ["java", "-jar", "/discordbot.jar"]
