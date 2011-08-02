package com.zenika.dorm.maven.processor.extension;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.builder.DependencyBuilderFromRequest;
import com.zenika.dorm.core.model.builder.DormFileBuilderFromRequest;
import com.zenika.dorm.core.model.impl.DefaultDormRequest;
import com.zenika.dorm.core.processor.impl.AbstractProcessorExtension;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import org.apache.commons.io.FilenameUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * The maven processor needs to create an abstract dependency node which will be the parent of the
 * following maven nodes : maven pom node, maven jar node, maven sha1 node, etc...
 * The only difference between theses nodes is the file and his type : pom.xml, jar, etc...
 *
 * See : https://docs.google.com/drawings/d/1N1epmWY3dUy7th-VwrSNk1HXf6srEi0RoUoETQbe8qM/edit?hl=fr
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenProcessor extends AbstractProcessorExtension {

    public static final String ENTITY_TYPE = "entity";
    public static final String INTERNAL_USAGE = "maven_internal";

    /**
     * Metadata names
     */
    public static final String METADATA_GROUPID = "groupId";
    public static final String METADATA_ARTIFACTID = "artifactId";
    public static final String METADATA_VERSIONID = "versionId";

    @Override
    public DependencyNode push(DormRequest request) {

        // get the maven type from the filename
        String type = FilenameUtils.getExtension(request.getFilename());

        if (!type.equalsIgnoreCase("jar") && type.equalsIgnoreCase("pom") && type.equalsIgnoreCase("sha1")) {
            throw new MavenException("Invalid maven type : " + type);
        }

        // get the maven metadatas from the request
        String groupId = request.getProperty(METADATA_GROUPID);
        String artifactId = request.getProperty(METADATA_ARTIFACTID);
        String versionId = request.getProperty(METADATA_VERSIONID);

        // create the root entity
        MavenMetadataExtension rootExtension = new MavenMetadataExtension(groupId, artifactId, versionId,
                MavenProcessor.ENTITY_TYPE);

        Dependency rootDependency = new DependencyBuilderFromRequest(request, rootExtension).build();

        // create the real maven dependency to push
        MavenMetadataExtension extension = new MavenMetadataExtension(groupId, artifactId, versionId, type);

        if (!request.hasFile()) {
            throw new MavenException("File is required.");
        }

        // add the internal usage to the child node
        Map<String, String> newProperties = new HashMap<String, String>();
        newProperties.put(DormRequest.USAGE, INTERNAL_USAGE);
        request = DefaultDormRequest.createFromRequest(request, newProperties);

        DormFile file = new DormFileBuilderFromRequest(request).build();

        Dependency dependency = new DependencyBuilderFromRequest(request, extension).file(file).build();

        DependencyNode root = DefaultDependencyNode.create(rootDependency);
        DependencyNode node = DefaultDependencyNode.create(dependency);
        root.addChild(node);

        return root;
    }
}
