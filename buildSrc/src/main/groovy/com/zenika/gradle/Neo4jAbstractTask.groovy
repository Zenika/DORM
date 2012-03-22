package com.zenika.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.StopExecutionException

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
abstract class Neo4jAbstractTask extends DefaultTask {

    private static final String NEO4J_STOP_STATUS = "Neo4j Server is not running\n"

    def runner = new CommandRunner()
    def neo4jHome

    def validate() {
        if (getNeo4jHome() == null | getNeo4jHome().isEmpty()) {
            throw new StopExecutionException('Please set NEO4J_HOME environnement variable')
        }
        return true
    }

    def neo4jIsStop() {
        return runner.runCmdWithStringReturn("neo4j status", getNeo4jBinFolder()).equals(NEO4J_STOP_STATUS)
    }

    def getNeo4jHome() {
        neo4jHome != null ? neo4jHome : System.getenv("NEO4J_HOME")
    }

    def getNeo4jBinFolder() {
        getNeo4jHome() + "/bin"
    }
}
