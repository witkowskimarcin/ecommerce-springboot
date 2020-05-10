FROM openjdk:11
ADD target/Shop-0.0.2.jar
EXPOSE 8000
CMD java -jar Shop-0.0.2.jar --envname=prod