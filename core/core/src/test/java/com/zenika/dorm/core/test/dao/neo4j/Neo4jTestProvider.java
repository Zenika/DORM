package com.zenika.dorm.core.test.dao.neo4j;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.test.model.DormMetadataTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jTestProvider {

    private static final Usage USAGE = Usage.create("test_usage");

    private static final String ARTIFACT_ID_HABI_BASE = "habi-base";
    private static final String ARTIFACT_ID_JUNIT = "junit";
    private static final String ARTIFACT_ID_COMMONS_LANG = "commons-lang";
    private static final String ARTIFACT_ID_COMMONS_CLI = "commons-cli";
    private static final String ARTIFACT_ID_COMMONS_LOGGING = "commons-logging";
    private static final String ARTIFACT_ID_JDORM = "jdorm";
    private static final String ARTIFACT_ID_XERCES_IMPL = "xerces-impl";
    private static final String ARTIFACT_ID_XALAN = "xalan";
    private static final String ARTIFACT_ID_XML_API = "xml-api";

    private static final String VERSION_ID_HABI_BASE = "0.6";
    private static final String VERSION_ID_JUNIT = "3.8.1";
    private static final String VERSION_ID_COMMONS_LANG = "1.0";
    private static final String VERSION_ID_COMMONS_CLI = "1.0";
    private static final String VERSION_ID_COMMONS_LOGGING = "1.0.4";
    private static final String VERSION_ID_JDORM = "1.1";
    private static final String VERSION_ID_XERCES_IMPL = "2.4.0";
    private static final String VERSION_ID_XALAN = "2.7.0";
    private static final String VERSION_ID_XML_API = "1.0b2";

    private DependencyNode habiBase;
    private DependencyNode junit;
    private DependencyNode commonsLang;
    private DependencyNode commonsCli;
    private DependencyNode commonsLogging;
    private DependencyNode jdorm;
    private DependencyNode xercesImpl;
    private DependencyNode xalan;
    private DependencyNode xmlApi;

    private DependencyNode root;

    private static Neo4jTestProvider provider;

    private Neo4jTestProvider() {
        init();
        root = createDependencyTree();
    }

    public static Neo4jTestProvider getProvider() {
        if (provider == null) {
            provider = new Neo4jTestProvider();
        }
        return provider;
    }

    public DependencyNode getRoot() {
        return root;
    }

    public List<DependencyNode> getDependencies(){
        List<DependencyNode> dependencyNodes = new ArrayList<DependencyNode>();
        dependencyNodes.add(habiBase);
        dependencyNodes.add(commonsCli);
        dependencyNodes.add(commonsLang);
        dependencyNodes.add(commonsLogging);
        dependencyNodes.add(jdorm);
        dependencyNodes.add(junit);
        dependencyNodes.add(xalan);
        dependencyNodes.add(xercesImpl);
        dependencyNodes.add(xmlApi);
        return dependencyNodes;
    }

    private DependencyNode createDependencyTree() {
        habiBase.addChild(junit);
        habiBase.addChild(commonsCli);
        habiBase.addChild(commonsLogging);
        habiBase.addChild(jdorm);
        habiBase.addChild(xercesImpl);
        habiBase.addChild(xalan);

        commonsLang.addChild(junit);

        commonsCli.addChild(commonsLang);
        commonsCli.addChild(commonsLogging);

        xalan.addChild(xmlApi);

        return habiBase;
    }

    private void init() {
        habiBase = createDependencyNode(ARTIFACT_ID_HABI_BASE, VERSION_ID_HABI_BASE);
        junit = createDependencyNode(ARTIFACT_ID_JUNIT, VERSION_ID_JUNIT);
        commonsLang = createDependencyNode(ARTIFACT_ID_COMMONS_LANG, VERSION_ID_COMMONS_LANG);
        commonsCli = createDependencyNode(ARTIFACT_ID_COMMONS_CLI, VERSION_ID_COMMONS_CLI);
        commonsLogging = createDependencyNode(ARTIFACT_ID_COMMONS_LOGGING, VERSION_ID_COMMONS_LOGGING);
        jdorm = createDependencyNode(ARTIFACT_ID_JDORM, VERSION_ID_JDORM);
        xercesImpl = createDependencyNode(ARTIFACT_ID_XERCES_IMPL, VERSION_ID_XERCES_IMPL);
        xalan = createDependencyNode(ARTIFACT_ID_XALAN, VERSION_ID_XALAN);
        xmlApi = createDependencyNode(ARTIFACT_ID_XML_API, VERSION_ID_XML_API);
    }

    private DependencyNode createDependencyNode(String version, String artifactId) {
        DormMetadata metadata = new DormMetadataTest(version, artifactId);
        Dependency dependency = DefaultDependency.create(metadata, USAGE);
        return DefaultDependencyNode.create(dependency);
    }
}
