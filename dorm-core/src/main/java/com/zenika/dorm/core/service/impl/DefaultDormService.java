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
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.get.DormServiceGetResult;
import com.zenika.dorm.core.service.impl.get.DefaultDormServiceGetResult;
import com.zenika.dorm.core.service.impl.put.DefaultDormServicePutResult;
import com.zenika.dorm.core.service.put.DormServicePutRequest;
import com.zenika.dorm.core.service.put.DormServicePutResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public DormServiceGetResult get(DormServiceGetRequest request) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Get request : " + request);
        }

        DormServiceGetResult result = new DefaultDormServiceGetResult(request.getProcessName());

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

    @Override
    public DormServicePutResult put(DormServicePutRequest request) {

        DependencyNode node = request.getNode();

        if (null == node) {
            throw new CoreException("There is no node to put");
        }

        if (request.isRepositoryRequest()) {
            repositoryPut(node);
        }

        dao.push(node);

        DefaultDormServicePutResult result = new DefaultDormServicePutResult(request.getProcessName());
        result.setSavedNode(node);

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
