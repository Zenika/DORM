package com.zenika.dorm.maven.pom;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenPluginMetadata;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenPomReader {

    private static final Logger LOG = LoggerFactory.getLogger(MavenPomReader.class);

    private Model model;

    public MavenPomReader(File pom) throws MavenException {

        try {
            model = new MavenXpp3Reader().read(new FileInputStream(pom));
        } catch (Exception e) {
            throw new MavenException("Maven metadata file cannot be read", e);
        }
    }

    public MavenPluginMetadata getArtifact() {

        MavenBuildInfo buildInfo = new MavenBuildInfo();
        buildInfo.setExtension("pom");

        String groupid = null;
        if (StringUtils.isNotBlank(model.getGroupId())) {
            groupid = model.getGroupId();
        } else if (null != model.getParent()) {
            groupid = model.getParent().getGroupId();
        }

        checkArgument(StringUtils.isNotBlank(groupid), "Unable to determine the groupId from the pom");

        String version = null;
        if (StringUtils.isNotBlank(model.getVersion())) {
            version = model.getVersion();
        } else if (null != model.getParent()) {
            version = model.getParent().getVersion();
        }

        checkArgument(StringUtils.isNotBlank(version), "Unable to determine the version from the pom");

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven pom groupId : " + groupid);
            LOG.debug("Maven pom version : " + version);
        }

        MavenPluginMetadata mavenPluginMetadata = new MavenPluginMetadata();
        mavenPluginMetadata.setArtifactId(model.getArtifactId());
        mavenPluginMetadata.setGroupId(groupid);
        mavenPluginMetadata.setVersion(version);
        mavenPluginMetadata.setBuildInfo(buildInfo);
        return mavenPluginMetadata;
    }

    public List<DormMetadata> getDependencies() {

        List<DormMetadata> dependencies = new ArrayList<DormMetadata>();

        for (org.apache.maven.model.Dependency dependency : model.getDependencies()) {
            MavenPluginMetadata mavenPluginMetadata = new MavenPluginMetadata();
            mavenPluginMetadata.setGroupId(dependency.getGroupId());
            mavenPluginMetadata.setArtifactId(dependency.getArtifactId());
            mavenPluginMetadata.setVersion(dependency.getVersion());

            if (StringUtils.isNotBlank(dependency.getClassifier())) {
                MavenBuildInfo buildInfo = new MavenBuildInfo();
                buildInfo.setClassifier(dependency.getClassifier());
                mavenPluginMetadata.setBuildInfo(buildInfo);
            }

            String scope = dependency.getScope();
            Usage usage = (StringUtils.isNotBlank(scope)) ? Usage.create(scope) : Usage.create();

            DormMetadata dormMetadata = mavenPluginMetadata.toDormMetadata();
            dormMetadata.setUsage(usage);

            dependencies.add(dormMetadata);
        }
        return dependencies;
    }
}
