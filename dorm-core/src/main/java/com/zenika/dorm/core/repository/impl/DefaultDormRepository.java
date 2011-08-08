package com.zenika.dorm.core.repository.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.RepositoryException;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormFile;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryResource;
import org.apache.ivy.plugins.repository.file.FileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormRepository implements DormRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDormRepository.class);

    private static final String DEFAULT_REPOSITORY_LOCATION = "tmp/repo-dorm";

    private File base;

    private DormRepositoryResolveEngine resolveEngine = new DormRepositoryResolveEngine();
    private DormRepositoryDeployEngine deployEngine = new DormRepositoryDeployEngine();

    @Inject
    public DefaultDormRepository() {
        base = new File(DEFAULT_REPOSITORY_LOCATION);
        base.mkdirs();
    }

    public DefaultDormRepository(String baseLocation) {
        base = new File(baseLocation);
        base.mkdirs();
    }

    @Override
    public boolean put(Dependency dependency) {

        LOG.debug("Put dependency = " + dependency);

        DormFile file = dependency.getFile();
        DormMetadata metadata = dependency.getMetadata();

        if (file == null) {
            throw new RepositoryException("Dorm file is required");
        }

        String location = getPathFromMetadata(metadata);
        DormRepositoryResource resource = new DefaultDormRepositoryResource(file, this, location);

        deployEngine.deploy(resource);

        LOG.debug("File is stored to the repository : " + resource.getPath());

        return true;
    }

    @Override
    public DormFile get(DormMetadata metadata) {

//        LOG.debug("Get dorm file by metadata : " + metadata);
//
//        String location = getPathFromMetadata(metadata);
//        LOG.debug("Get file at location : " + location);
//
//        // todo: fix this with creating a repository resource
//        File file = new File(BASEDIR + "/" + location);
//        FileResource resource = null;
//
//        try {
//            resource = repository.getResource(location);
//        } catch (IOException e) {
//            throw new RepositoryException("Cannot get the following file from the repository : "
//                    + location, e);
//        }
//
//        LOG.debug("File is stored at location : " + file.getPath());

        return null;// DefaultDormFile.create(file);
    }

    @Override
    public File getBase() {
        return base;
    }

    private String getPathFromMetadata(DormMetadata metadata) {
        return metadata.getFullQualifier() + "/" + metadata.getExtension().getQualifier();
    }
}
