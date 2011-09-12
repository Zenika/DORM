package com.zenika.dorm.maven.client;

import com.zenika.dorm.maven.importer.util.ConsoleRepositoryListener;
import com.zenika.dorm.maven.importer.util.ConsoleTransferListener;
import org.apache.maven.repository.internal.DefaultServiceLocator;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.connector.file.FileRepositoryConnectorFactory;
import org.sonatype.aether.connector.wagon.WagonProvider;
import org.sonatype.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.sonatype.aether.deployment.DeployRequest;
import org.sonatype.aether.impl.RemoteRepositoryManager;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.spi.connector.RepositoryConnectorFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenClientConfig {

    private RepositorySystemSession session;
    private RepositorySystem system;
    private RemoteRepository remoteRepository;

    private MavenClientConfig(RepositorySystemSession session, RepositorySystem system,
                              RemoteRepository remoteRepository) {
        this.session = session;
        this.system = system;
        this.remoteRepository = remoteRepository;
    }

    public RepositorySystemSession getSession() {
        return session;
    }

    public RepositorySystem getSystem() {
        return system;
    }

    public RemoteRepository getRemoteRepository() {
        return remoteRepository;
    }

    public static class Builder {

        private String path;
        private String remoteName;
        private String remoteUrl;

        public Builder(String path, String remoteName, String remoteUrl) {
            this.path = path;
            this.remoteName = remoteName;
            this.remoteUrl = remoteUrl;
        }

        public MavenClientConfig build() {
            RepositorySystem system = createRepositorySystem();
            RepositorySystemSession session = createRepositorySystemSession(system, path);
            RemoteRepository repository = new RemoteRepository(remoteName, "default", remoteUrl);
            
            return new MavenClientConfig(session, system, repository);
        }

        private RepositorySystem createRepositorySystem() {

            DefaultServiceLocator locator = new DefaultServiceLocator();
            locator.addService(RepositoryConnectorFactory.class,
                    FileRepositoryConnectorFactory.class);
            locator.addService(RepositoryConnectorFactory.class,
                    WagonRepositoryConnectorFactory.class);

            locator.setServices(WagonProvider.class, new MavenClientWagonProvider());

            return locator.getService(RepositorySystem.class);
        }

        private RepositorySystemSession createRepositorySystemSession(RepositorySystem system,
                                                                      String repository) {

            MavenRepositorySystemSession session = new MavenRepositorySystemSession();

            session.setLocalRepositoryManager(system.newLocalRepositoryManager(new LocalRepository(repository)));
            session.setTransferListener(new ConsoleTransferListener());
            session.setRepositoryListener(new ConsoleRepositoryListener());

            return session;
        }
    }
}
