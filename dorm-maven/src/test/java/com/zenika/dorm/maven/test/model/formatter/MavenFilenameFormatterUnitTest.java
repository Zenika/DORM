package com.zenika.dorm.maven.test.model.formatter;

import com.zenika.dorm.maven.model.formatter.MavenFilenameFormatter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFilenameFormatterUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFilenameFormatterUnitTest.class);

    private String artifactId;
    private String version;
    private String timestamp;
    private String buildNumber;
    private String classifier;
    private String extension;

    private String filename1;
    private String filename2;
    private String filename3;

    @Before
    public void before() {

        artifactId = "foo-bar";
        version = "1.0";
        timestamp = "20110822.152325";
        buildNumber = "1";
        classifier = "test";
        extension = "jar";

        filename1 = artifactId + "-" + version + "-" + timestamp + "-" + buildNumber + "." + extension;
        filename2 = artifactId + "-" + version + "-" + timestamp + "-" + buildNumber + "-" + classifier +
                "." + extension;
    }

    @Test
    public void formatValidFilenameWithoutClassifier() {

        LOG.debug("Test filename to format : " + filename1);

        MavenFilenameFormatter formatter = new MavenFilenameFormatter(filename1);
        formatter.format();

        LOG.debug("Formatted : " + formatter);

    }
}
