package com.zenika.dorm.test.ws;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.fest.assertions.Assertions;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public abstract class TomcatIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(TomcatIntegrationTest.class);

    private static final String WEBAPP_PATH = "dorm/src/main/webapp";
    private static Tomcat tomcat;

    public static final int PORT = 8080;
    public static final String BASE_URL = "http://localhost:" + PORT;

    protected Client client;

    protected abstract String getResourceUrl();

    @BeforeClass
    public static void beforeClass() throws Exception {
        startTomcat();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        LOG.debug("Tomcat is stopping...");
        tomcat.stop();
        LOG.debug("Tomcat is stopped");
    }

    @Before
    public void before() {
        if (null == client) {
            client = Client.create();
        }
    }

    private static void startTomcat() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(PORT);
        tomcat.addWebapp(null, "", new File(WEBAPP_PATH).getAbsolutePath());

        LOG.debug("Tomcat is starting...");
        tomcat.start();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.debug("Tomcat started on port " + PORT + ", waiting for new connection...");
                tomcat.getServer().await();
            }
        });

        thread.start();
    }

    @Test
    public void simplePing() {
        LOG.debug("Ping resource : " + getResourceUrl());
        WebResource resource = client.resource(getResourceUrl() + "/ping");
        ClientResponse response = resource.get(ClientResponse.class);

        Assertions.assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
        Assertions.assertThat(response.getEntity(String.class)).isEqualTo("pong");
    }
}
