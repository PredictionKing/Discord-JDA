FROM gradle:4
COPY ./ ./
RUN gradle shadowJar
CMD["java", "-jar", "Discord-JDA-1.0-SNAPSHOT-all.jar"]
