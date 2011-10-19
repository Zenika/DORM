package com.zenika.dorm.maven.model;

import com.zenika.dorm.core.model.PluginMetadata;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenPlugin implements PluginMetadata{

    public static final String MAVEN_PLUGIN = "maven_plugin";

    private String artifactId;
    private String groupId;
    private String version;

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

    public boolean hasPomObject(){
        return pomObject != null;
    }

    public PomObject getPomObject() {
        return pomObject;
    }

    public void setPomObject(PomObject pomObject) {
        this.pomObject = pomObject;
    }
}
