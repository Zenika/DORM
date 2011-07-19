package com.zenika.dorm.maven.test;

import com.zenika.dorm.core.modelnew.impl.DormArtifact;
import com.zenika.dorm.core.modelnew.impl.DormModule;
import com.zenika.dorm.core.modelnew.impl.DormScope;
import com.zenika.dorm.maven.model.impl.MavenOrigin;
import org.junit.Ignore;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class MavenArtifactTest {

    @Test
    public void createModuleAndArtifact() {

        MavenOrigin origin = new MavenOrigin("com.zenika", "art1", "1.0", "jar");
        DormModule module = new DormModule("1.0", origin);

        DormArtifact artifact = new DormArtifact(module.getQualifier(), module.getVersion());
        module.addArtifact(artifact);

        assertThat(module.getQualifier()).isEqualTo("com.zenika:art1");
        assertThat(module.getFullQualifier()).isEqualTo("com.zenika:art1:1.0:maven");
        assertThat(module.getVersion()).isEqualTo("1.0");
        assertThat(module.getArtifacts()).containsExactly(artifact);
        assertThat(module.getArtifactsByScope(DormScope.DEFAULT_SCOPE)).containsExactly(artifact);

        assertThat(artifact.getQualifier()).isEqualTo("com.zenika:art1");
        assertThat(artifact.getVersion()).isEqualTo("1.0");
        assertThat(artifact.getModule()).isEqualTo(module);
        assertThat(artifact.getScopes()).containsExactly(new DormScope("default"));
    }
}
