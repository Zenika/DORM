package com.zenika.dorm.maven.test.model.fixtures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class MavenPathFixtures {

    private static final Logger LOG = LoggerFactory.getLogger(MavenPathFixtures.class);

    public static final String GROUPID1 = "org/apache/wicket";
    public static final String GROUPID2 = "commons-io";
    public static final String GROUPID3 = "org/apache/lucene";
    public static final String GROUPID4 = "org/apache/struts";

    public static final String ARTIFACTID1 = "wicket";
    public static final String ARTIFACTID2 = "commons-io";
    public static final String ARTIFACTID3 = "lucene-memory";
    public static final String ARTIFACTID4 = "struts2-core";

    public static final String VERSION1 = "1.0";
    public static final String VERSION2 = "1.0.0";
    public static final String VERSION3 = "1.0-RC";
    public static final String VERSION4 = "1.0-milestone";
    public static final String VERSION_SNAPSHOT = "1.0-SNAPSHOT";

    public static final String TIMESTAMP = "20110906.080527";

    public static final String BUILDNUMBER = "100";

    public static final String CLASSIFIER2 = "foobar";
    public static final String CLASSIFIER4 = "foo.bar";

    public static final String EXTENSION1 = "jar";
    public static final String EXTENSION2 = "jar.md5";
    public static final String EXTENSION3 = "jar.asc.md5";
    public static final String EXTENSION4 = "a.b.c.d.e.f";

    public static final String URI1 = concat(GROUPID1, ARTIFACTID1, VERSION1, null, null, null, EXTENSION1);
    public static final String URI2 = concat(GROUPID2, ARTIFACTID2, VERSION2, null, null, CLASSIFIER2, EXTENSION2);
    public static final String URI3 = concat(GROUPID3, ARTIFACTID3, VERSION3, null, null, null, EXTENSION3);
    public static final String URI4 = concat(GROUPID4, ARTIFACTID4, VERSION4, null, null, CLASSIFIER4, EXTENSION4);

    public static final String URI_SNAPSHOT1 = concat(GROUPID1, ARTIFACTID1, VERSION_SNAPSHOT, TIMESTAMP,
            BUILDNUMBER, null, EXTENSION1);
    public static final String URI_SNAPSHOT2 = concat(GROUPID2, ARTIFACTID2, VERSION_SNAPSHOT, TIMESTAMP,
            BUILDNUMBER, CLASSIFIER2, EXTENSION2);

    static final String URI_SNAPSHOT4 = concat(GROUPID4, ARTIFACTID4, VERSION_SNAPSHOT, TIMESTAMP,
            BUILDNUMBER, CLASSIFIER4, EXTENSION4);

    private MavenPathFixtures() {
    }

    private static String concat(String groupdId, String artifactId, String version, String timestamp,
                                 String buildNumber, String classifier, String extension) {

        StringBuilder builder = new StringBuilder(100)
                .append(groupdId).append("/")
                .append(artifactId).append("/")
                .append(version).append("/")
                .append(artifactId).append("-")
                .append(version.replace("-SNAPSHOT", ""));

        if (null != timestamp && null != buildNumber) {
            builder.append("-").append(timestamp)
                    .append("-").append(buildNumber);
        }

        if (null != classifier) {
            builder.append("-").append(classifier);
        }

        builder.append(".").append(extension);

        String uri = builder.toString();
        LOG.debug("Generated URI for test : " + uri);

        return uri;
    }
}
