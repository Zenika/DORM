package com.zenika.dorm.maven.test;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.impl.DefaultDormFile;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.maven.model.impl.MavenOrigin;
import org.junit.Test;

import java.io.File;

/**
 * Test to represent a real use case of maven repository's import
 * This is a test on the model, to test it in a real use case et define if it's correct
 * We will not use and test web service, processor or services.
 *
 * File system structure of the repo :
 * repo/
 * - A/
 * -- A.jar
 * -- A.pom.xml
 *
 * - B/
 * -- B.jar
 * -- B.pom.xml
 *
 * - C/
 * -- C.jar
 * -- C.pom.xml
 *
 * Relations :
 * A depends on B
 * C depends on B
 *
 * Importer :
 * 1. scan repo /
 * 2. import A (pom.xml then jar)
 * 3. import B (pom.xml then jar)
 * 4. import C (jar then pom.xml, because we cannot predict the order)
 *
 * Final representation of neo4j graph after import should be :
 * https://docs.google.com/drawings/d/1QuK6g1cTYVllzSaXYpNggVwZw417k4VEcDOUQaVOH1Y/edit?hl=fr
 *
 * Problem with this representation is that pom.xml "artifact" and jar "artifact" must be at the same level
 * in the dependency graph. We have to create a node that will englobe all the artifacts from the same
 * maven artifact.
 * See : https://docs.google.com/drawings/d/1N1epmWY3dUy7th-VwrSNk1HXf6srEi0RoUoETQbe8qM/edit?hl=fr
 *
 * This is the maven plugin job to create the maven entity node.
 *
 * Usage is ignored in this test (default)
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenWorkflowTest {

    /**
     * Should be mocked with mockito
     */
    private DormService service;

    @Test
    public void test() {

        // import A

        // import A.pom.xml

        // metadata
        DormOrigin originApom = new MavenOrigin("com.zenika", "a", "1.0", "pom");
        DormMetadata metadataApom = new DefaultDormMetadata("1.0", originApom);

        // file
        DormFile fileApom = new DefaultDormFile("a.pom.xml", new File("repo/a/a.pom.xml"));

        // dependency
        Dependency dependencyApom = new DefaultDependency(metadataApom, fileApom);

        // store dependency
        // 1. create node com.zenika:a:1.0:1.0:maven
        // 2. store a.pom.xml in the FS
        service.pushDependency(dependencyApom);

        // import A.jar

        // metadata
        // should be equals to previous pom metadata, because only file changes
        DormOrigin originAjar = new MavenOrigin("com.zenika", "a", "1.0", "jar");
        DormMetadata metadataAjar = new DefaultDormMetadata("1.0", originAjar);

        // file
        DormFile fileAjar = new DefaultDormFile("a.jar", new File("repo/a/a.jar"));

        // dependency
        Dependency dependencyAjar = new DefaultDependency(metadataAjar, fileAjar);

        // store
        // 1. find node com.zenika:a:1.0:1.0:maven
        // 2. store a.jar in the FS
        service.pushDependency(dependencyAjar);
    }
}
