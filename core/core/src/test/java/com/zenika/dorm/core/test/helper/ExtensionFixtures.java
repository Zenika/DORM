package com.zenika.dorm.core.test.helper;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DerivedObject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;

import java.io.File;
import java.io.IOException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class ExtensionFixtures {

    public static final String TEST_PATH = "tmp/test/";

    private File file = new File(TEST_PATH + "testfile.jar");

    public ExtensionFixtures() {

        try {
            new File(TEST_PATH).mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            throw new CoreException("Cannot create file for test", e);
        }
    }

    /**
     * Metadata
     */
    private String version = "1.0";
    private String type = "jar";

    /**
     * Return the metadata extension
     *
     * @return
     */

    public String getType() {
        return type;
    }

    public DormMetadata getMetadata() {
        DormMetadata dormMetadata = new DormMetadata();
        dormMetadata.setName("testMetadata");
        dormMetadata.setVersion(version);
        return dormMetadata;
    }

    public DerivedObject getDerivedObject() {
        DerivedObject derivedObject = new DerivedObject();
        derivedObject.setLocation(file.getPath());
        return derivedObject;
    }

    public DormMetadata getDependencyWithResource() {
        DormMetadata dormMetadata = getMetadata();
        DerivedObject derivedObject = getDerivedObject();
        dormMetadata.setDerivedObject(derivedObject);
        return dormMetadata;
    }

    public String getVersion() {
        return version;
    }
}
