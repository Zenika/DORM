package com.zenika.dorm.core.test.util;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.util.DormFormatter;
import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class DormFormatterUnitTest {

    @Test
    public void formatValidMetadataExtensionQualifier() {
        Assertions.assertThat(DormFormatter.formatMetadataExtensionQualifier("Foo"));
    }

    @Test(expected = CoreException.class)
    public void formatInvalidMetadataExtensionQualifier() {
        Assertions.assertThat(DormFormatter.formatMetadataExtensionQualifier("Foo:Bar-Toto:85"));
    }

    @Test
    public void formatValidMetadataVersion() {
        Assertions.assertThat(DormFormatter.formatMetadataVersion("1.0")).isEqualTo("1.0");
        Assertions.assertThat(DormFormatter.formatMetadataVersion("1")).isEqualTo("1");
        Assertions.assertThat(DormFormatter.formatMetadataVersion("1.0-SNAPSHOT")).isEqualTo("1.0-SNAPSHOT");
    }

    @Test(expected = CoreException.class)
    public void formatInvalidMetadataVersion() {
        DormFormatter.formatMetadataVersion("1.0 foo");
    }
}
