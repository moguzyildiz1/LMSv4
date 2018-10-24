FROM maven:latest
ADD . app/
COPY entry-point.sh /usr/local/bin/
RUN ln -s usr/local/bin/entry-point.sh
RUN chmod +x ./usr/local/bin/entry-point.sh
ENTRYPOINT ["./entry-point.sh"]
EXPOSE 8080
#CMD ['cd /usr/src/app']
#CMD ['mvn clean install']
#CMD ['mvn', 'install']
#CMD ['ls']
#CMD ["java=","-Dspring.profiles.active=container","-jar","/usr/src/app/target/Administrator.jar"]