package com.zenika.dorm.importer.test;

import com.zenika.dorm.core.exception.CoreException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.License;
import org.apache.maven.model.Model;
import org.apache.maven.model.Organization;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenRepositoryGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(MavenRepositoryGenerator.class);
    private static final String SAMPLE_JAR_PATH = "samples/commons-io/commons-io-2.0.1.jar";
    private static final Random RANDOM = new Random();
    private static final String SHA1 = "SHA1";
    private static final String MD5 = "MD5";
    private static final int NUMBER_OF_GENERATED_ARTIFACT = 1;

    private static enum ScopeType {
        TEST, PROVIDED, RUNTIME, COMPILE
    }

    private String repositoryPath;
    private List<MavenRepositoryGeneratorListener> generatorListeners;
    private int numberOfGeneratedArtifact;

    public MavenRepositoryGenerator(String repositoryPath) {
        this(repositoryPath, NUMBER_OF_GENERATED_ARTIFACT);
    }

    public MavenRepositoryGenerator(String repositoryPath, int numberOfGeneratedArtifact) {
        this.repositoryPath = repositoryPath;
        this.generatorListeners = new ArrayList<MavenRepositoryGeneratorListener>();
        this.numberOfGeneratedArtifact = numberOfGeneratedArtifact;
    }

    public void generate() {
        File repository = null;
        try {
            URL urlSample = ClassLoader.getSystemResource(SAMPLE_JAR_PATH);
            repository = new File(repositoryPath);
            File sample = new File(urlSample.toURI());
            generateRepository(repository, sample);
        } catch (URISyntaxException e) {
            throw new CoreException("Unable to build Uri with this path: " + repository, e);
        }
    }

    public void deleteRepository() {
        try {
            FileUtils.deleteDirectory(new File(repositoryPath));
        } catch (IOException e) {
            throw new CoreException("Unable to delete this repository: " + repositoryPath, e);
        }
    }

    public void addGeneratorListener(MavenRepositoryGeneratorListener generatorListener) {
        this.generatorListeners.add(generatorListener);
    }

    public void removeGeneratorListener(MavenRepositoryGeneratorListener generatorListener) {
        this.generatorListeners.remove(generatorListener);
    }

    private void generateRepository(File repository, File sample) {
        for (int i = 0; i < numberOfGeneratedArtifact; i++) {
            Model model = generateModel(i);
            File artifactFolder = createDirectories(repository, model);
            MavenRepositoryArtifact mavenRepositoryArtifact = new MavenRepositoryArtifact();
            mavenRepositoryArtifact.setJarFile(new File(artifactFolder, generateFileName("jar", model)));
            mavenRepositoryArtifact.setJarMd5File(new File(artifactFolder, generateFileName("jar.md5", model)));
            mavenRepositoryArtifact.setJarSha1File(new File(artifactFolder, generateFileName("jar.sha1", model)));
            mavenRepositoryArtifact.setPomFile(new File(artifactFolder, generateFileName("pom", model)));
            mavenRepositoryArtifact.setPomMd5File(new File(artifactFolder, generateFileName("pom.md5", model)));
            mavenRepositoryArtifact.setPomSha1File(new File(artifactFolder, generateFileName("pom.sha1", model)));
            generateArtifactFiles(sample, mavenRepositoryArtifact, model);
            callGeneratorListener(mavenRepositoryArtifact);
        }
    }

    private String generateFileName(String extension, Model model){
        return new StringBuilder(100)
                .append(model.getArtifactId())
                .append("-")
                .append(model.getVersion())
                .append(".")
                .append(extension)
                .toString();
    }

    private File createDirectories(File repository, Model model) {
        String path = new StringBuilder(256)
                .append(model.getGroupId().replace(".", "/"))
                .append("/")
                .append(model.getArtifactId())
                .append("/")
                .append(model.getVersion())
                .toString();
        File artifactFolder = new File(repository, path);
        artifactFolder.mkdirs();
        return artifactFolder;
    }

    private void generateArtifactFiles(File sample, MavenRepositoryArtifact mavenRepositoryArtifact, Model model) {
        FileOutputStream outputStream = null;
        try {
            MavenXpp3Writer writer = new MavenXpp3Writer();

            File jarFile = mavenRepositoryArtifact.getJarFile();
            FileUtils.copyFile(sample, jarFile);

            File jarFileMd5 = mavenRepositoryArtifact.getJarMd5File();
            generateChecksum(jarFile, jarFileMd5, MD5);

            File jarFileSha1 = mavenRepositoryArtifact.getJarSha1File();
            generateChecksum(jarFile, jarFileSha1, SHA1);

            File pomFile = mavenRepositoryArtifact.getPomFile();
            outputStream = new FileOutputStream(pomFile);
            writer.write(outputStream, model);
            outputStream.close();

            File pomFileMd5 = mavenRepositoryArtifact.getPomMd5File();
            generateChecksum(pomFile, pomFileMd5, MD5);

            File pomFileSha1 = mavenRepositoryArtifact.getPomSha1File();
            generateChecksum(pomFile, pomFileSha1, SHA1);

            LOG.info("Files generate with this artifact Id: " + model.getArtifactId());
        } catch (IOException e) {
            throw new CoreException("Unable to generate file", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    private void callGeneratorListener(MavenRepositoryArtifact mavenRepositoryArtifact) {
        for (MavenRepositoryGeneratorListener generatorListener : generatorListeners) {
            generatorListener.artifactGenerated(mavenRepositoryArtifact);
        }
    }

    private void generateChecksum(File file, File fileChecksum, String type) {
        FileOutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        FileInputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(type);

            outputStream = new FileOutputStream(fileChecksum);
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            inputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] dataBytes = new byte[1024];
            int nread = 0;

            while ((nread = bufferedInputStream.read(dataBytes)) != -1) {
                digest.update(dataBytes, 0, nread);
            }

            byte[] byteDigested = digest.digest();

            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < byteDigested.length; i++) {
                sb.append(Integer.toString((byteDigested[i] & 0xff) + 0x100, 16).substring(1));
            }

            bufferedOutputStream.write(sb.toString().getBytes());
            bufferedOutputStream.flush();
        } catch (NoSuchAlgorithmException e) {
            throw new CoreException("Unable to digest this file: " + file, e);
        } catch (FileNotFoundException e) {
            throw new CoreException("Unable to digest this file: " + file, e);
        } catch (IOException e) {
            throw new CoreException("Unable to digest this file: " + file, e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(bufferedInputStream);
            IOUtils.closeQuietly(bufferedOutputStream);
        }
    }

    private Model generateModel(int i) {
        Model model = new Model();
        Organization organization = new Organization();
        License license = new License();


        organization.setName("Zenika");
        organization.setUrl("http://zenika.com");

        license.setName("Test");
        license.setUrl("http://licence.com");
        license.setDistribution("repo");

        model.setModelVersion("4.0.0");
        model.setGroupId("com.test");
        model.setArtifactId("artifact-n-" + i);
        model.setPackaging("jar");
        model.setVersion("1.0." + i);
        model.setName("Artifact test number: " + i);
        model.setDescription("It's a pom generate for integration test about dorm");
        model.setUrl("http://www.zenika.com");
        model.setOrganization(organization);
        model.setDependencies(generateDependencyList());

        return model;
    }

    private List<Dependency> generateDependencyList() {
        int dependenciesNumber = new Random().nextInt(10);
        List<Dependency> dependencies = new ArrayList<Dependency>(dependenciesNumber);
        for (int i = 0; i < dependenciesNumber; i++) {
            int randomId = RANDOM.nextInt(numberOfGeneratedArtifact);
            Dependency dependency = new Dependency();
            dependency.setGroupId("com.test." + randomId);
            dependency.setArtifactId("artifact-n-" + randomId);
            dependency.setVersion("1.0." + randomId);
            dependency.setScope(ScopeType.values()[new Random().nextInt(3)].toString());
            dependencies.add(dependency);
        }
        return dependencies;
    }
}
