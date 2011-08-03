package com.zenika.dorm.core.model.mapper;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadataExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Perform introspection on DormMetadataExtension implementations
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class MetadataExtensionMapper {

    private MetadataExtensionMapper() {

    }

    public static <T extends DormMetadataExtension> Map<String, String> fromExtension(T extension) {

        Map<String, String> properties = new HashMap<String, String>();

        Class<? extends DormMetadataExtension> reflect = extension.getClass();

        for (Field field : reflect.getDeclaredFields()) {

            // include non public attributes
            field.setAccessible(true);

            try {
                properties.put(field.getName(), (String) field.get(extension));
            } catch (IllegalAccessException e) {
                throw new CoreException("Cannot map from extension", e);
            }
        }

        return properties;
    }

    public static <T extends DormMetadataExtension> T toExtension(T extension, Map<String, String> properties) {

        Class<? extends DormMetadataExtension> reflect = extension.getClass();

        for (Field field : reflect.getDeclaredFields()) {

            // include non public attributes
            field.setAccessible(true);

            // final static, don't touch
            if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            Object property = properties.get(field.getName());

            try {
                field.set(extension, property);
            } catch (IllegalAccessException e) {
                throw new CoreException("Cannot fill extension", e);
            }
        }

        return extension;
    }

}
