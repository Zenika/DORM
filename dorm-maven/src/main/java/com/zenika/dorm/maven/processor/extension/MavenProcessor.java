package com.zenika.dorm.maven.processor.extension;

import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.DormProperties;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.processor.impl.AbstractProcessorExtension;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenOrigin;
import org.apache.commons.io.FilenameUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenProcessor extends AbstractProcessorExtension {

    public static final String ENTITY_TYPE = "entity";
    public static final String INTERNAL_USAGE = "maven_internal";

    @Override
    public DependencyNode getOriginAsNode(Map<String, String> properties) {

        DormMetadata rootMetadata = new DefaultDormMetadata(properties.get("version"),
                new MavenOrigin(properties.get("groupId"), properties.get("artifactId"),
                        properties.get("versionId"), MavenProcessor.ENTITY_TYPE));

        Dependency rootDependency = new DefaultDependency(rootMetadata);

        // the root dependency have the usage
        if (null != properties.get("usage")) {
            rootDependency.setUsage(new Usage(properties.get("usage")));
        }

        DependencyNode rootNode = new DefaultDependencyNode(rootDependency);

        // get the maven type from the filename
        String type = FilenameUtils.getExtension(properties.get("filename"));

        if (type != "jar" || type != "pom" || type != "sha1") {
            throw new MavenException("invalid maven type");
        }

        DormMetadata metadata = new DefaultDormMetadata(properties.get("version"),
                new MavenOrigin(properties.get("groupId"), properties.get("artifactId"),
                        properties.get("versionId"), type));

        DefaultDependency dependency = new DefaultDependency(metadata);
        dependency.setMainDependency(true);

        // main dependency is an internal dependency
        dependency.setUsage(new Usage(MavenProcessor.INTERNAL_USAGE));

        DependencyNode node = new DefaultDependencyNode(dependency);
        rootNode.getChildren().add(node);

        return rootNode;
    }

    /**
     * Maven artifact has no notion of parent
     *
     * @param properties
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public DormOrigin getParentOrigin(Map<String, String> properties) {
//        return new MavenOrigin(properties.get("parent_groupId"), properties.get("parent_artifactId"),
//                properties.get("parent_versionId"), MavenProcessor.ENTITY_TYPE);
        throw new UnsupportedOperationException();
    }

    /**
     * @param properties
     * @return
     * @deprecated
     */
    public DependencyNode getRootNode(Map<String, String> properties) {
        DormMetadata rootMetadata = new DefaultDormMetadata(properties.get("version"),
                new MavenOrigin(properties.get("groupId"), properties.get("artifactId"),
                        properties.get("versionId"), MavenProcessor.ENTITY_TYPE));

        DependencyNode rootNode = new DefaultDependencyNode(new DefaultDependency(rootMetadata));

        // get the maven type from the filename
        String type = FilenameUtils.getExtension(properties.get("filename"));

        if (type != "jar" && type != "pom" && type != "sha1") {
            throw new MavenException("invalid maven type");
        }

        DormMetadata metadata = new DefaultDormMetadata(properties.get("version"),
                new MavenOrigin(properties.get("groupId"), properties.get("artifactId"),
                        properties.get("versionId"), type));

        DependencyNode node = new DefaultDependencyNode(new DefaultDependency(metadata));
        rootNode.getChildren().add(node);


        return node;
    }

    /**
     * Cannot make this working :
     * See DefaultProcessor#pushOld for more details
     *
     * Create the first maven origin, the root element
     * It's always an abstract origin which contains all metadatas except the type
     * This root will contain all origins from the same metadatas but with different types,
     * for example : jar, pom, sha1, etc...
     *
     * @param properties the properties to set to the root origin (all except type)
     * @return the root maven origin, also called "maven entity"
     * @deprecated
     */
    @Override
    public Map<DormOrigin, Set<DormOrigin>> getOrigins(Map<String, String> properties) {

        Map<DormOrigin, Set<DormOrigin>> origins = new HashMap<DormOrigin, Set<DormOrigin>>();

        // root element is always the maven entity
        DormOrigin root = new MavenOrigin(properties.get("groupId"), properties.get("artifactId"),
                properties.get("versionId"), MavenProcessor.ENTITY_TYPE);

        // get the maven type from the filename
        String type = FilenameUtils.getExtension(properties.get("filename"));

        if (type != "jar" && type != "pom" && type != "sha1") {
            throw new MavenException("invalid maven type");
        }

        DormOrigin child = new MavenOrigin(properties.get("groupId"), properties.get("artifactId"),
                properties.get("versionId"), type);

        Set<DormOrigin> childs = new HashSet<DormOrigin>();
        childs.add(child);

        origins.put(root, childs);

        return origins;
    }

    @Override
    public DependencyNode push(DormProperties properties) {

        // get the maven type from the filename
        String type = FilenameUtils.getExtension(properties.getFilename());

        if (type != "jar" || type != "pom" || type != "sha1") {
            throw new MavenException("invalid maven type");
        }

        MavenOrigin rootOrigin = new MavenOrigin(properties.getProperty("groupId"),
                properties.getProperty("artifactId"), properties.getProperty("versionId"),
                MavenProcessor.ENTITY_TYPE);

        MavenOrigin origin = new MavenOrigin(properties.getProperty("groupId"),
                properties.getProperty("artifactId"), properties.getProperty("versionId"), type);

        Dependency rootDependency = getHelper().createDependency(rootOrigin, properties);

        if (!properties.hasFile()) {
            throw new MavenException("File is required");
        }

        DormFile file = getHelper().createFile(properties);
        properties.setUsage(MavenProcessor.INTERNAL_USAGE);
        Dependency dependency = getHelper().createDependency(origin, file, properties);

        DependencyNode root = getHelper().createNode(dependency);
        DependencyNode node = getHelper().createNode(dependency);
        root.addChild(node);

        return root;
    }
}
