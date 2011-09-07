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
import com.zenika.dorm.core.util.DormUtils;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.model.impl.MavenConstant;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import org.apache.commons.io.FilenameUtils;

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

    public void storeMavenFile(String url, File file, boolean override) {

        DormResource resource = DefaultDormResource.create(file);
        DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
                .resourcePath(url, MavenMetadataExtension.EXTENSION_NAME)
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

        DormResource jarSha1Resource = service.getResource(MavenMetadataExtension.METADATA_EXTENSION,
                jarSha1);
        DormResource jarMd5Resource = service.getResource(MavenMetadataExtension.METADATA_EXTENSION,
                jarSha1);
        DormResource pomResource = service.getResource(MavenMetadataExtension.METADATA_EXTENSION,
                jarSha1);
        DormResource pomSha1Resource = service.getResource(MavenMetadataExtension.METADATA_EXTENSION,
                jarSha1);
        DormResource pomMd5Resource = service.getResource(MavenMetadataExtension.METADATA_EXTENSION,
                jarSha1);
        DormResource metadataXmlResource = service.getResource(MavenMetadataExtension.METADATA_EXTENSION,
                jarSha1);

        if (DormUtils.isNullIn(jarSha1Resource, jarMd5Resource, pomResource, pomSha1Resource,
                pomMd5Resource, metadataXmlResource)) {
            return false;
        }

        return true;
    }

    public void convertDeployedArtifactToDorm(String url) {
    }

    public void storeFromUri(MavenUri mavenUri, File file) {

    }
}
