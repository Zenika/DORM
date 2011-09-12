package com.zenika.dorm.test.ws.resource;

import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class DormResourceIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(DormResourceIntegrationTest.class);

    private static final String DORM_URL = BASE_URL + "/dorm";

    @Override
    protected String getResourceUrl() {
        return DORM_URL;
    }
}
