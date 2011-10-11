package com.zenika.dorm.maven.service;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import org.apache.maven.repository.internal.DefaultArtifactDescriptorReader;
import org.apache.maven.repository.internal.DefaultVersionRangeResolver;
import org.apache.maven.repository.internal.DefaultVersionResolver;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.apache.maven.wagon.providers.http.HttpWagon;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.connector.wagon.PlexusWagonProvider;
import org.sonatype.aether.connector.wagon.WagonProvider;
import org.sonatype.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.sonatype.aether.impl.ArtifactDescriptorReader;
import org.sonatype.aether.impl.RemoteRepositoryManager;
import org.sonatype.aether.impl.VersionRangeResolver;
import org.sonatype.aether.impl.VersionResolver;
import org.sonatype.aether.impl.internal.DefaultRemoteRepositoryManager;
import org.sonatype.aether.impl.internal.DefaultServiceLocator;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactDescriptorException;
import org.sonatype.aether.resolution.ArtifactDescriptorRequest;
import org.sonatype.aether.resolution.ArtifactDescriptorResult;
import org.sonatype.aether.spi.connector.RepositoryConnectorFactory;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenProxyServiceAether implements MavenProxyService {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProxyServiceAether.class);

    @Override
    public DormResource getArtifact(DormMetadata metadata) {
        RepositorySystem system = createRepositorySystem();
        RepositorySystemSession session = createSession(system);
        DefaultArtifact artifact = new DefaultArtifact("commons-io", "commons-io", "pom", "1.4");

        ArtifactDescriptorRequest request = new ArtifactDescriptorRequest();
        request.setArtifact(artifact);

        List<RemoteRepository> repositoryList = new ArrayList<RemoteRepository>();
        repositoryList.add(new RemoteRepository("cental", "defult", "http://repo1.maven.org/maven2/"));
        request.setRepositories(repositoryList);

        try {
            ArtifactDescriptorResult result = system.readArtifactDescriptor(session, request);
            LOG.info("Artifact: " + result.getArtifact().getFile());
        } catch (ArtifactDescriptorException e) {
            throw new CoreException(e);
        }
        return null;
    }

    private RepositorySystemSession createSession(RepositorySystem system) {
        MavenRepositorySystemSession session = new MavenRepositorySystemSession();
        LocalRepository localRepository = new LocalRepository("tmp/repo");
        session.setLocalRepositoryManager(system.newLocalRepositoryManager(localRepository));
        return session;
    }

    private RepositorySystem createRepositorySystem() {
        DefaultServiceLocator locator = new DefaultServiceLocator();
        PlexusWagonProvider wagonProvider = new PlexusWagonProvider();

        HttpWagon httpWagon = new HttpWagon();

        try {
            DefaultPlexusContainer plexusContainer = new DefaultPlexusContainer();
        } catch (PlexusContainerException e) {
            throw new CoreException(e);
        }

        locator.setServices(WagonProvider.class, wagonProvider);
        locator.addService(RepositoryConnectorFactory.class, WagonRepositoryConnectorFactory.class);
        locator.addService(RemoteRepositoryManager.class, DefaultRemoteRepositoryManager.class);
        locator.addService(VersionResolver.class, DefaultVersionResolver.class);
        locator.addService(VersionRangeResolver.class, DefaultVersionRangeResolver.class);
        locator.addService(ArtifactDescriptorReader.class, DefaultArtifactDescriptorReader.class);
        return locator.getService(RepositorySystem.class);
    }

}
