package com.zenika.dorm.maven.pom;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenBuildInfoBuilder;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import com.zenika.dorm.maven.service.MavenService;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenPomReader {

    private static final Logger LOG = LoggerFactory.getLogger(MavenPomReader.class);

    private Model model;

    public MavenPomReader(File pom) {
        try {
            model = new MavenXpp3Reader().read(new FileInputStream(pom));
        } catch (Exception e) {
            throw new MavenException("Maven metadata file cannot be read", e);
        }
    }

    public MavenPomReader(InputStream stream) {
        try {
            model = new MavenXpp3Reader().read(stream);
        } catch (IOException e) {
            throw new MavenException("", e);
        } catch (XmlPullParserException e) {
            throw new MavenException("", e);
        }
    }

    public MavenMetadata getArtifact() {

        MavenBuildInfo buildInfo = new MavenBuildInfoBuilder()
                .extension("pom").build();

        String groupId = null;
        if (StringUtils.isNotBlank(model.getGroupId())) {
            groupId = model.getGroupId();
        } else if (null != model.getParent()) {
            groupId = model.getParent().getGroupId();
        }

        checkArgument(StringUtils.isNotBlank(groupId), "Unable to determine the groupId from the pom");

        String version = null;
        if (StringUtils.isNotBlank(model.getVersion())) {
            version = model.getVersion();
        } else if (null != model.getParent()) {
            version = model.getParent().getVersion();
        }

        checkArgument(StringUtils.isNotBlank(version), "Unable to determine the version from the pom");

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven pom groupId : " + groupId);
            LOG.debug("Maven pom version : " + version);
        }

        return new MavenMetadataBuilder()
                .artifactId(model.getArtifactId())
                .groupId(groupId)
                .version(version)
                .buildInfo(buildInfo)
                .build();
    }

    public List<Dependency> getDependencies(MavenService mavenService) {

        List<Dependency> dependencies = new ArrayList<Dependency>();

        for (org.apache.maven.model.Dependency dependency : model.getDependencies()) {
            String version = dependency.getVersion();
            if (isManagedByParent(dependency)) {
                version = getDependencyParentVersion(mavenService, dependency);
            }
            MavenMetadataBuilder builder = new MavenMetadataBuilder()
                    .groupId(dependency.getGroupId())
                    .artifactId(dependency.getArtifactId())
                    .version(version);

            if (StringUtils.isNotBlank(dependency.getClassifier())) {
                builder.buildInfo(new MavenBuildInfoBuilder()
                        .classifier(dependency.getClassifier())
                        .build());
            }

            MavenMetadata metadata = builder.build();

            String scope = dependency.getScope();
            Usage usage = (StringUtils.isNotBlank(scope)) ? Usage.create(scope) : Usage.create();

            dependencies.add(DefaultDependency.create(metadata, usage));
        }

        return dependencies;
    }

    private boolean isManagedByParent(org.apache.maven.model.Dependency dependency) {
        return dependency.getVersion() == null;
    }

    private String getDependencyParentVersion(MavenService mavenService, org.apache.maven.model.Dependency dependency) {
        Parent parent = model.getParent();
        MavenMetadata parentMetadata = new MavenMetadataBuilder()
                .artifactId(parent.getArtifactId())
                .groupId(parent.getGroupId())
                .version(parent.getVersion())
                .buildInfo(new MavenBuildInfoBuilder()
                        .extension("pom")
                        .build())
                .build();
        DormResource parentResource = mavenService.getArtifact(parentMetadata);
        return resolveVersion(mavenService, dependency.getArtifactId(), parentMetadata, parentResource);
    }

    private String resolveVersion(MavenService mavenService, String artifactId, MavenMetadata parentMetadata, DormResource dormResource) {
        File parentPom;
        if (dormResource != null) {
            parentPom = dormResource.getFile();
        } else {
            parentPom = mavenService.getArtifactByProxy(parentMetadata).getFile();
        }
        MavenPomReader parentReader = new MavenPomReader(parentPom);
        return parentReader.getDependencyManagedVersionByArtifactId(artifactId);
    }

    private String getDependencyManagedVersionByArtifactId(String artifactId) {
        for (org.apache.maven.model.Dependency dependency : model.getDependencyManagement().getDependencies()) {
            if (dependency.getArtifactId().equals(artifactId)) {
                return dependency.getVersion();
            }
        }
        return null;
    }
}
