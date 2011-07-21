package com.zenika.dorm.maven.processor.extension;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.processor.impl.AbstractProcessorExtension;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenOrigin;
import org.apache.commons.io.FilenameUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenProcessor extends AbstractProcessorExtension {

    public static final String ENTITY_TYPE = "entity";
    public static final String INTERNAL_USAGE = "maven_internal";

    @Override
    public DependencyNode push(DormRequest request) {

        // get the maven type from the filename
        String type = FilenameUtils.getExtension(request.getFilename());

        if (type != "jar" || type != "pom" || type != "sha1") {
            throw new MavenException("invalid maven type");
        }

        MavenOrigin rootOrigin = new MavenOrigin(request.getProperty("groupId"),
                request.getProperty("artifactId"), request.getProperty("versionId"),
                MavenProcessor.ENTITY_TYPE);

        MavenOrigin origin = new MavenOrigin(request.getProperty("groupId"),
                request.getProperty("artifactId"), request.getProperty("versionId"), type);

        Dependency rootDependency = getRequestProcessor().createDependency(rootOrigin, request);

        if (!request.hasFile()) {
            throw new MavenException("File is required");
        }

        DormFile file = getRequestProcessor().createFile(request);

        // todo: fix this
//        request.setUsage(MavenProcessor.INTERNAL_USAGE);
        Dependency dependency = getRequestProcessor().createDependency(origin, file, request);

        DependencyNode root = getRequestProcessor().createNode(dependency);
        DependencyNode node = getRequestProcessor().createNode(dependency);
        root.addChild(node);

        return root;
    }
}
