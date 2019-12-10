FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/jpashop-0.0.1-SNAPSHOP.jar target/app.jar
RUN bash -c 'touch target/app.jar'
ENTRYPOINT ["java","-jar","target/app.jar"]