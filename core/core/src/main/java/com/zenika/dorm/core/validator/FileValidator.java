package com.zenika.dorm.core.validator;

import java.io.File;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class FileValidator {

    public static void validateFile(File file) {
        checkNotNull(file);
        checkArgument(file.exists());
        checkArgument(file.isFile());
    }

    public static void validateFolder(File file) {
        checkNotNull(file);
        checkArgument(file.exists());
        checkArgument(file.isDirectory());
    }
}