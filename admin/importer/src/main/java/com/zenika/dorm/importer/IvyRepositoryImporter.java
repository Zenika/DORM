package com.zenika.dorm.importer;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ivy.core.module.descriptor.Artifact;
import org.apache.ivy.core.module.descriptor.ModuleDescriptor;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.parser.m2.PomModuleDescriptorWriter;
import org.apache.ivy.plugins.parser.m2.PomWriterOptions;
import org.apache.ivy.plugins.parser.xml.XmlModuleDescriptorParser;
import org.apache.ivy.util.ChecksumHelper;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class IvyRepositoryImporter extends RepositoryImporter {


    private static final String TMP_DIRECTORY = "tmp/import";

    private IvySettings ivySettings;

    @Inject
    private MavenProcessor processor;

    public IvyRepositoryImporter() {
        new File(TMP_DIRECTORY).mkdir();
        ivySettings = new IvySettings();
    }

    @Override
    protected void importProcess() {
        findIvyFiles(new File(configuration.getBasePath()));
        cleanTmpDirectory();
    }

    private void cleanTmpDirectory() {
        FileUtils.deleteQuietly(new File(TMP_DIRECTORY));
    }

    private void findIvyFiles(File root) {
        for (File file : root.listFiles()) {
            if (isIvyFile(file.getName())) {
                ModuleDescriptor moduleDescriptor = getModuleDescriptor(file);
                sendModuleFiles(moduleDescriptor, file.getParentFile().getParentFile());
            } else if (file.isDirectory()) {
                findIvyFiles(file);
            }
        }
    }

    private void sendModuleFiles(ModuleDescriptor moduleDescriptor, File parentFile) {
        File jarDirectory = new File(parentFile, "jars");
        Artifact[] artifacts = moduleDescriptor.getAllArtifacts();
        for (Artifact artifact : artifacts) {
            String name = artifact.getName();
            String revision = moduleDescriptor.getRevision();
            File jarFile = getFile(name, revision, jarDirectory, "jar");
            File jarMd5File = getFile(name, revision, jarDirectory, "md5");
            File jarSha1 = getFile(name, revision, jarDirectory, "sha1");
            File pomFile = new File(TMP_DIRECTORY, getFileName(name, revision, "xml"));
            fillPomFile(moduleDescriptor, pomFile);
            File pomMd5File = generateChecksum(pomFile, "md5");
            File pomSha1File = generateChecksum(pomFile, "sha1");
            Model model = getModel(pomFile);
            sendFileIsExist(jarFile, model);
            sendFileIsExist(jarMd5File, model);
            sendFileIsExist(jarSha1, model);
            sendFileIsExist(pomFile, model);
            sendFileIsExist(pomMd5File, model);
            sendFileIsExist(pomSha1File, model);
            numberOfImport++;
        }
    }

    private Model getModel(File pomFile) {
        Model model;
        MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
        try {
            model = mavenXpp3Reader.read(new FileInputStream(pomFile));
        } catch (IOException e) {
            throw new CoreException("Unable to read the pom");
        } catch (XmlPullParserException e) {
            throw new CoreException("Unable to read the pom");
        }
        return model;
    }

    private File generateChecksum(File file, String type) {
        FileWriter fileWriter = null;
        File checksumFile = new File(file.getParentFile(), file.getName() + "." + type);
        try {
            fileWriter = new FileWriter(checksumFile);
            String checksum = ChecksumHelper.computeAsString(file, type);
            fileWriter.write(checksum);
            fileWriter.flush();
        } catch (IOException e) {
            throw new CoreException(e);
        } finally {
            IOUtils.closeQuietly(fileWriter);
        }
        return checksumFile;
    }

    private void fillPomFile(ModuleDescriptor moduleDescriptor, File pomFile) {
        try {
            PomModuleDescriptorWriter.write(moduleDescriptor, pomFile, new PomWriterOptions());
        } catch (IOException e) {
            throw new CoreException("Unable to fill the pom file");
        }
    }

    private File getFile(String name, String revision, File jarDirectory, String extension) {
        return new File(jarDirectory, getFileName(name, revision, extension));
    }

    private String getFileName(String name, String revision, String extension) {
        return new StringBuilder(name)
                .append("-")
                .append(revision)
                .append(".")
                .append(extension)
                .toString();
    }

    private ModuleDescriptor getModuleDescriptor(File file) {
        XmlModuleDescriptorParser descriptorParser = XmlModuleDescriptorParser.getInstance();
        ModuleDescriptor moduleDescriptor;
        try {
            moduleDescriptor = descriptorParser.parseDescriptor(ivySettings, file.toURI().toURL(), true);
        } catch (ParseException e) {
            throw new CoreException("Unable to parse the ivy file: " + file, e);
        } catch (IOException e) {
            throw new CoreException("Unable to parse the ivy file: " + file, e);
        }
        return moduleDescriptor;
    }

    private boolean isIvyFile(String name) {
        return Pattern.compile("ivy-[0-9.]+.xml").matcher(name).matches();
    }

    public void sendFileIsExist(File file, Model model) {
        if (file.exists()) {
            MavenHelper mavenHelper = new MavenHelper();
            DormWebServiceRequest request = new DormWebServiceRequest.Builder()
                    .file(file)
                    .repositoryName(configuration.getRepositoryName())
                    .origin(MavenMetadata.EXTENSION_NAME)
                    .property("uri", mavenHelper.getArtifactPath(model, file.getName()))
                    .build();
            processor.push(request);
        }
    }
}