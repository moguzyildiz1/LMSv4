FROM maven:latest
ADD . /usr/src/app
EXPOSE 8761
ENTRYPOINT ["java","-Dspring.profiles.active=container","-jar","/usr/src/app/target/Eureka.jar"]
CMD ['cd', '/usr/src/app']
CMD ['ls']
CMD ['mvn', 'clean']
CMD ['mvn', 'install']