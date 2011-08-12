package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.maven.model.impl.MavenFileType;
import org.fest.assertions.Assertions;
import org.junit.Test;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFileTypeUnitTest {

    @Test
    public void isValidMavenType() {
        Assertions.assertThat(MavenFileType.isMavenType("jar")).isTrue();
        Assertions.assertThat(MavenFileType.isMavenType("sha1")).isTrue();
        Assertions.assertThat(MavenFileType.isMavenType("pom")).isTrue();
    }

    @Test
    public void isWrongMavenType() {
        Assertions.assertThat(MavenFileType.isMavenType("foo")).isFalse();
    }
}
