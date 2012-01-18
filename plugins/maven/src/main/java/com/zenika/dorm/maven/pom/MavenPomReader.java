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
    private MavenService mavenService;

    private Model parentModel;

    public MavenPomReader(MavenService mavenService, File pom) {
        this.mavenService = mavenService;
        model = getModelFromFile(pom);
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

    public List<Dependency> getDependencies() {

        List<Dependency> dependencies = new ArrayList<Dependency>();
        MavenVersionResolver mavenVersionResolver = new MavenVersionResolver(this);

        for (org.apache.maven.model.Dependency dependency : model.getDependencies()) {
            String version = mavenVersionResolver.getVersion(dependency);
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

    public Model getParentModel() {
        if (parentModel == null) {
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
            File parentFilePom;
            if (parentResource != null) {
                parentFilePom = parentResource.getFile();
            } else {
                parentFilePom = mavenService.getArtifactByProxy(parentMetadata).getFile();
            }
            parentModel = getModelFromFile(parentFilePom);
        }
        return parentModel;
    }

    public Model getModel() {
        return model;
    }

    private Model getModelFromFile(File pom) {
        try {
            return new MavenXpp3Reader().read(new FileInputStream(pom));
        } catch (Exception e) {
            throw new MavenException("Maven metadata file cannot be read", e);
        }
    }
}
