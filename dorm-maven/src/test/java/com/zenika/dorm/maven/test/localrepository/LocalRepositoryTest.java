package com.zenika.dorm.maven.test.localrepository;

import org.apache.maven.repository.internal.DefaultArtifactDescriptorReader;
import org.apache.maven.repository.internal.DefaultVersionRangeResolver;
import org.apache.maven.repository.internal.DefaultVersionResolver;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.codehaus.plexus.PlexusContainerException;
import org.junit.Test;
import org.omg.PortableServer.POAManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.sonatype.aether.impl.ArtifactDescriptorReader;
import org.sonatype.aether.impl.VersionRangeResolver;
import org.sonatype.aether.impl.VersionResolver;
import org.sonatype.aether.impl.internal.DefaultServiceLocator;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.resolution.ArtifactDescriptorException;
import org.sonatype.aether.resolution.ArtifactDescriptorRequest;
import org.sonatype.aether.resolution.ArtifactDescriptorResult;
import org.sonatype.aether.spi.connector.RepositoryConnectorFactory;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class LocalRepositoryTest {

//    private static final Logger LOGGER = LoggerFactory.getLogger(LocalRepositoryTest.class);

    @Test
    public void test() throws ArtifactDescriptorException, PlexusContainerException {
        RepositorySystem repoSystem = newRepositorySystem();
		RepositorySystemSession session = newSession(repoSystem);

        ArtifactDescriptorRequest request = new ArtifactDescriptorRequest();
        File root = session.getLocalRepository().getBasedir();

        List<File> fileList = new ArrayList<File>();

//        for (File file : root.listFiles()){
//            File[] filelistFiles(new FilenameFilter() {
//                public boolean accept(File file, String s) {
//                    return s.equals("pom.xml");  //To change body of implemented methods use File | Settings | File Templates.
//                }
//            })[0]);
//        }


//        for(File file:root.listFiles()){
//            if (file.getName().equals("pom.xml")){
//                fileList.add(file);
//            }
//        }

//        System.out.println("test");
//        for (File file : fileList){
//            System.out.println(file.getAbsolutePath());
//        }

//		// File file = new File("tmp/repo/maven-profile-2.2.1.pom");
//
//		DefaultArtifact artifact = new DefaultArtifact("org.apache.maven",
//				"maven-profile", "pom", "2.2.1");
//
//		ArtifactDescriptorRequest descriptorRequest = new ArtifactDescriptorRequest();
//		descriptorRequest.setArtifact(artifact);
//
//		ArtifactDescriptorResult artifactDescriptorResult = repoSystem
//				.readArtifactDescriptor(session, descriptorRequest);
    }


    private static RepositorySystemSession newSession(RepositorySystem system) {


        MavenRepositorySystemSession session = new MavenRepositorySystemSession();

        LocalRepository localRepo = new LocalRepository("/home/erouan/.m2/repository");
        session.setLocalRepositoryManager(system
                .newLocalRepositoryManager(localRepo));

        return session;
    }

    private static RepositorySystem newRepositorySystem()
            throws PlexusContainerException {
        DefaultServiceLocator locator = new DefaultServiceLocator();

        locator.addService(RepositoryConnectorFactory.class,
                WagonRepositoryConnectorFactory.class);
        locator.addService(VersionResolver.class, DefaultVersionResolver.class);
        locator.addService(VersionRangeResolver.class,
                DefaultVersionRangeResolver.class);
        locator.addService(ArtifactDescriptorReader.class,
                DefaultArtifactDescriptorReader.class);

        return locator.getService(RepositorySystem.class);
    }

//    public static void main(String args[]) throws Exception {
//        MavenPomResolution pomResolution = new MavenPomResolution();
//        pomResolution.foo();
//    }
}
