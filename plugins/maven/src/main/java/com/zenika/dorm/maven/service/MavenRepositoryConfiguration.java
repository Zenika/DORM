package com.zenika.dorm.maven.service;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenRepositoryConfiguration {

    private static final String pattern = "[groupId: {separator: .}]/[artifactId]/[version]/[artifactId]-[version][classifier: {prefix: -, optionally}].[extension]";

    public String getPattern(){
        return pattern;
    }
}