package com.zenika.dorm.maven.test;

import com.zenika.dorm.core.model.old.DormFile;
import com.zenika.dorm.maven.model.impl.MavenOrigin;
import com.zenika.dorm.maven.service.MavenService;
import com.zenika.dorm.maven.service.impl.MavenServiceImpl;
import org.junit.Test;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenServiceTest {

    @Test
    public void pushArtifact() {

        String filename = "artifact.jar";
        File jar = new File("tmp/tests/" + filename);

        MavenOrigin origin = new MavenOrigin("com.zenika", "maven-artifact", "1.0-SNAPSHOT", "jar");
        DormFile file = new DormFile(filename, jar);

        MavenService service = new MavenServiceImpl();
        service.pushArtifact(origin, file);
    }
}
