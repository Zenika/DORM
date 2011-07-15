package com.zenika.dorm.core.model.mapper;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormOrigin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Perform introspection on DormOrigin implementations
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class OriginMapper {

    public static <T extends DormOrigin> Map<String, String> fromOrigin(T origin) {

        Map<String, String> properties = new HashMap<String, String>();

        Class<? extends DormOrigin> reflect = origin.getClass();

        for (Field field : reflect.getDeclaredFields()) {

            // include non public attributes
            field.setAccessible(true);

            // TODO: Add required field checking, by annotation for example ?

            try {
                properties.put(field.getName(), (String) field.get(origin));
            } catch (IllegalAccessException e) {
                throw new CoreException("Cannot map from origin", e);
            }
        }

        return properties;
    }

    public static <T extends DormOrigin> T toOrigin(T origin, Map<String, String> properties) {

        Class<? extends DormOrigin> reflect = origin.getClass();

        for (Field field : reflect.getDeclaredFields()) {

            // include non public attributes
            field.setAccessible(true);

            // final static, don't touch
            if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            Object property = properties.get(field.getName());

            try {
                field.set(origin, property);
            } catch (IllegalAccessException e) {
                throw new CoreException("Cannot populate origin", e);
            }
        }

        return origin;
    }

}
