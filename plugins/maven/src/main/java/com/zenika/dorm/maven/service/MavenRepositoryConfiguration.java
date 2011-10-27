package com.zenika.dorm.maven.service;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenRepositoryConfiguration {

    private static final String pattern = "[groupId: {separator: .}]/[artifactId: {alias: name}]/[version]/[artifactId: {alias: name}]-[version][classifier: {prefix: -, optionally}].[extension]";

    public String getPattern(){
        return pattern;
    }
}