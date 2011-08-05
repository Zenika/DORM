package com.zenika.dorm.core.test.repository;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.repository.DormRepositoryManager;
import com.zenika.dorm.core.repository.impl.IvyRepositoryManager;
import com.zenika.dorm.core.test.AbstractDormTest;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class IvyRepositoryIntegrationTest extends AbstractDormTest {

    private static final Logger LOG = LoggerFactory.getLogger(IvyRepositoryIntegrationTest.class);

    private DormRepositoryManager repositoryManager = new IvyRepositoryManager();

    @Test
    public void putDependencyToIvyRepositoryManager() {

        File file = new File(IvyRepositoryManager.BASEDIR, fixtures.getMetadata().getFullQualifier() + "/" +
                fixtures.getMetadataExtension().getQualifier());

        LOG.trace("Test file should be pushed at the location : " + file.getPath());

        if (file.exists()) {
            LOG.trace("Test file already exists, remove it!");
            file.delete();
        }

        Dependency dependency = fixtures.getDependencyWithFile();
        repositoryManager.put(dependency);

        Assertions.assertThat(file.exists()).isTrue();
    }

    @Test
    public void getDependencyFromIvyRepositoryManager() {

        // need to store before get
        putDependencyToIvyRepositoryManager();

        DormFile file = repositoryManager.get(fixtures.getMetadata());
        LOG.trace("Test file from repository is at location : " + file.getFile().getPath());

        Assertions.assertThat(file.getFile().getPath()).isEqualTo(IvyRepositoryManager.BASEDIR.getPath() +
                "/" + fixtures.getMetadata().getFullQualifier() + "/" + fixtures.getQualifier());
    }
}
