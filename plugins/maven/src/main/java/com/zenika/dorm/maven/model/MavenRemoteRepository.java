package com.zenika.dorm.maven.model;

/**
 * @author: Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenRemoteRepository {
    
    public static final String DEFAULT_ID = "Maven central";
    public static final String DEFAULT_URL = "http://repo1.maven.org/maven2/";
    
    private String id;
    private String url;

    public MavenRemoteRepository(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public static MavenRemoteRepository getDefaultRemoteRepository() {
        return new MavenRemoteRepository(DEFAULT_ID, DEFAULT_URL);
    }
    
    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
