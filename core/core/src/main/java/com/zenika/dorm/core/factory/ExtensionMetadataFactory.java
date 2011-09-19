package com.zenika.dorm.core.factory;

import com.zenika.dorm.core.model.DormMetadata;

import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface ExtensionMetadataFactory {

    public String getExtensionName();

    public DormMetadata createFromProperties(Map<String, String> properties);

    public Map<String, String> toMap(DormMetadata metadata);
}
