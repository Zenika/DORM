package com.zenika.dorm.core.factory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;

import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Singleton
public final class DormMetadataFactory {

    private Map<String, ExtensionMetadataFactory> metadataFactories;

    /**
     * @deprecated
     */
    private Map<String, Class> metadataClasses;

    @Inject
    public DormMetadataFactory(Map<String, Class> metadataClasses, Map<String,
            ExtensionMetadataFactory> metadataFactories) {
        this.metadataClasses = metadataClasses;
        this.metadataFactories = metadataFactories;
    }

    public DormMetadata getInstanceFromProperties(String name, Map<String, String> properties) {

        ExtensionMetadataFactory factory = metadataFactories.get(name);

        if (null == factory) {
            throw new CoreException("Metadata factory not found for identifier : " + name);
        }

        return factory.createFromProperties(properties);
    }

    /**
     * @param name
     * @return
     * @deprecated by factories
     */
    public DormMetadata getInstanceOf(String name) {

        Class metadata = metadataClasses.get(name);

        if (null == metadata) {
            throw new CoreException("Metadata not found for identifier : " + name);
        }

        try {
            return (DormMetadata) metadata.newInstance();
        } catch (Exception e) {
            throw new CoreException("Cannot instanciate metadata : " + name, e);
        }
    }
}
