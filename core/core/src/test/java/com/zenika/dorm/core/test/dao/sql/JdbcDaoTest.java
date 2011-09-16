package com.zenika.dorm.core.test.dao.sql;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.sql.DormDaoJdbc;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * todo: fix from refactoring
 *
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
//@Ignore
public class JdbcDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcDaoTest.class);

    public static DormDao dao;

    public Usage usage;

    private static DataSource dataSource;

    DependencyNode habi_base;
    DependencyNode commons_cli;
    DependencyNode xercesImpl;
    DependencyNode commons_logging;
    DependencyNode jdom;
    DependencyNode xalan;
    DependencyNode commons_lang;
    DependencyNode junit;
    DependencyNode xml_apis;

    @BeforeClass
    public static void setUpClass() {
        Injector injector = Guice.createInjector(new JdbcTestModule());
        dao = injector.getInstance(DormDaoJdbc.class);
//        JdbcPushExecutor executor = injector.getInstance(JdbcPushExecutor.class);
//        LOG.info("Database source: " + executor.getDataSource());
    }

    @Before
    public void setUp() {
        usage = Usage.create();

        habi_base = createDependencyNode("habi-base", "0.6");
        commons_cli = createDependencyNode("commons-cli", "1.0");
        xercesImpl = createDependencyNode("xercesImpl", "2.4.0");
        commons_logging = createDependencyNode("commons-logging", "1.0.4");
        jdom = createDependencyNode("jdom", "1.1");
        xalan = createDependencyNode("xalan", "2.7.0");
        commons_lang = createDependencyNode("commons-lang", "1.0");
        junit = createDependencyNode("junit", "3.8.1");
        xml_apis = createDependencyNode("xml-apis", "1.0b2");
//        habi_base.addChild(commons_cli);
//        habi_base.addChild(xercesImpl);
//        habi_base.addChild(commons_logging);
//        habi_base.addChild(jdom);
//        habi_base.addChild(junit);
//        habi_base.addChild(xalan);
//        commons_cli.addChild(commons_logging);
//        commons_cli.addChild(commons_lang);
//        commons_lang.addChild(junit);
//        xalan.addChild(xml_apis);
    }

    @Test
        public void testSaveMetadata() {
            dao.saveMetadata(new DormMetadata() {
                @Override
                public String getIdentifier() {
                    return "toto";
                }

                @Override
                public String getVersion() {
                    return "toto-1.0.0";  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public String getExtensionName() {
                    return "null";  //To change body of implemented methods use File | Settings | File Templates.
                }
            });
        }

    @Test
    public void testGetOne() {
//        DormServiceGetValues values = new DefaultDormServiceGetValues(new DefaultDormMetadataExtension("dorm"));
//        values.setUsage(usage);
//        values.setQualifier(habi_base.getDependency().getMetadata().getQualifier());
//        DependencyNode node = dao.getOne(values, false);
//        LOG.trace("Dependency : " + node.getDependency());
    }

    @Test
    public void testGet() {
//        DormServiceGetValues values = new DefaultDormServiceGetValues(new DefaultDormMetadataExtension("dorm"));
//        values.setUsage(usage);
//        values.addMetadataExtensionClause("groupId", "test");
//        values.addMetadataExtensionClause("versionId", "1.0.0");
//        List<DependencyNode> nodes = dao.get(values, false);
//        for (DependencyNode node : nodes) {
//            LOG.trace("Node : " + node);
//        }
    }

    @Test
    public void testGetMetadataByQualifier() {
        DormMetadata metadata = dao.getMetadataByQualifier("maven:com.zenika.test-test-jar-jar-0.0.1:0.0.1");
        LOG.info("Metadata: " + metadata);
    }

    @Test
    public void testGetMetadataByExtension() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("version", "0.0.1");
        List<DormMetadata> metadatas = dao.getMetadataByExtension("maven", map, null);
        for (DormMetadata metadata : metadatas) {
            LOG.info("Metadata: " + metadata);
        }
    }


    @AfterClass
    public static void afterClass() {
//        ((DormDaoJdbc) dao).closeConnection();
    }

    private DependencyNode createDependencyNode(String name, String version) {
//        DormMetadata extension = new DefaultDormMetadataExtension(name);
//        DormMetadata metadata = DefaultDormMetadata.create(version, extension);
//        Dependency dependency = DefaultDependency.create(metadata, usage);
//        DependencyNode dependencyNode = DefaultDependencyNode.create(dependency);
//        return dependencyNode;

        return null;
    }
}
