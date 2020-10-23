FROM gradle:4 AS GRADLE_BUILD
COPY ./ ./
RUN gradle shadowJar

FROM openjdk:8-jre-alpine3.9

COPY --from=GRADLE_BUILD /build/libs/Discord-JDA-1.0-SNAPSHOT-all.jar /discordbot.jar

CMD["java", "-jar", "/discordbot.jar"]
