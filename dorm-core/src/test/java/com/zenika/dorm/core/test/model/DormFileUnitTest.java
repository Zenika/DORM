package com.zenika.dorm.core.test.model;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.impl.DefaultDormFile;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormFileUnitTest extends AbstractUnitTest {

    @Test
    public void createFile() {
        DormFile file = DefaultDormFile.create(fixtures.getFilenameWithExtension(), fixtures.getFile());
        Assertions.assertThat(file.getFilename()).isEqualTo(fixtures.getFilenameWithExtension());
        Assertions.assertThat(file.getName()).isEqualTo(fixtures.getFilename());
        Assertions.assertThat(file.getExtension()).isEqualTo(fixtures.getFilenameExtension());
    }

    @Test(expected = CoreException.class)
    public void createFileWithInvalidExtension() {
        DefaultDormFile.create("noextension", fixtures.getFile());
    }

    @Test(expected = CoreException.class)
    public void createFileWithoutFile() {
        DefaultDormFile.create(fixtures.getFilenameWithExtension(), null);
    }
}
