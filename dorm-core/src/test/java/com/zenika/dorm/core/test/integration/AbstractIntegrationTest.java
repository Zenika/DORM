package com.zenika.dorm.core.test.integration;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    private static final String LOCALHOST = "http://localhost:9191/";

    private static HttpServer server;

    protected abstract String getRootResource();

    @BeforeClass
    public static void setUp() throws IOException {
        server = HttpServerFactory.create(LOCALHOST);

        LOG.trace("Start server for tests at : " + LOCALHOST);
        server.start();
    }

    @AfterClass
    public static void tearDown() {
        LOG.trace("Stop test server now");
        server.stop(0);
    }

    public String requestResource(String resource, String method) throws Exception {

        String urlAsString = LOCALHOST + getRootResource() + "/" + resource;
        LOG.trace("Request url : " + urlAsString);

        URL url = new URL(urlAsString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(method);
        connection.connect();

        InputStream inputStream = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String firstLineOfText = reader.readLine();

        connection.disconnect();
        return firstLineOfText;
    }
}
