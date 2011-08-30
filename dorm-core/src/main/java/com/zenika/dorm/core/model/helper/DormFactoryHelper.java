package com.zenika.dorm.core.model.helper;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;

/**
 * Factory for simple models
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormFactoryHelper {

    public static Dependency createSimpleDependency(String version, String type,
                                                    DormMetadataExtension extension) {
        return DefaultDependency.create(DefaultDormMetadata.create(version, extension));
    }
}
