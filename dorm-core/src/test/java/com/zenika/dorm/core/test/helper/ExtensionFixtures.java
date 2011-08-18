package com.zenika.dorm.core.test.helper;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.*;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormResource;

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
    public abstract DormMetadataExtension getMetadataExtension();

    public String getRequestVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public DormRequestBuilder getRequestBuilder() {
        return new DormRequestBuilder(getRequestVersion(), getMetadataExtension().getExtensionName());
    }

    public DormRequestBuilder getRequestBuilderWithFile() {
        return getRequestBuilder().filename(filenameWithExtension).file(file);
    }

    public DormRequest getRequestWithoutFile() {
        return getRequestBuilder().build();
    }

    public DormRequest getRequestWithFile() {
        return getRequestBuilderWithFile().build();
    }

    public DormMetadata getMetadata() {
        return DefaultDormMetadata.create(getRequestVersion(), getType(), getMetadataExtension());
    }

    public DormResource getDormResource() {
        return DefaultDormResource.create(filename, filenameExtension, file);
    }

    public Dependency getDependencyWithoutFile() {
        return DefaultDependency.create(getMetadata());
    }

    public Dependency getDependencyWithFile() {
        return DefaultDependency.create(getMetadata(), getDormResource());
    }

    public DependencyNode getNodeWithoutFile() {
        return DefaultDependencyNode.create(getDependencyWithoutFile());
    }

    public DependencyNode getNodeWithFile() {
        return DefaultDependencyNode.create(getDependencyWithFile());
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
