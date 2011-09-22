package com.zenika.dorm.maven.test.service;

import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.service.MavenHashService;
import com.zenika.dorm.maven.test.fixtures.MavenWebServiceRequestFixtures;
import com.zenika.dorm.maven.test.unit.MavenUnitTest;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenHashServiceUnitTest extends MavenUnitTest {

    private MavenWebServiceRequestFixtures webServiceRequestFixtures;

    @Mock
    private DormService service;

    @InjectMocks
    private MavenHashService hashService = new MavenHashService();

    @Override
    public void before() {
        super.before();
        webServiceRequestFixtures = new MavenWebServiceRequestFixtures(pathFixtures);
    }

    @Test
    @Ignore
    public void compareMd5Hash() throws Exception {

        File file = webServiceRequestFixtures.getJar();
        DormResource resource = DefaultDormResource.create(file);
        MavenMetadata metadata = fixtures.getSimpleJar();

        when(service.getResource(metadata)).thenReturn(resource);

        assertThat(hashService.compareHash("md5", metadata, file)).isTrue();

        verify(hashService.compareHash("md5", metadata, file));
    }
}
