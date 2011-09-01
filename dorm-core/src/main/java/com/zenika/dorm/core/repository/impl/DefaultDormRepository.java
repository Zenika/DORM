package com.zenika.dorm.core.repository.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.RepositoryException;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryResource;
import com.zenika.dorm.core.service.put.DormServicePutValues;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormRepository implements DormRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDormRepository.class);

    private static final String DEFAULT_REPOSITORY_LOCATION = "tmp/repo-dorm";
    private static final String INTERNAL_PATH_PREFIX = "_internal_";

    private File base;

    private DormRepositoryResolveEngine resolveEngine = new DormRepositoryResolveEngine(this);
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

        if (LOG.isDebugEnabled()) {
            LOG.debug("Put dependency = " + dependency);
        }

        DormResource file = dependency.getResource();
        DormMetadata metadata = dependency.getMetadata();

        if (file == null) {
            throw new RepositoryException("Dorm file is required");
        }

        String location = getPathFromMetadata(metadata);
        DormRepositoryResource resource = new DormRepositoryResource(file.getFile(), this, location);

        deployEngine.deploy(resource);

        LOG.debug("File is stored to the repository : " + resource.getPath());

        return true;
    }

    @Override
    public boolean store(DormResource resource, DormServicePutValues values) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Resource to store to the repository : " + resource);
        }

        StringBuilder builder = new StringBuilder(50);
        builder.append("/");

        if (values.isResourceInternal()) {

            if (StringUtils.isBlank(values.getExtensionName())) {
                throw new RepositoryException("Resource is internal but value's extension name is null");
            }

            String extensionName = FilenameUtils.normalizeNoEndSeparator(values.getExtensionName());

            builder.append(INTERNAL_PATH_PREFIX)
                    .append(extensionName)
                    .append("/");
        }

        builder.append(values.getResourcePath());

        String foldersPath = FilenameUtils.normalizeNoEndSeparator(builder.toString());

        File folders = new File(foldersPath);
        folders.mkdirs();

        String location = foldersPath + "/" + FilenameUtils.normalizeNoEndSeparator(resource.getFilename());

        DormRepositoryResource repositoryResource = new DormRepositoryResource(resource.getFile(), this, location);
        deployEngine.deploy(repositoryResource);

        return true;
    }

    @Override
    public DormResource get(DormMetadata metadata) {

        LOG.debug("Get dorm file by metadata : " + metadata);

        String location = getPathFromMetadata(metadata);
        LOG.debug("Get file at location : " + location);

        DormRepositoryResource resource = resolveEngine.resolve(location);

        if (!resource.exists()) {
            throw new RepositoryException("File not found for metadata : " + metadata);
        }

        return DefaultDormResource.create(resource.getFile());
    }

    @Override
    public File getBase() {
        return base;
    }

    /**
     * Pattern from metadata
     *
     * @param metadata
     * @return
     */
    private String getPathFromMetadata(DormMetadata metadata) {
        return metadata.getQualifier() + "/" + metadata.getExtension().getQualifier();
    }
}
