package com.zenika.dorm.core.test.model;

import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.io.File;

/**
 * Unit tests for the dorm request
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormRequestUnitTest extends AbstractUnitTest {

    /**
     * Test the construction by copy
     */
    @Test
    public void createRequestFromExistingOneWithValidParams() {

        String newVersion = "2.0";
        File newFile = new File("tmp/file-new.jar");

        // get the default request as model for tests
        DormRequest request = new DormRequestBuilder(fixtures.getVersion(), fixtures.getOrigin())
                .file(fixtures.getFile())
                .filename(fixtures.getFilenameWithExtension())
                .build();

        /*
            TEST BY EDITING THE VERSION
         */

        DormRequest requestNewVersionModel = new DormRequestBuilder(newVersion, fixtures.getOrigin())
                .file(fixtures.getFile())
                .filename(fixtures.getFilenameWithExtension())
                .build();

        // create new request with the new version
        DormRequest requestNewVersion = new DormRequestBuilder(request)
                .version(newVersion)
                .build();

        Assertions.assertThat(requestNewVersion).isEqualTo(requestNewVersionModel);
        Assertions.assertThat(requestNewVersion.getVersion()).isEqualTo(newVersion);


        /*
            TEST BY EDITING THE FILE
        */

        DormRequest requestNewFileModel = new DormRequestBuilder(fixtures.getVersion(), fixtures.getOrigin())
                .file(newFile)
                .filename(fixtures.getFilenameWithExtension())
                .build();

        // create new request with the new file
        DormRequest requestNewFile = new DormRequestBuilder(request)
                .file(newFile)
                .build();

        Assertions.assertThat(requestNewFile).isEqualTo(requestNewFileModel);
        Assertions.assertThat(requestNewFile.getFile()).isEqualTo(newFile);


        /*
            TEST BY EDITING THE VERSION AND THE FILE
        */

        DormRequest requestNewVersionAndFileModel = new DormRequestBuilder(newVersion, fixtures.getOrigin())
                .file(newFile)
                .filename(fixtures.getFilenameWithExtension())
                .build();

        // create new request with both the new version and file
        DormRequest requestNewVersionAndFile = new DormRequestBuilder(request)
                .version(newVersion)
                .file(newFile)
                .build();

        Assertions.assertThat(requestNewVersionAndFile).isEqualTo(requestNewVersionAndFileModel);
        Assertions.assertThat(requestNewVersionAndFile.getFile()).isEqualTo(newFile);
        Assertions.assertThat(requestNewVersionAndFile.getVersion()).isEqualTo(newVersion);
    }
}
