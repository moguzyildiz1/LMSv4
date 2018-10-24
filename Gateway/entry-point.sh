#!/bin/bash
cd app
mvn clean install
java -Dspring.profiles.active=container -jar target/Gateway.jar