package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.get.DormServiceGetValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormDaoJdbc implements DormDao {

    private static final Logger LOG = LoggerFactory.getLogger(DormDaoJdbc.class);

    public DormDaoJdbc(){

    }
    @Inject
    private void init(){

    }

    @Override
    public Boolean push(Dependency node) {
//        new JdbcPushAbstractService(node).execute();
        return true;
    }

    @Override
    public void saveMetadata(DormMetadata metadata) {
//        new JdbcPushAbstractService(metadata).execute();
    }

    @Override
    public List<DependencyNode> get(DormServiceGetValues values, boolean withDependencies) {
//        JdbcSelectAbstractService executor = new JdbcSelectAbstractService(values);
//        executor.execute();
//        return executor.getNodes();
        return null;
    }

    @Override
    public DependencyNode getOne(DormServiceGetValues values, boolean withDependencies) {
//        JdbcSelectAbstractService executor = new JdbcSelectAbstractService(values);
//        executor.execute();
//        return executor.getNode();
        return null;
    }

    @Override
    public DormMetadata getMetadataByQualifier(String qualifier, Usage usage) {
//        JdbcSelectAbstractService executor = new JdbcSelectAbstractService(qualifier);
//        executor.execute();
//        return executor.getMetadata();
        return null;
    }

    @Override
    public List<DormMetadata> getMetadataByExtension(String extensionName, Map<String, String> extensionClauses, Usage usage) {
//        JdbcSelectAbstractService executor = new JdbcSelectAbstractService(extensionName, extensionClauses);
//        executor.execute();
//        return executor.getMetadatas();
        return null;
    }

}