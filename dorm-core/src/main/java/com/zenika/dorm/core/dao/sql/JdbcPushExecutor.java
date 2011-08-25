package com.zenika.dorm.core.dao.sql;

import com.zenika.dorm.core.graph.visitor.impl.DependenciesNodeCollector;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class JdbcPushExecutor extends JdbcExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcPushExecutor.class);

    private DependencyNode node;
    private DormMetadata metadata;
    private DormMetadataExtension extension;

    private Usage usage;

    private Long id;

    public JdbcPushExecutor(Connection connection, Dependency dependency) {
        super(connection);
        this.metadata = dependency.getMetadata();
        this.extension = metadata.getExtension();
        this.usage = dependency.getUsage();
    }

    public JdbcPushExecutor(Connection connection, DependencyNode node) {
        super(connection);
        this.node = node;
        this.usage = node.getDependency().getUsage();
        this.metadata = node.getDependency().getMetadata();
        this.extension = metadata.getExtension();
    }

    public void execute() {
        if (node == null || node.getChildren().isEmpty()) {
            push();
        } else {
            pushWithChildren();
        }
        commit();
    }

    private Long push() {
        getDependencyId();
        if (id == null) {
            insertMetadata();
            insertExtension();
        } else {
            LOG.debug("The dependency " + metadata.getQualifier() + " already insert in database");
        }
        return id;
    }

    private void pushWithChildren() {
        DependenciesNodeCollector collector = new DependenciesNodeCollector(node.getDependency().getUsage());
        node.accept(collector);
        Set<DependencyNode> nodes = collector.getDependencies();
        for (DependencyNode currentNode : nodes) {
            JdbcPushExecutor executor = new JdbcPushExecutor(connection, currentNode.getDependency());
            Long idDependency = executor.push();
            List<Long> childrenId = new ArrayList<Long>(currentNode.getChildren().size());
            for (DependencyNode child : currentNode.getChildren()) {
                executor = new JdbcPushExecutor(connection, child);
                childrenId.add(executor.push());
            }
            if (!childrenId.isEmpty()) {
                executor.insertDependencyNode(idDependency, childrenId, usage);
            }
        }
    }

    private void getDependencyId() {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT id FROM dorm_metadata WHERE metadata_qualifier = ?");
            statement.setString(1, metadata.getQualifier());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                id = result.getLong(ID_COLUMN);
            }
        } catch (SQLException e) {
            rollbackSQLException(e);
        } finally {
            finallySQLException(statement);
        }
    }

    private void insertMetadata() {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO dorm_metadata (metadata_qualifier, metadata_version, metadata_type) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, metadata.getQualifier());
            statement.setString(2, metadata.getVersion());
            statement.setString(3, metadata.getType());
            if (statement.executeUpdate() > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getLong(1);
                }
            }
            LOG.debug("Id of the new Metadata : " + id);
        } catch (SQLException e) {
            rollbackSQLException(e);
        } finally {
            finallySQLException(statement);
        }
    }

    private void insertExtension() {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO dorm_extension (property_key, property_value, extension_qualifier, extension_name, metadata_id) VALUES (?, ?, ?, ?, ?)");
            for (Map.Entry<String, String> properties : MetadataExtensionMapper.fromExtension(extension).entrySet()) {
                statement.setString(1, properties.getKey());
                statement.setString(2, properties.getValue());
                statement.setString(3, extension.getQualifier());
                statement.setString(4, extension.getExtensionName());
                statement.setLong(5, id);
                statement.execute();
                statement.clearParameters();
            }
        } catch (SQLException e) {
            rollbackSQLException(e);
        } finally {
            finallySQLException(statement);
        }
    }

    private void insertDependencyNode(Long idParent, List<Long> idChildren, Usage usage) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM dorm_dependencies WHERE id_parent = ? AND id_child = ?",
                    Statement.RETURN_GENERATED_KEYS,
                    ResultSet.CONCUR_UPDATABLE);
            for (Long idChild : idChildren) {
                statement.setLong(1, idParent);
                statement.setLong(2, idChild);
                resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    resultSet.moveToInsertRow();
                    resultSet.updateLong(ID_PARENT_COLUMN, idParent);
                    resultSet.updateLong(ID_CHILD_COLUMN, idChild);
                    resultSet.updateString(USAGE_COLUMN, usage.getName());
                    resultSet.insertRow();
                }
                statement.clearParameters();
            }
        } catch (SQLException e) {
            rollbackSQLException(e);
        } finally {
            finallySQLException(statement, resultSet);
        }
    }


}
