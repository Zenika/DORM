package com.zenika.dorm.core.test.model;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormMetadataExtensionUnitTest extends AbstractUnitTest {

    @Test
    public void createExtension() {

        DormMetadataExtension extension = new DefaultDormMetadataExtension(fixtures.getName());

        // dorm extension qualifier is only the name
        Assertions.assertThat(extension.getQualifier()).isEqualTo(fixtures.getName());
        Assertions.assertThat(extension.getExtension()).isEqualTo(fixtures.getOrigin());
    }
}
