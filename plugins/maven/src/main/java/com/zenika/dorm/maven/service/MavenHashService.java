package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.maven.constant.MavenConstant;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenBuildInfoBuilder;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenHashService {

    private static final Logger LOG = LoggerFactory.getLogger(MavenHashService.class);

    @Inject
    private DormService service;

    public boolean compareHash(MavenMetadata hashMetadata, File file) {

        String hashType = FilenameUtils.getExtension(hashMetadata.getBuildInfo().getExtension());

        MavenMetadata metadata = getRealMetadataFromHash(hashMetadata);

        DormResource resource = service.getResource(metadata);

        if (null == resource) {
            throw new MavenException("Metadata associated to the hash was not found : " + metadata);
        }

        String currentHash = null;
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(file));
            currentHash = reader.readLine();
        } catch (IOException e) {
            throw new MavenException("Error reading file containing hash", e);
        }

        String modelHash;

        try {
            if (StringUtils.equals(hashType, MavenConstant.Extension.MD5)) {
                modelHash = DigestUtils.md5Hex(new FileInputStream(resource.getFile()));
            } else if (StringUtils.equals(hashType, MavenConstant.Extension.SHA1)) {
                modelHash = DigestUtils.shaHex(new FileInputStream(resource.getFile()));
            } else {
                throw new MavenException("Unknown hash type : " + hashType);
            }

        } catch (IOException e) {
            throw new MavenException("File on which generate hash not found");
        }

        return StringUtils.equals(currentHash, modelHash);
    }

    private MavenMetadata getRealMetadataFromHash(MavenMetadata hashMetadata) {

        String extension = FilenameUtils.removeExtension(hashMetadata.getBuildInfo().getExtension());

        MavenBuildInfo buildInfo = new MavenBuildInfoBuilder(hashMetadata.getBuildInfo())
                .extension(extension)
                .build();

        return new MavenMetadataBuilder(hashMetadata)
                .buildInfo(buildInfo)
                .build();
    }
}
