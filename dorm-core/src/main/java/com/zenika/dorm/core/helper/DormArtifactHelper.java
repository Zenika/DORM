package com.zenika.dorm.core.helper;

import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormArtifactHelper {

    public static <T extends MetadataExtension> DormArtifact<T> getDormArtifact(DormMetadata<T> metadata, DormFile file,
                                                                                List<DormArtifact<T>> dependencies) {

        DormArtifact<T> artifact = new DormArtifact<T>(metadata, file, dependencies);

        return artifact;
    }

    public static <T extends MetadataExtension> DormArtifact<T> getDormArtifact(DormMetadata<T> metadata, DormFile file) {


        DormArtifact<T> artifact = new DormArtifact<T>(metadata, file);

        return artifact;
    }


}
