FROM openjdk:22

WORKDIR /app

ADD target/adoptpet.jar /app/adoptpet.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/adoptpet.jar"]