package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.util.DormUtils;
import com.zenika.dorm.maven.model.MavenConstant;
import com.zenika.dorm.maven.model.MavenMetadata;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenService {

    @Inject
    private DormService service;

    public void storeMavenFile(String url, File file, boolean override) {

        DormResource resource = DefaultDormResource.create(file);
        DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
                .resourcePath(url, MavenMetadata.EXTENSION_NAME)
                .override(override);

        service.storeResource(resource, config);
    }

    public boolean isMavenDeployCompleted(String url) {

        String urlWithtoutExtension = FilenameUtils.removeExtension(url);
        String jarSha1 = url + "." + MavenConstant.FileExtension.SHA1;
        String jarMd5 = url + "." + MavenConstant.FileExtension.MD5;
        String pom = url + "." + MavenConstant.FileExtension.POM;
        String pomSha1 = url + "." + MavenConstant.FileExtension.SHA1;
        String pomMd5 = url + "." + MavenConstant.FileExtension.MD5;
        String metadataXml = url + MavenConstant.Special.MAVEN_METADATA_XML;

        DormResource jarSha1Resource = service.getResource(MavenMetadata.METADATA_EXTENSION,
                jarSha1);
        DormResource jarMd5Resource = service.getResource(MavenMetadata.METADATA_EXTENSION,
                jarSha1);
        DormResource pomResource = service.getResource(MavenMetadata.METADATA_EXTENSION,
                jarSha1);
        DormResource pomSha1Resource = service.getResource(MavenMetadata.METADATA_EXTENSION,
                jarSha1);
        DormResource pomMd5Resource = service.getResource(MavenMetadata.METADATA_EXTENSION,
                jarSha1);
        DormResource metadataXmlResource = service.getResource(MavenMetadata.METADATA_EXTENSION,
                jarSha1);

        if (DormUtils.isNullIn(jarSha1Resource, jarMd5Resource, pomResource, pomSha1Resource,
                pomMd5Resource, metadataXmlResource)) {
            return false;
        }

        return true;
    }

    public void storeMetadataWithArtifact(MavenMetadata mavenMetadata, File file) {

        DormMetadata metadata = DefaultDormMetadata.create(null, mavenMetadata);

        DormResource resource = DefaultDormResource.create(file);
        DormServiceStoreResourceConfig storeResourceConfig = new DormServiceStoreResourceConfig()
                .override(true)
                .metadata(metadata);

        service.storeMetadata(metadata);
        service.storeResource(resource, storeResourceConfig);
    }

    public void storeArtifact(MavenMetadata metadata, File file) {
    }

    public void storePom(File file) {
    }
}
