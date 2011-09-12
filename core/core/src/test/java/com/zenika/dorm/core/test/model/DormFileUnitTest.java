package com.zenika.dorm.core.test.model;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormFileUnitTest extends AbstractUnitTest {

    @Test
    public void createFile() {
        DormResource resource = DefaultDormResource.create(fixtures.getFilenameWithExtension(), fixtures.getFile());
        Assertions.assertThat(resource.getFilename()).isEqualTo(fixtures.getFilenameWithExtension());
        Assertions.assertThat(resource.getName()).isEqualTo(fixtures.getFilename());
        Assertions.assertThat(resource.getExtension()).isEqualTo(fixtures.getFilenameExtension());
    }

    @Test(expected = CoreException.class)
    public void createFileWithInvalidExtension() {
        DefaultDormResource.create("noextension", fixtures.getFile());
    }

    @Test(expected = CoreException.class)
    public void createFileWithoutFile() {
        DefaultDormResource.create(fixtures.getFilenameWithExtension(), null);
    }
}
