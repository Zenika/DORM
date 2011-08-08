package com.zenika.dorm.maven.test.integration;

import com.zenika.dorm.core.test.integration.AbstractIntegrationTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenIntegrationTest.class);

    @Override
    protected String getRootResource() {
        return "maven";
    }

    @Test
    public void simpleTest() {
        LOG.trace("simple test");
    }
}
