package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataResult;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataValues;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenService {

    @Inject
    private DormService service;

    public MavenMetadataExtension getMetadataByUrl(String url) {

        MavenMetadataExtension mavenMetadata = new MavenMetadataExtension(url);
        DormMetadata metadata = DefaultDormMetadata.create(null, mavenMetadata);

        DormServiceGetMetadataValues values = new DormServiceGetMetadataValues(metadata)
                .withMetadataExtensionClause("url", url);
        
        DormServiceGetMetadataResult result = service.getMetadata(values);

        if (!result.hasUniqueResult()) {
            return null;
        }

        DormMetadata metadataFromResult = result.getUniqueMetadata();
        MavenMetadataExtension mavenMetadataFromResult;

        try {
            mavenMetadataFromResult = (MavenMetadataExtension) metadataFromResult.getExtension();
        } catch (ClassCastException e) {
            throw new MavenException("Metadata extension is not maven", e);
        }

        return mavenMetadataFromResult;
    }

    public void storeMavenFile(String url, File file) {

        DormResource resource = DefaultDormResource.create(file);
        DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
                .resourcePath(url, MavenMetadataExtension.EXTENSION_NAME);

        service.storeResource(resource, config);
    }
}
