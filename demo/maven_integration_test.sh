#!/bin/bash
while getopts "p:h:P:u:" optname
  do
    case "$optname" in
      "p")
	PORT=$OPTARG
	;;
      "h")
	HOST=$OPTARG
	;;
      "P")
	ROOT_PATH=$OPTARG
	;;
      "u")
	USER_DORM=$OPTARG
	;;
      "pw")
	PASSWORD_DORM=$OPTARG
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
if [ -z "$HOST" ]; then
  HOST="localhost"
fi
if [ -z "$PORT" ]; then
  PORT="8080"
fi
if [ -z "$ROOT_PATH" ]; then
  ROOT_PATH="dorm-server/maven"
fi
if [ -z "$USER_DORM" ]; then
  USER_DORM="admin"
fi
if [ -z "$PASSWORD_DORM" ]; then 
  PASSWORD_DORM="password"
fi
export "$HOST"
export PORT=$PORT
export ROOT_PATH=$ROOT_PATH
export "$USER_DORM"
export "$PASSWORD_DORM"
java -jar maven_importer.jar -h "$HOST" -p "$PORT" -P "$ROOT_PATH" -l lib_to_deploy/ -u "$USER_DORM" -pwd "$PASSWORD_DORM"
groovy set_maven_setting.groovy -install -u "$USER_DORM" -pw "$PASSWORD_DORM"
mvn archetype:create -DgroupId=com.zenika.test -DartifactId=test -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeGroupId=org.apache.maven.archetypes
sed -e '/<\/dependencies>/a\
  <repositories>\
    <repository>\
      <id>DORM-Maven-integration-test<\/id>\
      <url>http:\/\/'"$HOST"':'"$PORT"'\/'"$ROOT_PATH"'<\/url>\
    <\/repository>\
  <\/repositories>\
  <distributionManagement>\
    <repository>\
      <id>DORM-Maven-integration-test<\/id>\
      <url>http://'"$HOST"':'"$PORT"'/'"$ROOT_PATH"'<\/url>\
    <\/repository>\
  <\/distributionManagement>
  /<dependencies>/a\
    <dependency>\
      <groupId>commons-cli<\/groupId>\
      <artifactId>commons-cli<\/artifactId>\
      <version>1.2<\/version>\
    <\/dependency>\
    <dependency>\
      <groupId>commons-collections<\/groupId>\
      <artifactId>commons-collections<\/artifactId>\
      <version>3.2.1<\/version>\
    <\/dependency>' test/pom.xml > test/pom2.xml
mv test/pom2.xml test/pom.xml
cd test
mvn install
mvn deploy
cd ..
groovy set_maven_setting.groovy -delete
rm -rf test
