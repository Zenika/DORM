package com.zenika.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.Task
import org.gradle.api.Action
import groovy.xml.StreamingMarkupBuilder

class DemoTask extends DefaultTask {

    def pathRoot
    def demoPathRoot
    def runner = new CommandRunner()

    @TaskAction
    def demo() {
        unzipMavenImporter()
        importDemoArtifacts()
        createProjectToDeploy()
        addDependenciesIntoProjectToDeploy()
        mavenInstall()
        mavenDeploy()
        deleteProjectToDeploy()
    }

    def unzipMavenImporter() {
        ant.unzip(src: pathRoot + '/admin/maven-importer/build/distributions/maven-importer-1.0.0-SNAPSHOT.zip',
                dest: pathRoot + '/admin/maven-importer/build/distributions/')
    }

    def importDemoArtifacts() {
        runner.runCmd("java -jar maven-importer-1.0.0-SNAPSHOT.jar -h localhost -p 8080 -P dorm-server/maven -l " + demoPathRoot + "/lib_to_deploy -u admin -pwd password", pathRoot + "/admin/maven-importer/build/distributions/")
    }

    def createProjectToDeploy() {
        runner.runCmd("mvn archetype:create -DgroupId=com.zenika.test -DartifactId=test -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeGroupId=org.apache.maven.archetypes", demoPathRoot)
    }

    def addDependenciesIntoProjectToDeploy() {
        def pom = new File(demoPathRoot + "/test/pom.xml")
        def project = new XmlSlurper().parse(pom)
        def dependencies = project.dependencies
        dependencies.appendNode {
            dependency {
                groupId('commons-cli')
                artifactId('commons-cli')
                version('1.2')
            }
            dependency {
                groupId('commons-collections')
                artifactId('commons-collections')
                version('3.2.1')
            }
        }
        project.appendNode {
            distributionManagement {
                repository {
                    id('Dorm')
                    url('http://localhost:8080/dorm-server/maven')
                }
            }
            repositories {
                repository {
                    id('Dorm')
                    url('http://localhost:8080/dorm-server/maven')
                }
            }
        }
        def outputBuilder = new StreamingMarkupBuilder();
        def defaultNamespace = project.namespaceURI()
        result = outputBuilder.bind {
            namespaces << ["": defaultNamespace]
            mkp.yield project
        }.writeTo(pom.newWriter())
        result.close()
    }

    def mavenInstall() {
        runner.runCmd("mvn -s " + demoPathRoot + "/settings.xml install", demoPathRoot + "/test")
    }

    def mavenDeploy() {
        runner.runCmd("mvn -s " + demoPathRoot + "/settings.xml deploy", demoPathRoot + "/test")
    }

    void deleteProjectToDeploy() {
        ant.delete(dir: demoPathRoot + "/test")
    }
}