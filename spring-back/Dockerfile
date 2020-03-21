FROM maven:3.6.3-jdk-8
COPY . /home/app/
WORKDIR /home/app/
RUN cd /home/app/src
EXPOSE 8080
CMD ["mvn","jetty:run"]
