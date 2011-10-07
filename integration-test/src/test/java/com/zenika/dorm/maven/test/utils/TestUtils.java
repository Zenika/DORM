package com.zenika.dorm.maven.test.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class TestUtils {

    private static final Logger LOG = LoggerFactory.getLogger(TestUtils.class);

    public static void deleteRepository(File file) {
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            throw new RuntimeException("Unable to delete this directory: " + file, e);
        }
    }

    public static void executeLinuxShellCommand(String command) {
        try {
            LOG.info("Starting command: " + command);
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                LOG.info("Console out: " + line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to execute this command:\n\t" + command, e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Unable to execute this command:\n\t" + command, e);
        }
    }

    public static Properties getEnvironment() {
        Properties env = new Properties();
        try {
            env.load(Runtime.getRuntime().exec("env").getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Unable to get the environment variable", e);
        }
        return env;
    }
}
