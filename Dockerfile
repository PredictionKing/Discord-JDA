FROM gradle:4 AS GRADLE_BUILD
COPY ./ ./
RUN gradle shadowJar

FROM openjdk:16-jdk-alpine

COPY --from=GRADLE_BUILD /build/libs/Discord-JDA-1.0-SNAPSHOT-all.jar /discordbot.jar

RUN["java", "-jar", "/discordbot.jar"]
