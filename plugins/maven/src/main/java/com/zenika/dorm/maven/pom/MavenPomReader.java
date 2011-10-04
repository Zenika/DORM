package com.zenika.dorm.maven.pom;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.impl.DefaultDependency;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenBuildInfoBuilder;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenPomReader {

    private Model model;

    public MavenPomReader(File pom) {
        try {
            model = new MavenXpp3Reader().read(new FileInputStream(pom));
        } catch (Exception e) {
            throw new MavenException("Maven metadata file cannot be read", e);
        }
    }

    public MavenMetadata getArtifact() {

        MavenBuildInfo buildInfo = new MavenBuildInfoBuilder()
                .extension("pom").build();

        String groupid = null;
        
        if (StringUtils.isNotBlank(model.getGroupId())) {
            groupid = model.getGroupId();
        } else if (null != model.getParent()) {
            groupid = model.getGroupId();
        }

        if (StringUtils.isBlank(groupid)) {
            throw new MavenException("Unable to determine the groupid from maven artifact : " + model);
        }

        return new MavenMetadataBuilder()
                .artifactId(model.getArtifactId())
                .groupId(groupid)
                .version(model.getVersion())
                .buildInfo(buildInfo)
                .build();
    }

    public List<Dependency> getDependencies() {

        List<Dependency> dependencies = new ArrayList<Dependency>();

        for (org.apache.maven.model.Dependency dependency : model.getDependencies()) {

            MavenMetadataBuilder builder = new MavenMetadataBuilder()
                    .groupId(dependency.getGroupId())
                    .artifactId(dependency.getArtifactId())
                    .version(dependency.getVersion());

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
}
