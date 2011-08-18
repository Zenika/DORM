package com.zenika.dorm.test.ws;

import com.sun.jersey.api.client.Client;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Manage :
 * - jersey client embedded
 * - tomcat server embedded
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class IntegrationTestWrapper {

    private static final Logger LOG = LoggerFactory.getLogger(IntegrationTestWrapper.class);

    private static final String TOMCAT_WEBAPP_PATH = "dorm/src/main/webapp";
    public static final int TOMCAT_PORT = 8080;

    private static IntegrationTestWrapper instance;

    private Tomcat tomcat;
    private Client jerseyClient;

    public static IntegrationTestWrapper getInstance() {

        if (null == instance) {
            instance = new IntegrationTestWrapper();
        }

        return instance;
    }

    private IntegrationTestWrapper() {

    }

    void startTomcat() {
        tomcat = new Tomcat();
        tomcat.setPort(TOMCAT_PORT);
        tomcat.addWebapp(null, "", new File(TOMCAT_WEBAPP_PATH).getAbsolutePath());

        LOG.debug("Tomcat is starting...");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            LOG.error("Tomcat failed to start");
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.debug("Tomcat started on port " + TOMCAT_PORT + ", waiting for new connection...");
                tomcat.getServer().await();
            }
        });

        thread.start();
    }

    void stopTomcat() {
        LOG.debug("Tomcat is stopping...");
        try {
            tomcat.stop();
        } catch (LifecycleException e) {
            LOG.error("Tomcat failed to stop");
            e.printStackTrace();
        }
        LOG.debug("Tomcat is stopped");
    }

    void startJerseyClient() {
        jerseyClient = Client.create();
    }

    void stopJerseyClient() {
        jerseyClient.destroy();
    }

    public Client getJerseyClient() {
        return jerseyClient;
    }
}
