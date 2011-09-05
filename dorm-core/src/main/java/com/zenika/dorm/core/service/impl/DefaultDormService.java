package com.zenika.dorm.core.service.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.visitor.filter.WithResourceDependencyVisitorFilter;
import com.zenika.dorm.core.graph.visitor.impl.DependenciesCollector;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.config.DormServiceGetResourceConfig;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataResult;
import com.zenika.dorm.core.service.get.DormServiceGetMetadataValues;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.get.DormServiceGetResult;
import com.zenika.dorm.core.service.put.DormServicePutRequest;
import com.zenika.dorm.core.service.put.DormServiceStoreResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormService implements DormService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDormService.class);

    @Inject
    private DormDao dao;

    @Inject
    private DormRepository repository;

    @Override
    public DormServiceStoreResult store(DormServicePutRequest request) {
        return null;
    }

    @Override
    public DormServiceStoreResult storeMetadata(DormMetadata metadata) {
        return null;
    }

    @Override
    public void storeResource(DormResource resource, DormServiceStoreResourceConfig config) {

        if (config.isInternalResource()) {
            repository.store(config.getExtensionName(), config.getResourcePath(), resource, config.isOverride());
        }
    }

    @Override
    public DormServiceGetMetadataResult getMetadata(DormServiceGetMetadataValues values) {

        DormServiceGetMetadataResult result = new DormServiceGetMetadataResult();

        // if null then get by all usages
        Usage usage = values.getUsage();

        List<DormMetadata> metadatas = null;

        if (values.isGetByQualifier()) {

            DormMetadata metadata = dao.getByQualifier(values.getMetadata().getQualifier(), usage);

            if (null != metadata) {
                metadatas = new ArrayList<DormMetadata>();
                metadatas.add(metadata);
            }

        } else {
            metadatas = dao.getByMetadataExtension(values.getMetadata().getExtension().getExtensionName(),
                    values.getMetadataExtensionClauses(), usage);
        }

        result.setMetadatas(metadatas);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Result from database : " + result);
        }
        
        return result;
    }

    @Override
    public DormResource getResource(DormMetadata metadata, DormServiceGetResourceConfig config) {

//        repository.get(metadata)
        return null;
    }

    @Override
    public DormResource getResource(String extension, String path, DormServiceGetResourceConfig config) {
//        repository.get()
        return null;
    }

    public DormServiceGetResult get(DormServiceGetRequest request) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Get request : " + request);
        }

        DormServiceGetResult result = new DormServiceGetResult();

        if (request.isUniqueResultRequest()) {

            DependencyNode node = dao.getOne(request.getValues(), request.getTransitiveDependencies());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Get unique node : " + dao);
            }

            result.addNode(node);
        } else {
            List<DependencyNode> nodes = dao.get(request.getValues(), request.getTransitiveDependencies());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Get nodes : " + nodes);
            }
            result.setNodes(nodes);
        }

        if (!result.hasResult()) {
            LOG.debug("No result found for the get request : " + request);
            return result;
        }

        if (request.isRepositoryRequest()) {
            for (DependencyNode node : result.getNodes()) {
                Dependency dependency = getDependencyWithResource(node);
                node.setDependency(dependency);
            }
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("Get result : " + result);
        }

        return result;
    }

    public DormServiceStoreResult put(DormServicePutRequest request) {

        if (null == request || null == request.getValues()) {
            throw new CoreException("Service put request or values are null");
        }

        if (request.isEmpty()) {
            throw new CoreException("Service put request is empty");
        }

        DormResource resource = null;

        // database request
        if (request.isDatabaseRequest()) {

            DependencyNode node = request.getNode();
            if (null == node) {
                throw new CoreException("Node to save to database is null");
            }

            dao.push(node);

            // database request + repository request
            if (request.isRepositoryRequest()) {
                if (null != node.getDependency() && null != node.getDependency().getResource()) {
                    resource = node.getDependency().getResource();
                } else {
                    LOG.error("Request is repository request but node's resource is null, ignored");
                }
            }
        }

        // repository request only
        else {

            if (request.isRepositoryRequest()) {
                if (null != request.getResource()) {
                    resource = request.getResource();
                } else {
                    LOG.error("Request is repository request only but resource is null, ignored");
                }
            }
        }

        if (null != resource) {
            repository.store(resource, request.getValues());
        }

        DormServiceStoreResult result = new DormServiceStoreResult(request.getProcessName());
        return result;
    }

    private Dependency getDependencyWithResource(DependencyNode node) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Get resource for node : " + node);
        }

        DormMetadata metadata = node.getDependency().getMetadata();
        DormResource resource = repository.get(metadata);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Resource from the repository : " + resource);
        }

        if (null == resource) {
            return node.getDependency();
        }

        return DefaultDependency.create(metadata, resource);
    }

    private void repositoryPut(DependencyNode node) {
        DependenciesCollector visitor = new DependenciesCollector(node.getDependency().getUsage());
        visitor.addFilter(new WithResourceDependencyVisitorFilter());
        node.accept(visitor);
        Set<Dependency> dependencies = visitor.getDependencies();
        for (Dependency dependency : dependencies) {
            repository.put(dependency);
        }
    }
}
