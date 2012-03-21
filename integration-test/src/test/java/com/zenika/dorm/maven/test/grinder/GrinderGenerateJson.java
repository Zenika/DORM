package com.zenika.dorm.maven.test.grinder;


import com.zenika.dorm.maven.test.model.MavenConfiguration;
import com.zenika.dorm.maven.test.model.MavenPutResource;
import com.zenika.dorm.maven.test.model.MavenResource;
import com.zenika.dorm.maven.test.model.MavenSample;
import com.zenika.dorm.maven.test.result.MavenPutResult;
import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.License;
import org.apache.maven.model.Model;
import org.apache.maven.model.Organization;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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
public class GrinderGenerateJson {

    private static final String JSON_PATH = "maven_put_generate.json";

    private static final String SAMPLE_JAR_PATH = "files/sample.jar";

    private static final Random RANDOM = new Random();

    private static final String SHA1 = "SHA1";
    private static final String MD5 = "MD5";

    private static final int NUMBER_OF_GENERATED_FILE = 1000;

    private static final Logger LOG = LoggerFactory.getLogger(GrinderGenerateJson.class);

    public static void generateJsonAndRepository(File repository) {
        try {
            URL urlSample = ClassLoader.getSystemResource(SAMPLE_JAR_PATH);
            URL urlJsonFile = ClassLoader.getSystemResource(JSON_PATH);

            LOG.info("Sample URI: " + urlSample.toURI());

            File sample = new File(urlSample.toURI());
            File jsonFile = new File(urlJsonFile.toURI());
            generateJsonAndRepository(repository, sample, jsonFile);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to build Uri with this path: " + repository, e);
        }
    }

    public static void generateJsonAndRepository(File repository, File sample, File jsonFile) {
        FileOutputStream outputStream = null;
        try {
            MavenXpp3Writer writer = new MavenXpp3Writer();

            MavenSample sampleJson = getDefaultSample();

            for (int i = 0; i < NUMBER_OF_GENERATED_FILE; i++) {
                MavenPutResource resource = new MavenPutResource();

                File jarFile = new File(repository, "file-" + i + ".jar");
                FileUtils.copyFile(sample, jarFile);
                resource.setJarPath(jarFile.getAbsolutePath());

                File jarFileMd5 = new File(repository, "file-" + i + ".jar.md5");
                generateChecksum(jarFile, jarFileMd5, MD5);
                resource.setJarPathMd5(jarFileMd5.getAbsolutePath());

                File jarFileSha1 = new File(repository, "file-" + i + ".jar.sha1");
                generateChecksum(jarFile, jarFileSha1, SHA1);
                resource.setJarPathSha1(jarFileSha1.getAbsolutePath());

                Model model = generateModel(i);
                File pomFile = new File(repository, "file-" + i + ".pom");
                outputStream = new FileOutputStream(pomFile);
                writer.write(outputStream, model);
                outputStream.close();
                resource.setPomPath(pomFile.getAbsolutePath());

                File pomFileMd5 = new File(repository, "file-" + i + ".pom.md5");
                generateChecksum(pomFile, pomFileMd5, MD5);
                resource.setPomPathMd5(pomFileMd5.getAbsolutePath());

                File pomFileSha1 = new File(repository, "file-i" + i + ".pom.sha1");
                generateChecksum(pomFile, pomFileSha1, SHA1);
                resource.setPomPathSha1(pomFileSha1.getAbsolutePath());

                resource = generateResourceUrl(resource, model);
                sampleJson.getResources().add(resource);
                LOG.info("Files generate with this artifact Id: " + model.getArtifactId());
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(jsonFile, sampleJson);
        } catch (IOException e) {
            throw new RuntimeException("Unable to generate file", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to close the Output stream", e);
                }
            }
        }
    }

    private static MavenPutResource generateResourceUrl(MavenPutResource resource, Model model) {
        String baseUrl = new StringBuilder(50)
                .append(model.getGroupId().replace('.', '/'))
                .append('/')
                .append(model.getArtifactId())
                .append('/')
                .append(model.getVersion())
                .append('/')
                .append(model.getArtifactId())
                .append('-')
                .append(model.getVersion())
                .toString();
        resource.setJarUrl(baseUrl + ".jar");
        resource.setJarMd5Url(baseUrl + ".jar.md5");
        resource.setJarSha1Url(baseUrl + ".jar.sha1");
        resource.setPomUrl(baseUrl + ".pom");
        resource.setPomMd5Url(baseUrl + ".pom.md5");
        resource.setPomSha1Url(baseUrl + ".pom.sha1");
        resource.setExpectedResult(new MavenPutResult(200));
        return resource;
    }

    private static MavenSample getDefaultSample() {
        MavenSample sample = new MavenSample();
        MavenConfiguration configuration = new MavenConfiguration();
        configuration.setProxyActive(false);
        configuration.setProxyHostname("");
        configuration.setProxyPort(-1);
        configuration.setTargetBaseUrl("maven");
        configuration.setTargetHostname("localhost");
        configuration.setTargetPort(8080);
        sample.setConfiguration(configuration);
        sample.setResources(new ArrayList<MavenResource>());
        return sample;  //To change body of created methods use File | Settings | File Templates.
    }

    /**
     * @param file         to generate checksum
     * @param fileChecksum to put the generated checksum
     */
    private static void generateChecksum(File file, File fileChecksum, String checksumType) {
        FileOutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        FileInputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(checksumType);

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
            throw new RuntimeException("Unable to digest this file: " + file, e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to digest this file: " + file, e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to digest this file: " + file, e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to digest this file: " + file, e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to digest this file: " + file, e);
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to digest this file: " + file, e);
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to digest this file: " + file, e);
                }
            }
        }
    }

    private static Model generateModel(int i) {
        Model model = new Model();
        Organization organization = new Organization();
        License license = new License();


        organization.setName("Zenika");
        organization.setUrl("http://zenika.com");

        license.setName("Test");
        license.setUrl("http://licence.com");
        license.setDistribution("repo");

        model.setModelVersion("4.0.0");
        model.setGroupId("com.test." + i);
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

    private static List<Dependency> generateDependencyList() {
        int dependenciesNumber = new Random().nextInt(10);
        List<Dependency> dependencies = new ArrayList<Dependency>(dependenciesNumber);
        for (int i = 0; i < dependenciesNumber; i++) {
            int randomId = RANDOM.nextInt(NUMBER_OF_GENERATED_FILE);
            Dependency dependency = new Dependency();
            dependency.setGroupId("com.test." + randomId);
            dependency.setArtifactId("artifact-n-" + randomId);
            dependency.setVersion("1.0." + randomId);
            dependency.setScope(ScopeType.values()[new Random().nextInt(3)].toString());
            dependencies.add(dependency);
        }
        return dependencies;
    }

    private static enum ScopeType {
        TEST, PROVIDED, RUNTIME, COMPILE
    }


}
