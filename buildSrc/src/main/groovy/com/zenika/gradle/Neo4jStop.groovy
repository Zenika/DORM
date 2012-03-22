package com.zenika.gradle

import org.gradle.api.tasks.TaskAction

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
class Neo4jStop extends Neo4jAbstractTask{

    @TaskAction
    def stop() {
        if (validate() && !neo4jIsStop()) {
            runner.runCmd("neo4j stop", getNeo4jBinFolder())
        }
    }
}


