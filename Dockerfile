FROM openjdk:11-jre-slim

RUN apt-get update \
    && apt-get install -y bash python3 python3-venv python3-pip

RUN pip3 install checkov

WORKDIR /app/

COPY ./build/libs/prototype-1.0.5.jar ./app.jar
COPY ./uploads/aws ./external/aws
COPY ./uploads/ncp ./external/ncp
COPY ./uploads/openstack ./external/openstack
COPY ./uploads/none ./external/none

EXPOSE 8080 

ENTRYPOINT ["java","-jar","./app.jar"]