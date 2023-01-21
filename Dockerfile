FROM openjdk:11-jre-slim

RUN apt-get update \
    && apt-get install -y bash python3 python3-venv python3-pip

RUN pip3 install checkov

WORKDIR /app/

COPY ./build/libs/scanner-2.0.0.jar ./scanner.jar
COPY ./volume/uploads/aws ./external/aws
COPY ./volume/uploads/ncp ./external/ncp
COPY ./volume/uploads/openstack ./external/openstack
COPY ./volume/uploads/none ./external/none

EXPOSE 8080 

ENTRYPOINT ["java","-jar","./scanner.jar"]