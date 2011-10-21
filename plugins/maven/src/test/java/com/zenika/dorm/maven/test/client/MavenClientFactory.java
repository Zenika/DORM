package com.zenika.dorm.maven.test.client;

import org.apache.maven.repository.internal.DefaultServiceLocator;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.connector.file.FileRepositoryConnectorFactory;
import org.sonatype.aether.connector.wagon.WagonProvider;
import org.sonatype.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.sonatype.aether.spi.connector.RepositoryConnectorFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenClientFactory {

    public static RepositorySystem createRepositorySystem() {

        DefaultServiceLocator locator = new DefaultServiceLocator();
        locator.addService(RepositoryConnectorFactory.class,
                FileRepositoryConnectorFactory.class);
        locator.addService(RepositoryConnectorFactory.class,
                WagonRepositoryConnectorFactory.class);

        locator.setServices(WagonProvider.class, new MavenClientWagonProvider());

        return locator.getService(RepositorySystem.class);
    }


}