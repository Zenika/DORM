package com.zenika.dorm.test.ws.resource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.test.ws.IntegrationTestWrapper;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public abstract class AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    public static final String BASE_URL = "http://localhost:" + IntegrationTestWrapper.TOMCAT_PORT;

    protected Client client;

    protected abstract String getResourceUrl();

    @Before
    public void before() {
        if (null == client) {
            client = IntegrationTestWrapper.getInstance().getJerseyClient();
        }
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
