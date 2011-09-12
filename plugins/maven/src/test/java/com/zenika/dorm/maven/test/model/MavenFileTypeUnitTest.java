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
        Assertions.assertThat(MavenConstant.FileExtension.isMavenExtension("jar")).isTrue();
        Assertions.assertThat(MavenConstant.FileExtension.isMavenExtension("sha1")).isTrue();
        Assertions.assertThat(MavenConstant.FileExtension.isMavenExtension("pom")).isTrue();
    }

    @Test
    public void isWrongMavenType() {
        Assertions.assertThat(MavenConstant.FileExtension.isMavenExtension("foo")).isFalse();
    }
}
