package com.zenika.dorm.core.helper;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;

import java.io.File;

public class DormFileHelper {

    public static String getExtensionFromFilename(String filename) {

        try {
            return filename.trim().substring(filename.lastIndexOf(".")).toLowerCase().substring(1);
        } catch (Exception e) {
            throw new CoreException("Cannot create DormFile, maybe extension is missing");
        }
    }

    public static <T extends MetadataExtension> DormFile createDormFileFromFilename(DormMetadata<T> metadata, File file, String filename) {

        DormFile dormFile = new DormFile();
        dormFile.setExtension(getExtensionFromFilename(filename));
        dormFile.setFile(file);
        dormFile.setName(metadata.getFullQualifier());

        return dormFile;
    }

    public static <T extends MetadataExtension> DormFile createDormFile(DormMetadata<T> metadata, File file, String extension) {

        DormFile dormFile = new DormFile();
        dormFile.setExtension(extension);
        dormFile.setFile(file);
        dormFile.setName(metadata.getFullQualifier());

        return dormFile;
    }
}
