Zenika DORM :
- Dorm : Contains the war to be deployed on server and the servlet guice configuration.
- Dorm core : Contains the core with the dao implementation on MongoDB and the dorm generic web service.
- Dorm maven : first POC with maven dorm module, contains the web service for maven artifacts.

Installation from sources :
- Build main project with gradle : gradle build
- Install the war from dorm subproject to a servlet container. The location of the war should be :
dorm/build/libs/dorm-xxx.war

Web service :

- Dorm :
    - Push artifact :
        curl -X POST http://localhost:8080/dorm/nametest/versiontest
        curl -X POST -F"file=artifact.jar" http://localhost:8080/dorm/foo/1.0/foo-1.0.jar

- Maven :

JDBC Dao :

- Download and install postgres database version >= 8.4.
- Execute the dao_jdbc/postgresql_create.sql script to init the database and all update_xxx.sql.

Neo4j Dao :

- Download neo4j graph database community version 1.4.1 and uncompress it
- Launch neo4j in standalone mode on the default port 7474 : neo4j_folder/bin/neo4j start
