#!/bin/bash
while getopts "p:h:" optname
  do
    case "$optname" in
      "p")
	PORT=$OPTARG
	;;
      "h")
	HOST=$OPTARG
	;;
      "?")
	echo "Unknow option $OPTARG"
	;;
      ":")
	echo "No argument value for option $OPTARG"
	;;
      *)
	echo "Unknown error while processing options"
	;;
    esac
  done
java -jar maven_importer.jar -h localhost -p 8080 -P dorm-server/maven -l lib_to_deploy/ -u admin -pwd password
mvn archetype:create -DgroupId=com.zenika.test -DartifactId=test -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeGroupId=org.apache.maven.archetypes
sed -f sed_script test/pom.xml > test/pom2.xml
mv test/pom2.xml test/pom.xml
cd test
mvn install
mvn deploy
