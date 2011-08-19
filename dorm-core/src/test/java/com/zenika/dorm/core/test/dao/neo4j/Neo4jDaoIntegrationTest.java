package com.zenika.dorm.core.test.dao.neo4j;

import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor;
import com.zenika.dorm.core.dao.neo4j.util.RequestExecutor;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.model.impl.Usage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Ignore
public class Neo4jDaoIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jDaoTest.class);

    private Dependency dependency21Response;
    private DependencyNode dependencyNode;
    private DormMetadata metadata20Response;
    private DormMetadataExtension extension19Response;
    private Usage usage;

    private Neo4jDaoTestProvider provider;
    private DormDaoNeo4j dao;

    @Before
    public void setUp() {

        dao = new DormDaoNeo4j();
        usage = Usage.create();
        extension19Response = new DefaultDormMetadataExtension("habi-base");
        metadata20Response = DefaultDormMetadata.create("0.6", "drom" ,extension19Response);
        dependency21Response = DefaultDependency.create(metadata20Response, usage);
        dependencyNode = DefaultDependencyNode.create(dependency21Response);
    }


    @Test
    public void push() throws Exception {
        dao.push(dependency21Response);
    }

    //
    @Test
    public void pushDependencyNode() {
        //dao.push(dependencyNode);
    }

    //
    @Test
    public void testPushWithRecursiveChildren() {
        DormMetadataExtension extension19Response;
        DormMetadata metadata20Response;
        Dependency dependency21Response;
        DependencyNode dependencyNode;
        List<DependencyNode> dependencies = new ArrayList<DependencyNode>();
        for (int i = 0; i < 100; i++) {
            extension19Response = new DefaultDormMetadataExtension("maven" + (i));
            metadata20Response = DefaultDormMetadata.create("1.0.0", "dorm", extension19Response);
            dependency21Response = DefaultDependency.create(metadata20Response, Usage.create("DEFAULT"));
            dependencyNode = DefaultDependencyNode.create(dependency21Response);
            dependencies.add(dependencyNode);
            if (i != 0) {
                dependencies.get(i - 1).addChild(dependencies.get(i));
            }
        }
        long time = System.currentTimeMillis();
        dao.push(dependencies.get(0));
        System.out.println("Times to standard push : " + (System.currentTimeMillis() - time));
    }

    //
    @Test
    public void testPushWithManyChildren() {
        DormMetadataExtension extension19Response = new DefaultDormMetadataExtension("maven");
        DormMetadata metadata20Response = DefaultDormMetadata.create("1.0.0","dorm",  extension19Response);
        Dependency dependency21Response = DefaultDependency.create(metadata20Response, Usage.create("DEFAULT"));
        DependencyNode dependencyNode = DefaultDependencyNode.create(dependency21Response);
        for (int i = 1; i < 100; i++) {
            DormMetadataExtension extensionBis = new DefaultDormMetadataExtension("maven" + (i * 10));
            DormMetadata metadataBis = DefaultDormMetadata.create("1.0.0", "dorm", extensionBis);
            Dependency dependencyBis = DefaultDependency.create(metadataBis, Usage.create("DEFAULT"));
            DependencyNode dependencyNodeBis = DefaultDependencyNode.create(dependencyBis);
            dependencyNode.addChild(dependencyNodeBis);
        }
        long time = System.currentTimeMillis();
        dao.push(dependencyNode);
        System.out.println("Times to standard push : " + (System.currentTimeMillis() - time));
    }

    //
    @Test
    public void getByMetaData() {
        long time = System.currentTimeMillis();
        dao.getSingleByMetadata(metadata20Response, usage);
        System.out.println("Times to standard push : " + (System.currentTimeMillis() - time));
    }

    //
    @Test
    public void getOtherByMetadata() {
        DormMetadataExtension extension19Response = new DefaultDormMetadataExtension("maven");
        DormMetadata metadata20Response = DefaultDormMetadata.create("1.0.0", "dorm", extension19Response);
        long time = System.currentTimeMillis();
        DependencyNode node = dao.getSingleByMetadata(metadata20Response, usage);
        LOG.info("Metadata : " + node.getDependency().getMetadata());
        LOG.info("Extension : " + node.getDependency().getMetadata().getExtension());
        System.out.println("Times to standard push : " + (System.currentTimeMillis() - time));
    }

    //
    @Test
    public void pushDependencyNodeWithChildren() {
        DependencyNode habi_base = createDependencyNode("habi-base", "0.6");
        DependencyNode commons_cli = createDependencyNode("commons-cli", "1.0");
        DependencyNode xercesImpl = createDependencyNode("xercesImpl", "2.4.0");
        DependencyNode commons_logging = createDependencyNode("commons-logging", "1.0.4");
        DependencyNode jdom = createDependencyNode("jdom", "1.1");
        DependencyNode xalan = createDependencyNode("xalan", "2.7.0");
        DependencyNode commons_lang = createDependencyNode("commons-lang", "1.0");
        DependencyNode junit = createDependencyNode("junit", "3.8.1");
        DependencyNode xml_apis = createDependencyNode("xml-apis", "1.0b2");
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
        System.out.println(habi_base.getDependency().getMetadata().getQualifier());
        dao.push(habi_base);
    }

    @Test
    public void testGetByMetadataExtension(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("groupId", "com.zenika");
        map.put("name", "MavenWebTest");
        map.put("version", "1.0-SNAPSHOT");
        DependencyNode node = dao.getByMetadataExtension(metadata20Response, usage, map);
        for (DependencyNode currentNode : node.getChildren()){
            LOG.trace("Dependency : " + currentNode.getDependency());
        }
    }

    private DependencyNode createDependencyNode(String name, String version) {
        DormMetadataExtension extension19Response = new DefaultDormMetadataExtension(name);
        DormMetadata metadata20Response = DefaultDormMetadata.create(version, "dorm", extension19Response);
        Dependency dependency21Response = DefaultDependency.create(metadata20Response, usage);
        DependencyNode dependencyNode = DefaultDependencyNode.create(dependency21Response);
        return dependencyNode;
    }
}
