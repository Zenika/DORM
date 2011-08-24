package com.zenika.dorm.core.dao.sql;

import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.graph.visitor.impl.DependenciesNodeCollector;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.Usage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormDaoJdbc implements DormDao {

    private static final Logger LOG = LoggerFactory.getLogger(DormDaoJdbc.class);

    private JdbcRequestExecutor executor;

    public DormDaoJdbc() {
        executor = new JdbcRequestExecutor();
    }

    @Override
    public Boolean push(Dependency node) {
        insertMetadataIfNotExist(node);
        executor.commit();
        return true;
    }

    @Override
    public Boolean push(DependencyNode node) {
        if (node.getChildren().isEmpty()) {
            return push(node);
        }
        DependenciesNodeCollector collector = new DependenciesNodeCollector(node.getDependency().getUsage());
        node.accept(collector);
        Set<DependencyNode> nodes = collector.getDependencies();
        for (DependencyNode currentNode : nodes) {
            insertDependencyNode(currentNode);
        }
        executor.commit();
        return true;
    }

    @Override
    public DependencyNode getSingleByMetadata(DormMetadata metadata, Usage usage) {
        Dependency dependency = executor.selectDependency(metadata);
        return DefaultDependencyNode.create(dependency);
    }

    @Override
    public DependencyNode getByMetadata(DormMetadata metadata, Usage usage) {
        return null;
    }

    @Override
    public DependencyNode getByMetadataExtension(DormMetadata metadata, Usage usage, Map<String, String> params) {
        return executor.selectDependencyNode(metadata, usage, params);
    }

    public void closeConnection() {
        executor.close();
    }

    public Long insertMetadataIfNotExist(Dependency dependency) {
        Long id = executor.getDependencyId(dependency.getMetadata());
        if (id == null) {
            return this.insertDependency(dependency);
        } else {
            LOG.debug("The dependency " + dependency.getMetadata().getQualifier() + " already insert in database");
            return executor.getDependencyId(dependency.getMetadata());
        }
    }

    public void insertDependencyNode(DependencyNode node) {
        Long idDependency = insertMetadataIfNotExist(node.getDependency());
        List<Long> childrenId = new ArrayList<Long>(node.getChildren().size());
        for (DependencyNode child : node.getChildren()) {
            childrenId.add(insertMetadataIfNotExist(child.getDependency()));
        }
        if (!childrenId.isEmpty()) {
            executor.insertDependencyNode(idDependency, childrenId, node.getDependency().getUsage());
        }
    }

    public boolean isMetadataExist(DormMetadata metadata) {
        return executor.getDependencyId(metadata) != null;
    }



    public Long insertDependency(Dependency dependency) {
        DormMetadata metadata = dependency.getMetadata();
        DormMetadataExtension extension = metadata.getExtension();
        Long id = executor.insertMetadata(metadata);
        executor.insertExtension(extension, id);
        return id;
    }
}