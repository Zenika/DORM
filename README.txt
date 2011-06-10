Zenika DORM :
- Dorm : Contains the war to be deployed on server and the servlet guice configuration.
- Dorm core : Contains the core with the dao implementation on MongoDB and the dorm generic web service.
- Dorm maven : first POC with maven dorm module, contains the web service for maven artifacts.
- Dorm core test : Tests from core.

Installation from sources :
- Build main project with gradle : ./gradle build
- Launch the war from dorm subproject : ./dorm/build/libs/dorm-xxx.war
- Install and launch MongoDB instance on default port

Web service :
- Dorm :
    - Push artifact : curl -X POST -F"file=artifact.jar" http://localhost:8080/dorm/foo/1.0/foo-1.0.jar
    - Get artifact : curl -X GET http://localhost:8080/dorm/foo/1.0/foo-1.0.jar
- Maven :