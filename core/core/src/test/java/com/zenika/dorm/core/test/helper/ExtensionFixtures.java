package com.zenika.dorm.core.test.helper;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;

import java.io.File;
import java.io.IOException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class ExtensionFixtures {

    public static final String TESTPATH = "tmp/test/";

    /**
     * Dorm file
     */
    private String filenameWithExtension = "testfile.jar";
    private String filename = "testfile";
    private String filenameExtension = "jar";
    private File file = new File(TESTPATH + "testfile.jar");

    public ExtensionFixtures() {

        try {
            new File(TESTPATH).mkdirs();
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
    public abstract DormMetadata getMetadataExtension();

    public String getRequestVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public DormWebServiceRequest.Builder getRequestBuilder() {
        return new DormWebServiceRequest.Builder(getRequestVersion());
    }

    public DormWebServiceRequest.Builder getRequestBuilderWithFile() {
        return getRequestBuilder().filename(filenameWithExtension).file(file);
    }

    public DormWebServiceRequest getRequestWithoutFile() {
        return getRequestBuilder().build();
    }

    public DormWebServiceRequest getRequestWithFile() {
        return getRequestBuilderWithFile().build();
    }

    public DormMetadata getMetadata() {
//        return DefaultDormMetadata.create(getRequestVersion(), getMetadataExtension());
        return null;
    }

    public DormResource getDormResource() {
        return DefaultDormResource.create(filename, filenameExtension, file);
    }

    public Dependency getDependencyWithoutResource() {
        return DefaultDependency.create(getMetadata());
    }

    public Dependency getDependencyWithResource() {
        return DefaultDependency.create(getMetadata(), getDormResource());
    }

    public DependencyNode getNodeWithoutResource() {
        return DefaultDependencyNode.create(getDependencyWithoutResource());
    }

    public DependencyNode getNodeWithResource() {
        return DefaultDependencyNode.create(getDependencyWithResource());
    }

    public String getFilenameWithExtension() {
        return filenameWithExtension;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilenameExtension() {
        return filenameExtension;
    }

    public File getFile() {
        return file;
    }

    public String getVersion() {
        return version;
    }
}
