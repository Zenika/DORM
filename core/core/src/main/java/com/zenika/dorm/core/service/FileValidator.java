package com.zenika.dorm.core.service;

import java.io.File;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class FileValidator {

    public void validateFile(File file) {
        validateFile(file, null);
    }

    public void validateFile(File file, String message) {
        checkNotNull(file, message);
        checkArgument(file.exists(), message);
        checkArgument(file.isFile(), message);
    }

}