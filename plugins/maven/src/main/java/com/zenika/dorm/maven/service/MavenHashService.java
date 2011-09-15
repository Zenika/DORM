package com.zenika.dorm.maven.service;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DormQualifier;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenConstant;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenHashService {

    @Inject
    private DormService service;

    public boolean compareMavenHashes(MavenMetadata metadata, File file) {
        return compareHash(MavenConstant.FileExtension.MD5, metadata, file) &&
                compareHash(MavenConstant.FileExtension.SHA1, metadata, file);
    }

    private boolean compareHash(String hash, MavenMetadata metadata, File file) {

        MavenMetadata hashMetadata = new MavenMetadataBuilder(metadata)
                .extension(hash)
                .build();

        DormQualifier qualifier = new DormQualifier(hashMetadata);
        DormResource resource = service.getResource(qualifier);

        if (resource.exists()) {
            return false;
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
