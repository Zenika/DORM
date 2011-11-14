package com.zenika.dorm.importer;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ImportConfiguration {

    private String basePath;
    private String repositoryName;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }
}
