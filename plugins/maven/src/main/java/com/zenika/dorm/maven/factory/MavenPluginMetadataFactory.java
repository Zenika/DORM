package com.zenika.dorm.maven.factory;

import com.zenika.dorm.core.factory.PluginExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.util.DormStringUtils;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenPluginMetadata;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.zenika.dorm.maven.model.MavenPluginMetadata.*;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenPluginMetadataFactory implements PluginExtensionMetadataFactory {

    @Override
    public String getExtensionName() {
        return MAVEN_PLUGIN;
    }

    @Override
    public DormMetadata fromMap(Long id, Map<String, String> properties) {
        return convertToDormMetadata(id, properties);
    }

    @Override
    public Map<String, String> toMap(DormMetadata metadata) {
        return convertToMap(metadata);
    }

    private Map<String, String> convertToMap(DormMetadata metadata) {
        MavenPluginMetadata mavenPluginMetadata = metadata.getPlugin(MavenPluginMetadata.class);

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(METADATA_GROUP_ID, mavenPluginMetadata.getGroupId());
        properties.put(METADATA_ARTIFACT_ID, mavenPluginMetadata.getArtifactId());
        properties.put(METADATA_VERSION_ID, mavenPluginMetadata.getVersion());
        properties.put(METADATA_SNAPSHOT, String.valueOf(mavenPluginMetadata.isSnapshot()));

        if (hasBuildInfo(mavenPluginMetadata)) {
            properties.put(MavenBuildInfo.METADATA_CLASSIFIER, mavenPluginMetadata.getBuildInfo().getClassifier());
            properties.put(MavenBuildInfo.METADATA_EXTENSION, mavenPluginMetadata.getBuildInfo().getExtension());
            properties.put(MavenBuildInfo.METADATA_TIMESTAMP, mavenPluginMetadata.getBuildInfo().getTimestamp());
            properties.put(MavenBuildInfo.METADATA_BUILDNUMBER, mavenPluginMetadata.getBuildInfo().getBuildNumber());
        }

        return properties;
    }

    private boolean hasBuildInfo(MavenPluginMetadata mavenPluginMetadata) {
        return null != mavenPluginMetadata.getBuildInfo();
    }

    private DormMetadata convertToDormMetadata(Long id, Map<String, String> properties) {
        MavenPluginMetadata mavenPluginMetadata = new MavenPluginMetadata();
        mavenPluginMetadata.setArtifactId(properties.get(METADATA_ARTIFACT_ID));
        mavenPluginMetadata.setGroupId(properties.get(METADATA_GROUP_ID));
        mavenPluginMetadata.setVersion(properties.get(METADATA_VERSION_ID));

        MavenBuildInfo buildInfo = new MavenBuildInfo(
                StringUtils.defaultIfBlank(properties.get(MavenBuildInfo.METADATA_EXTENSION), null),
                StringUtils.defaultIfBlank(properties.get(MavenBuildInfo.METADATA_CLASSIFIER), null),
                StringUtils.defaultIfBlank(properties.get(MavenBuildInfo.METADATA_TIMESTAMP), null),
                StringUtils.defaultIfBlank(properties.get(MavenBuildInfo.METADATA_BUILDNUMBER), null)
        );

        mavenPluginMetadata.setBuildInfo(buildInfo);
        return mavenPluginMetadata.toDormMetadata(id);
    }


}
