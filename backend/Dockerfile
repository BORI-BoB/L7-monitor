FROM openjdk:17-ea-11-jdk-slim

WORKDIR /server

ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
RUN apt-get install -y tzdata

COPY ./L7-Monitor.jar l7-monitor.jar

ARG HOST_IP

ENV HOST_IP=${HOST_IP}

ENTRYPOINT ["java", "-jar", "l7-monitor.jar", "--spring.datasource.url=jdbc:mysql://bori-db-container/bob_l7?characterEncoding=utf8", "--boribob.host-ip=$HOST_IP"]
