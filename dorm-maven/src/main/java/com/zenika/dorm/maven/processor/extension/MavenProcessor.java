package com.zenika.dorm.maven.processor.extension;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.builder.DependencyBuilderFromRequest;
import com.zenika.dorm.core.model.impl.DefaultDormRequest;
import com.zenika.dorm.core.processor.impl.AbstractProcessorExtension;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(MavenProcessor.class);

    public static final String ENTITY_TYPE = "entity";

    /**
     * Metadata names
     */
    public static final String METADATA_GROUPID = "groupId";
    public static final String METADATA_ARTIFACTID = "artifactId";
    public static final String METADATA_VERSIONID = "versionId";

    @Override
    public DependencyNode push(DormRequest request) {

        LOG.debug("Maven request to push = " + request);

        if (!request.hasFile()) {
            throw new MavenException("File is required.");
        }

        // get the maven type from the filename
        String type = FilenameUtils.getExtension(request.getFilename());

        LOG.debug("Type of the maven file = " + type);

        if (!type.equalsIgnoreCase("jar") && type.equalsIgnoreCase("pom") && type.equalsIgnoreCase("sha1")) {
            throw new MavenException("Invalid maven type : " + type);
        }

        // get the maven metadatas from the request
        String groupId = request.getProperty(METADATA_GROUPID);
        String artifactId = request.getProperty(METADATA_ARTIFACTID);
        String versionId = request.getProperty(METADATA_VERSIONID);

        // create the entity extension which is the same as the child with a different type
        MavenMetadataExtension entityExtension = new MavenMetadataExtension(groupId, artifactId, versionId,
                MavenProcessor.ENTITY_TYPE);

        // entity dependencuy has no file
        Map<String, String> entityProperties = new HashMap<String, String>();
        entityProperties.put(DormRequest.FILENAME, null);
        DormRequest entityRequest = DefaultDormRequest.createFromRequest(request, entityProperties);

        Dependency rootDependency = new DependencyBuilderFromRequest(request,
                entityExtension).file(null).build();
        LOG.debug("Maven entity dependency = " + rootDependency);

        // create the real maven dependency to push
        MavenMetadataExtension childExtension = new MavenMetadataExtension(groupId, artifactId, versionId, type);

        // replace the default usage by the maven internal for the child dependency
        Usage childUsage = Usage.createInternal(MavenMetadataExtension.NAME);

        Dependency dependency = new DependencyBuilderFromRequest(request, childExtension).usage(childUsage).build();
        LOG.debug("Maven real dependency = " + dependency);

        DependencyNode root = DefaultDependencyNode.create(rootDependency);
        DependencyNode node = DefaultDependencyNode.create(dependency);
        root.addChild(node);

        return root;
    }
}
