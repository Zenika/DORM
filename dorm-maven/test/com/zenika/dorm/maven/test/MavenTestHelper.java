package com.zenika.dorm.maven.test;

import com.zenika.dorm.core.modelnew.impl.DormArtifact;
import com.zenika.dorm.core.modelnew.impl.DormModule;
import com.zenika.dorm.maven.model.impl.MavenOrigin;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenTestHelper {

    public static DormModule getMavenModule() {

        MavenOrigin origin = new MavenOrigin("com.zenika", "art1", "1.0");
        DormModule module = new DormModule("1.0", origin);

        DormArtifact artifact = new DormArtifact(module.getQualifier(), module.getVersion());
        module.addArtifact(artifact);

        return module;
    }
}
