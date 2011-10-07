package com.zenika.dorm.maven.test.grinder;

import com.zenika.dorm.maven.test.utils.TestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class GrinderNeo4jStarter {

    public static void startNeo4j(String neo4jHome, boolean resetData) {
        String neo4jScript = neo4jHome + "bin/neo4j";
        if (resetData) {
            stopNeo4j(neo4jScript);
            deleteDatabase(neo4jHome);
            startNeo4j(neo4jScript);
        } else {
            startNeo4j(neo4jScript);
        }
    }

    private static void stopNeo4j(String neo4jScript) {
        TestUtils.executeLinuxShellCommand(neo4jScript + " stop");
    }

    private static void startNeo4j(String neo4jScript) {
        TestUtils.executeLinuxShellCommand(neo4jScript + " start");
    }

    private static void deleteDatabase(String neo4jHome) {
        File dataDirectory = new File(neo4jHome + "data/graph.db");
        Collection<File> files = FileUtils.listFiles(dataDirectory, null, true);
        for (File file : files) {
            file.delete();
        }
    }
}
