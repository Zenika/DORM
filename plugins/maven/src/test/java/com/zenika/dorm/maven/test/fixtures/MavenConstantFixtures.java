package com.zenika.dorm.maven.test.fixtures;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenConstantFixtures {

    public static final String GROUPID1 = "org/apache/wicket";
    public static final String GROUPID2 = "commons-io";

    public static final String ARTIFACTID1 = "wicket";
    public static final String ARTIFACTID2 = "commons-io";

    public static final String VERSION_SIMPLE = "1.0";
    public static final String VERSION_TEXT = "1.0-RC";
    public static final String VERSION_SNAPSHOT = "1.0-SNAPSHOT";

    public static final String TIMESTAMP = "20110906.080527";

    public static final String BUILDNUMBER = "100";

    public static final String CLASSIFIER_SIMPLE = "foobar";
    public static final String CLASSIFIER_POINT = "foo.bar";

    public static final String EXTENSION_SIMPLE = "jar";
    public static final String EXTENSION_HASH = "jar.md5";
    public static final String EXTENSION_MULTIPLE_HASH = "jar.asc.md5";
    public static final String EXTENSION_MULTIPLE = "a.b.c.d.e.f";

    public static final String EXTENSION_MULTIPLE_LAST_ELEMENT = "f";
    public static final String EXTENSION_MULTIPLE_HASH_LAST_ELEMENT = "asc.md5";

    public static final String CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE = "foobar.a.b.c.d.e";
    public static final String CLASSIFIER_SIMPLE_WITH_EXTENSION_MULTIPLE_HASH = "foobar.jar";

    public static final String CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE = "foo.bar.a.b.c.d.e";
    public static final String CLASSIFIER_POINT_WITH_EXTENSION_MULTIPLE_HASH = "foo.bar.jar";
}
