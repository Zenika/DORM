package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.model.impl.MavenConstant;
import org.fest.assertions.Assertions;
import org.junit.Test;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFileTypeUnitTest {

    @Test
    public void isValidMavenType() {
        Assertions.assertThat(MavenConstant.Type.isMavenType("jar")).isTrue();
        Assertions.assertThat(MavenConstant.Type.isMavenType("sha1")).isTrue();
        Assertions.assertThat(MavenConstant.Type.isMavenType("pom")).isTrue();
    }

    @Test
    public void isWrongMavenType() {
        Assertions.assertThat(MavenConstant.Type.isMavenType("foo")).isFalse();
    }
}
