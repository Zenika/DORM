package com.zenika.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.StopExecutionException

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
class Neo4jStart extends Neo4jAbstractTask {

    @TaskAction
    def start() {
        if (validate() && neo4jIsStop()) {
            runner.runCmd("neo4j start", getNeo4jBinFolder())
        }
    }


}
