package com.zenika.dorm.core.test.helper;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.impl.DefaultDormFile;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class ExtensionFixtures {

    /**
     * Dorm file
     */
    private String filenameWithExtension = "testfile.jar";
    private String filename = "testfile";
    private String filenameExtension = "jar";
    private File file = new File("/tmp/testfile.jar");

    /**
     * Metadata
     */
    private String version = "1.0";

    /**
     * Return the metadata extension
     *
     * @return
     */
    public abstract DormMetadataExtension getMetadataExtension();

    /**
     * Return the request properties that are specific to the extension
     *
     * @return
     */
    public abstract Map<String, String> getRequestPropertiesForExtension();

    public Map<String, String> getRequestPropertiesWithoutFile() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(DormRequest.ORIGIN, DefaultDormMetadataExtension.NAME);
        properties.put(DormRequest.VERSION, version);

        Map<String, String> extensionProperties = getRequestPropertiesForExtension();

        if (null != extensionProperties) {
            properties.putAll(extensionProperties);
        }

        return properties;
    }

    public Map<String, String> getRequestPropertiesWithFile() {
        Map<String, String> properties = getRequestPropertiesWithoutFile();
        properties.put(DormRequest.FILENAME, filenameWithExtension);
        return properties;
    }

    public DormRequest getRequestWithoutFile() {
        return DefaultDormRequest.create(getRequestPropertiesWithoutFile());
    }

    public DormRequest getRequestWithFile() {
        return DefaultDormRequest.create(getRequestPropertiesWithFile(), file);
    }

    public DormMetadata getMetadata() {
        return DefaultDormMetadata.create(version, getMetadataExtension());
    }

    public DormFile getDormFile() {
        return DefaultDormFile.create(filename, filenameExtension, file);
    }

    public Dependency getDependencyWithoutFile() {
        return DefaultDependency.create(getMetadata());
    }

    public Dependency getDependencyWithFile() {
        return DefaultDependency.create(getMetadata(), getDormFile());
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
