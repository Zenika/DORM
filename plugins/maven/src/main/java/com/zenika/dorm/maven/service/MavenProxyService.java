package com.zenika.dorm.maven.service;

import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public interface MavenProxyService {

    DormResource getArtifact(DormMetadata metadata);

}
