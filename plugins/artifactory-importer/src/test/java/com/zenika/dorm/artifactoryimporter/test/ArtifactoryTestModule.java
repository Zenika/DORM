package com.zenika.dorm.artifactoryimporter.test;

import com.google.inject.AbstractModule;
import com.zenika.dorm.artifactoryimporter.ArtifactoryWebResource;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.impl.DefaultDormRepository;
import com.zenika.dorm.core.service.DefaultDormService;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.maven.service.MavenProxyService;
import com.zenika.dorm.maven.service.MavenProxyServiceHttp;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ArtifactoryTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ArtifactoryWebResource.class);
        bind(DormDao.class).to(DormDaoNeo4j.class);
        bind(MavenProxyService.class).to(MavenProxyServiceHttp.class);
        bind(DormRepository.class).to(DefaultDormRepository.class);
        bind(DormService.class).to(DefaultDormService.class);
        
    }
}
