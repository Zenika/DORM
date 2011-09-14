package com.zenika.dorm.core.test.dao.neo4j;

import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.impl.Usage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Ignore
public class Neo4jDaoIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jDaoIntegrationTest.class);

    DependencyNode habi_base;
    DependencyNode commons_cli;
    DependencyNode xercesImpl;
    DependencyNode commons_logging;
    DependencyNode jdom;
    DependencyNode xalan;
    DependencyNode commons_lang;
    DependencyNode junit;
    DependencyNode xml_apis;

    private Usage usage;

    private Neo4jDaoTestProvider provider;
    private static DormDaoNeo4j dao;

    @BeforeClass
    public static void setUpClass() {
        dao = new DormDaoNeo4j();
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
        habi_base.addChild(commons_cli);
        habi_base.addChild(xercesImpl);
        habi_base.addChild(commons_logging);
        habi_base.addChild(jdom);
        habi_base.addChild(junit);
        habi_base.addChild(xalan);
        commons_cli.addChild(commons_logging);
        commons_cli.addChild(commons_lang);
        commons_lang.addChild(junit);
        xalan.addChild(xml_apis);
    }


    //
    @Test
    public void pushDependencyNode() {
        //dao.push(dependencyNode);
    }

    //
    @Test
    public void testGet() {
//        DormServiceGetValues values = new DefaultDormServiceGetValues(new DefaultDormMetadataExtension("dorm"));
//        values.setUsage(usage);
//        values.addMetadataExtensionClause("groupId", "test");
//        values.addMetadataExtensionClause("versionId", "1.0.0");
//        List<DependencyNode> nodes = dao.get(values, false);
//        for (DependencyNode node : nodes) {
//            LOG.trace("Dependency : " + node.getDependency());
//        }
    }

    @Test
    public void testGetWithChildren() {
//        DormServiceGetValues values = new DefaultDormServiceGetValues(new DefaultDormMetadataExtension("dorm"));
//        values.setUsage(usage);
//        values.addMetadataExtensionClause("groupId", "test");
//        values.addMetadataExtensionClause("versionId", "1.0.0");
//        List<DependencyNode> nodes = dao.get(values, true);
//        for (DependencyNode node : nodes) {
//            LOG.trace("Dependency : " + node.getDependency());
//            DependenciesNodeCollector visitor = new DependenciesNodeCollector(node.getDependency().getUsage());
//            node.accept(visitor);
//            Set<DependencyNode> children = visitor.getDependencies();
//            for (DependencyNode child : children) {
//                LOG.trace("Dependency Child : " + child.getDependency());
//            }
//        }
    }

    @Test
    public void testGetOne() {
//        DormServiceGetValues values = new DefaultDormServiceGetValues(new DefaultDormMetadataExtension("dorm"));
//        values.setQualifier(habi_base.getDependency().getMetadata().getQualifier());
//        values.setUsage(usage);
//        DependencyNode node = dao.getOne(values, false);
//        LOG.trace("Dependency : " + node.getDependency());
    }

    @Test
    public void testGetOneWithChildren() {
//        DormServiceGetValues values = new DefaultDormServiceGetValues(new DefaultDormMetadataExtension("dorm"));
//        values.setQualifier(habi_base.getDependency().getMetadata().getQualifier());
//        values.setUsage(usage);
//        DependencyNode node = dao.getOne(values, true);
//        LOG.trace("Dependency : " + node.getDependency());
//        DependenciesNodeCollector visitor = new DependenciesNodeCollector(node.getDependency().getUsage());
//        node.accept(visitor);
//        Set<DependencyNode> children = visitor.getDependencies();
//        for (DependencyNode child : children) {
//            LOG.trace("Dependency Child : " + child.getDependency());
//        }
    }

    /**
     * todo: fix from refactoring
     *
     * @param name
     * @param version
     * @return
     */
    private DependencyNode createDependencyNode(String name, String version) {
//        DormMetadata extension19Response = new DefaultDormMetadataExtension(name);
//        DormMetadata metadata20Response = DefaultDormMetadata.create(version, extension19Response);
//        Dependency dependency21Response = DefaultDependency.create(metadata20Response, usage);
//        DependencyNode dependencyNode = DefaultDependencyNode.create(dependency21Response);
//        return dependencyNode;
        return null;
    }
}
