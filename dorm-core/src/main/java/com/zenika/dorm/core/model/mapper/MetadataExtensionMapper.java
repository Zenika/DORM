package com.zenika.dorm.core.model.mapper;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadataExtension;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * Perform introspection on DormMetadataExtension implementations
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class MetadataExtensionMapper {

    private static final Logger LOG = LoggerFactory.getLogger(MetadataExtensionMapper.class);

    private MetadataExtensionMapper() {

    }

    public static <T extends DormMetadataExtension> Map<String, String> fromExtension(T extension) {
        try {
            return BeanUtils.describe(extension);
        } catch (Exception e) {
            throw new CoreException("Cannot map extension attributes to key -> value", e);
        }
    }

//    public static <T extends DormMetadataExtension> Map<String, String> fromExtension(T extension) {
//
//        Map<String, String> properties = new HashMap<String, String>();
//
//        Class<? extends DormMetadataExtension> reflect = extension.getClass();
//
//        for (Field field : reflect.getDeclaredFields()) {
//
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Trying to map field : " + field);
//            }
//
//            field.setAccessible(true);
//
//            // ignore transient attributes
//            if (Modifier.isTransient(field.getModifiers())) {
//                continue;
//            }
//
//            try {
//                properties.put(field.getName(), (String) field.get(extension));
//            } catch (ClassCastException e) {
//                LOG.error("Cannot map anything else thann Strings, make " + field + " transient", e);
//                continue;
//            } catch (IllegalAccessException e) {
//                throw new CoreException("Cannot access field " + field + " to make the mapping", e);
//            }
//        }
//
//        return properties;
//    }

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
