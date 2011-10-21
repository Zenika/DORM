package com.zenika.dorm.maven.test.service;

import com.google.inject.Guice;
import com.zenika.dorm.core.model.DerivedObject;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.maven.model.MavenBuildInfo;
import com.zenika.dorm.maven.model.MavenPluginMetadata;
import com.zenika.dorm.maven.model.MavenUri;
import com.zenika.dorm.maven.service.MavenProxyService;
import com.zenika.dorm.maven.service.MavenProxyServiceHttp;
import com.zenika.dorm.maven.test.module.MavenTestModule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenFederationServiceTest {

    @Test
    public void test() {

        MavenProxyService service = Guice.createInjector(new MavenTestModule()).getInstance(MavenProxyServiceHttp.class);

        MavenBuildInfo info = new MavenBuildInfo();
        info.setExtension("jar");

        MavenPluginMetadata mavenPluginMetadata = new MavenPluginMetadata();
        mavenPluginMetadata.setGroupId("commons-io");
        mavenPluginMetadata.setArtifactId("commons-io");
        mavenPluginMetadata.setVersion("1.4");
        mavenPluginMetadata.setBuildInfo(info);

        MavenUri mavenUri = new MavenUri(mavenPluginMetadata);

        Object resource = service.getArtifact(mavenUri);

        assertThat(resource).isInstanceOf(InputStream.class);
    }
}
