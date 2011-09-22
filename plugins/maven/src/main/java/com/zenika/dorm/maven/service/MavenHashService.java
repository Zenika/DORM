package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenConstant;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import org.apache.commons.codec.digest.DigestUtils;
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

    public boolean compareMavenHashes(MavenMetadata metadata, File file) {
        return compareHash(MavenConstant.Extension.MD5, metadata, file) &&
                compareHash(MavenConstant.Extension.SHA1, metadata, file);
    }

    public boolean compareHash(String hash, MavenMetadata metadata, File file) {

        MavenMetadata hashMetadata = new MavenMetadataBuilder(metadata)
                .extension(hash)
                .build();

        DormResource resource = service.getResource(hashMetadata);

        if (null == resource) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Hash " + hash + " not found for metadata : " + metadata + ", ignoring");
            }

            return true;
        }

        String modelHash = null;
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(resource.getFile()));
            modelHash = reader.readLine();
        } catch (IOException e) {
            throw new MavenException("Error reading file containing hash", e);
        }

        String currentHash;
        try {
            currentHash = DigestUtils.md5Hex(new FileInputStream(file));
        } catch (IOException e) {
            throw new MavenException("File on which generate hash not found");
        }

        return modelHash != null && modelHash.equals(currentHash);
    }
}
