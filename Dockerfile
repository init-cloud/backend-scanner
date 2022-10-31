FROM openjdk:11-jre-slim

RUN apt-get update \
    && apt-get install -y bash python3 python3-venv python3-pip

RUN pip3 install checkov

WORKDIR /app/
COPY ./build/libs/prototype-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080 

ENTRYPOINT ["java","-jar","app.jar"]