package com.zenika.dorm.core.factory;

import com.zenika.dorm.core.model.DormMetadata;

import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface ExtensionMetadataFactory {

    public DormMetadata createFromProperties(Map<String, String> properties);
}
