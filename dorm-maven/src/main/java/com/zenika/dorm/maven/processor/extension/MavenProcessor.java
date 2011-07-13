package com.zenika.dorm.maven.processor.extension;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.processor.ProcessorExtension;
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
public class MavenProcessor implements ProcessorExtension {

    public static final String ENTITY_TYPE = "entity";

    private Dependency currentDependency;

    @Override
    public Dependency push(DormMetadata metadata) {
        return null;
    }

    /**
     * Create maven entity that will englobe
     *
     * @param properties
     * @return
     */
    @Override
    public DormOrigin getOrigin(Map<String, String> properties) {

        return null;
    }

    public DependencyNode getRootNode(Map<String, String> properties) {
        DormMetadata rootMetadata = new DefaultDormMetadata(properties.get("version"),
                new MavenOrigin(properties.get("groupId"), properties.get("artifactId"),
                        properties.get("versionId"), MavenProcessor.ENTITY_TYPE));

        DependencyNode rootNode = new DefaultDependencyNode(new DefaultDependency(rootMetadata));

        // get the maven type from the filename
        String type = FilenameUtils.getExtension(properties.get("filename"));

        if (type != "jar" || type != "pom" || type != "sha1") {
            throw new MavenException("invalid maven type");
        }

        DormMetadata metadata = new DefaultDormMetadata(properties.get("version"),
                new MavenOrigin(properties.get("groupId"), properties.get("artifactId"),
                        properties.get("versionId"), type));

        DependencyNode node = new DefaultDependencyNode(new DefaultDependency(metadata));
        rootNode.getChildrens().add(node);


        return node;
    }

    public Dependency getCurrentDependency() {
        if (null == currentDependency) {
            throw new MavenException("there is no current dependency, you must call getRootNode() first");
        }

        return null;
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
     */
    public Map<DormOrigin, Set<DormOrigin>> getOrigins(Map<String, String> properties) {

        Map<DormOrigin, Set<DormOrigin>> origins = new HashMap<DormOrigin, Set<DormOrigin>>();

        // root element is always the maven entity
        DormOrigin root = new MavenOrigin(properties.get("groupId"), properties.get("artifactId"),
                properties.get("versionId"), MavenProcessor.ENTITY_TYPE);

        // get the maven type from the filename
        String type = FilenameUtils.getExtension(properties.get("filename"));

        if (type != "jar" || type != "pom" || type != "sha1") {
            throw new MavenException("invalid maven type");
        }

        DormOrigin child = new MavenOrigin(properties.get("groupId"), properties.get("artifactId"),
                properties.get("versionId"), type);

        Set<DormOrigin> childs = new HashSet<DormOrigin>();
        childs.add(child);

        origins.put(root, childs);

        return origins;
    }
}
