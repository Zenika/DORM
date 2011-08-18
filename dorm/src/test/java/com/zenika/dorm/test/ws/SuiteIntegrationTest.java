package com.zenika.dorm.test.ws;

import com.zenika.dorm.test.ws.resource.DormResourceIntegrationTest;
import com.zenika.dorm.test.ws.resource.MavenResourceIntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DormResourceIntegrationTest.class, MavenResourceIntegrationTest.class
})
public class SuiteIntegrationTest {

    @BeforeClass
    public void beforeClass() {
        IntegrationTestWrapper.getInstance().startTomcat();
        IntegrationTestWrapper.getInstance().startJerseyClient();
    }

    @AfterClass
    public void afterClass() {
        IntegrationTestWrapper.getInstance().stopJerseyClient();
        IntegrationTestWrapper.getInstance().stopTomcat();
    }
}
