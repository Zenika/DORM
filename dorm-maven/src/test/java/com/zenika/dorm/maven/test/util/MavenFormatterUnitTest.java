package com.zenika.dorm.maven.test.util;

import com.zenika.dorm.maven.util.MavenFormatter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFormatterUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFormatterUnitTest.class);

    private String artifactId;
    private String version;
    private String timestamp;
    private String buildNumber;
    private String classifier;

    private String filenameWithoutExtension;
    private String filenameWithClassifierAndWithoutExtension;
    private String filenameWithoutClassifierAndWithExtension;
    private String filenameWithClassifierAndExtension;

    @Before
    public void before() {

        artifactId = "foo";
        version = "1.0";
        timestamp = "20110822.152325";
        buildNumber = "1";
        classifier = "bar";

        filenameWithoutExtension = artifactId + "-" + version + "-" + timestamp + "-" + buildNumber;
        filenameWithClassifierAndWithoutExtension = filenameWithoutExtension + "-" + classifier;
        filenameWithoutClassifierAndWithExtension = filenameWithoutExtension + ".jar";
        filenameWithClassifierAndExtension = filenameWithClassifierAndWithoutExtension + ".jar";
    }

    @Test
    public void getBuildNumberFromValidFilename() {

        LOG.debug("Test filename : " + filenameWithoutClassifierAndWithExtension);
        String buildNumber = MavenFormatter.getBuildNumber(filenameWithoutClassifierAndWithExtension);

    }
}
