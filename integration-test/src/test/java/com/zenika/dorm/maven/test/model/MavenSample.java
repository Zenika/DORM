package com.zenika.dorm.maven.test.model;

import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenSample {

    private MavenConfiguration configuration;
    private List<MavenResource> resources;

    public MavenConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(MavenConfiguration configuration) {
        this.configuration = configuration;
    }

    public List<MavenResource> getResources() {
        return resources;
    }

    public void setResources(List<MavenResource> resources) {
        this.resources = resources;
    }
}
