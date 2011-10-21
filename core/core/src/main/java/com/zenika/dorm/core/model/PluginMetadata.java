package com.zenika.dorm.core.model;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public interface PluginMetadata {

    String getType();

    DormMetadata toDormMetadata();
}
