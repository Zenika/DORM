package com.zenika.dorm.core.repository.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryResource;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.util.DormStringUtils;
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
    public void store(DormResource resource, DormMetadata metadata, DormServiceStoreResourceConfig config) {
        DormRepositoryResource repositoryResource = generateRepositoryResource(resource, metadata, config);
        deployEngine.deploy(repositoryResource);
    }

    @Override
    public void importResource(DormResource resource, DormMetadata metadata, DormServiceStoreResourceConfig config) {
        DormRepositoryResource repositoryResource = generateRepositoryResource(resource, metadata, config);
        deployEngine.copy(repositoryResource);
    }

    private DormRepositoryResource generateRepositoryResource(DormResource resource, DormMetadata metadata, DormServiceStoreResourceConfig config) {
        String path = getPathFromMetadata(metadata);
        LOG.debug("Store resource at location : {}", path);
        DormRepositoryResource repositoryResource = new DormRepositoryResource(path, resource.getFile());
        repositoryResource.setOverride(config.isOverride());
        return repositoryResource;
    }

    @Override
    public void store(String extension, String path, DormResource resource, boolean override) {

        if (DormStringUtils.oneIsBlank(extension, path)) {
            throw new CoreException("Extension and path are required to store internal resource");
        }

        if (null == resource || null == resource.getFile()) {
            throw new CoreException("File to store to the repository is required");
        }

        String fullPath = getBaseBuilder().append(INTERNAL_PATH_PREFIX)
                .append(extension)
                .append("/")
                .append(path)
                .toString();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Full path to store resource : " + fullPath);
        }

        DormRepositoryResource repositoryResource = new DormRepositoryResource(fullPath, resource.getFile());
        repositoryResource.setOverride(override);

        deployEngine.deploy(repositoryResource);
    }

    @Override
    public DormResource get(DormMetadata metadata, String extension) {

        LOG.debug("Get dorm file by metadata: {}", metadata);

        String location = getPathFromMetadata(metadata);
        LOG.debug("Get file at location: {}", location);

        DormRepositoryResource resource = resolveEngine.resolve(location);

        if (!resource.exists()) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Resource not found for metadata : {}", metadata);
            }

            return null;
        }

        return DefaultDormResource.create(resource.getFile());
    }

    private String getPathFromMetadata(DormMetadata metadata) {

        String path = getBaseBuilder()
                .append(metadata.getType().replace("/", "-"))
                .append("/")
                .append(metadata.getVersion().replace("/", "-"))
                .append("/")
                .append(metadata.getFunctionalId().replace("/", "-"))
                .toString();

        return path.replace(":", "-");
    }

    private StringBuilder getBaseBuilder() {
        return new StringBuilder(100)
                .append(base.getAbsolutePath())
                .append("/");
    }
}
