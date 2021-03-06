package com.zenika.dorm.maven.test.service;

import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.service.MavenHashService;
import com.zenika.dorm.maven.test.fixtures.MavenWebServiceRequestFixtures;
import com.zenika.dorm.maven.test.unit.MavenUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
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
    public void compareMd5Hash() throws Exception {

        File md5File = webServiceRequestFixtures.getJarMd5();
        File jarFile = webServiceRequestFixtures.getJar();
        DormResource resource = DefaultDormResource.create(jarFile);

        MavenMetadata hashMetadata = fixtures.getSimpleJarMd5();
        MavenMetadata metadata = fixtures.getSimpleJar();

        when(service.getResource(metadata, null)).thenReturn(resource);

        assertThat(hashService.compareHash(hashMetadata, md5File)).isTrue();
    }

    @Test
    public void compareSha1Hash() throws Exception {

        File sha1File = webServiceRequestFixtures.getJarSha1();
        File jarFile = webServiceRequestFixtures.getJar();
        DormResource resource = DefaultDormResource.create(jarFile);

        MavenMetadata hashMetadata = fixtures.getSimpleJarSha1();
        MavenMetadata metadata = fixtures.getSimpleJar();

        when(service.getResource(metadata, null)).thenReturn(resource);

        assertThat(hashService.compareHash(hashMetadata, sha1File)).isTrue();
    }
}
