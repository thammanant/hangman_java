FROM openjdk:latest
WORKDIR /usr/src/app
COPY . .
RUN javac server.java
EXPOSE 12345
CMD ["java", "server"]
