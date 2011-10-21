package com.zenika.dorm.maven.model;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.PluginMetadata;
import com.zenika.dorm.maven.constant.MavenConstant;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenPluginMetadata implements PluginMetadata{

    public static final String MAVEN_PLUGIN = "maven_plugin";

    private String artifactId;
    private String groupId;
    private String version;

    private MavenBuildInfo buildInfo;

    private PomObject pomObject;

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getType() {
        return MAVEN_PLUGIN;
    }

    @Override
    public DormMetadata toDormMetadata() {
        DormMetadata dormMetadata = new DormMetadata();
        dormMetadata.setName(getArtifactId());
        dormMetadata.setVersion(getVersion());
        dormMetadata.addPluginMetadata(this);
        return dormMetadata;
    }

    public boolean hasPomObject(){
        return pomObject != null;
    }

    public PomObject getPomObject() {
        return pomObject;
    }

    public void setPomObject(PomObject pomObject) {
        this.pomObject = pomObject;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getVersion() {
        return version;
    }

    public MavenBuildInfo getBuildInfo() {
        return buildInfo;
    }

    public void setBuildInfo(MavenBuildInfo buildInfo) {
        this.buildInfo = buildInfo;
    }

    public boolean isSnapshot() {
        return version.endsWith("-" + MavenConstant.Special.SNAPSHOT);
    }
}
