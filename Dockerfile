FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD jpashop-0.0.1-SNAPSHOP.jar /app.jar
RUN bash -c 'touch target/app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]