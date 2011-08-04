package com.zenika.dorm.core.test.model;

import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.impl.DefaultDormRequest;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests for the dorm request
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormRequestUnitTest extends AbstractUnitTest {

    /**
     * Test the constructions by copy
     */
    @Test
    public void createRequestFromExistingOneWithValidParams() {

        String newVersion = "2.0";
        File newFile = new File("tmp/file-new.jar");

        Map<String, String> newProperties = new HashMap<String, String>(fixtures.getRequestPropertiesWithFile());
        newProperties.put(DormRequest.VERSION, newVersion);

        // create dorm request model with the new properties
        DormRequest requestNewPropertiesModel = DefaultDormRequest.create(newProperties, fixtures.getFile());

        // create dorm request model with the new file
        DormRequest requestNewFileModel = DefaultDormRequest.create(fixtures.getRequestPropertiesWithFile(), newFile);

        // create dorm request model with both the new properties and file
        DormRequest requestNewPropertiesAndFileModel = DefaultDormRequest.create(newProperties, newFile);

        // get the default request properties and edit the version
        DormRequest request = fixtures.getRequestWithFileOldWay();

        // create new request with the new properties
        DormRequest requestNewProperties = DefaultDormRequest.createFromRequest(request, newProperties);

        Assertions.assertThat(requestNewProperties).isEqualTo(requestNewPropertiesModel);
        Assertions.assertThat(requestNewProperties.getVersion()).isEqualTo(newVersion);

        // create new request with the new version
        DormRequest requestNewFile = DefaultDormRequest.createFromRequest(request, newFile);

        Assertions.assertThat(requestNewFile).isEqualTo(requestNewFileModel);
        Assertions.assertThat(requestNewFile.getFile()).isEqualTo(newFile);

        // create new request with both the new properties and file
        DormRequest requestNewPropertiesAndFile = DefaultDormRequest.createFromRequest(request, newProperties,
                newFile);

        Assertions.assertThat(requestNewPropertiesAndFile).isEqualTo(requestNewPropertiesAndFileModel);
        Assertions.assertThat(requestNewPropertiesAndFile.getFile()).isEqualTo(newFile);
        Assertions.assertThat(requestNewPropertiesAndFile.getVersion()).isEqualTo(newVersion);
    }
}
