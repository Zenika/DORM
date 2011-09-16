package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.model.MavenConstant;
import org.fest.assertions.Assertions;
import org.junit.Test;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFileTypeUnitTest {

    @Test
    public void isValidMavenType() {
        Assertions.assertThat(MavenConstant.Extension.isMavenExtension("jar")).isTrue();
        Assertions.assertThat(MavenConstant.Extension.isMavenExtension("sha1")).isTrue();
        Assertions.assertThat(MavenConstant.Extension.isMavenExtension("pom")).isTrue();
    }

    @Test
    public void isWrongMavenType() {
        Assertions.assertThat(MavenConstant.Extension.isMavenExtension("foo")).isFalse();
    }
}
