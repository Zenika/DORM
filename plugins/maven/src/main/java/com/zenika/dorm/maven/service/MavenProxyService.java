package com.zenika.dorm.maven.service;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.maven.model.MavenUri;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public interface MavenProxyService {

    public Object getArtifact(MavenUri mavenUri);
}
