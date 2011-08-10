package com.zenika.dorm.core.test.repository;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.repository.impl.DefaultDormRepository;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormRepositoryUnitTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDormRepositoryUnitTest.class);

    private static final String REPO_TEST = "tmp/test/repo";

    private DefaultDormRepository repository;

    @Override
    public void before() {
        super.before();

        new File(REPO_TEST).delete();
        repository = new DefaultDormRepository(REPO_TEST);
    }

    @Test
    public void putValidDependency() {
        Dependency dependency = fixtures.getDependencyWithFile();
        repository.put(dependency);
    }

    @Test
    public void getValidDependency() {
        DormMetadata metadata = fixtures.getMetadata();
        DormFile file = repository.get(metadata);
        LOG.trace("Test file from repository : " + file);
    }
}
