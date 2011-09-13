package com.zenika.dorm.core.factory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadataExtension;

import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Singleton
public final class MetadataExtensionFactory {

    private Map<String, Class> extensions;

    @Inject
    public MetadataExtensionFactory(Map<String, Class> extensions) {
        this.extensions = extensions;
    }

    public DormMetadataExtension getInstanceOf(String extension) {

        Class metadata = extensions.get(extension);

        if (null == metadata) {
            throw new CoreException("Metadata extension not found for identifier : " + extension);
        }

        try {
            return (DormMetadataExtension) metadata.newInstance();
        } catch (Exception e) {
            throw new CoreException("Cannot instanciate metadata : " + extension, e);
        }
    }
}
