Zenika DORM prototype
=====================


Project architecture
--------------------

- **admin**
    - dao-jdbc-scripts : sql scripts to create the database structure required by the jdbc dao.
    - maven-importer : tool to import local repository to dorm.
- **core**
    - core : the core sources, generic and independent of all implementations
    - webapp : encapsulatio of dorm as jee webapp with jersey and guice configuration.
- **plugins**
    - ecr : the nuxeo/ecr bundle as the metadata storage of dorm.
    - maven : the maven dorm plugin, which adds the maven logic to the webservice and the model.


Installation from sources
-------------------------

- Build main project with gradle : `gradle build`
- Install the war from dorm subproject to a servlet container. The location of the war should be : `dorm/build/libs/dorm-xxx.war`


Web container
-------------

The project was only tested with tomcat 7 for now but there is nothing specific for that container.


Storages
--------

- **JDBC with PostgreSQL**

    - Download and install postgres database version >= 8.4.
    - Create the database.
    - Execute the sql scripts : postgresql_create.sql and then all the update_xxx.sql.
    - Launch tomcat with following parameters : `-Dpostgres_host=db_host -Dpostgres_port=5432 -Dpostgres_database=db_name -Dpostgres_user=db_user -Dpostgres_password=db_pass`

- **Neo4j**

    - Download neo4j graph database community version 1.4.1 and uncompress it
    - Launch neo4j in standalone mode on the default port 7474 : `neo4j_folder/bin/neo4j start`

- **Nuxeo / ECR**

    - Download and install the nuxeo/ecr server : github.com/nuxeo/org.eclipse.ecr
    - Install and activate the ecr bundle located at plugins/ecr on the ecr server
    - If needed, edit the com.zenika.dorm.core.dao.nuxeo.DormDaoNuxeo to set the hostname and port of


Dorm extension points / plugins
-------------------------------

- **Maven plugin** : Read the maven plugin README located at plugins/maven


