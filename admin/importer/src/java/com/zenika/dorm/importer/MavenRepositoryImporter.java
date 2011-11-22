package com.zenika.dorm.importer;


import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.comparator.DefaultFileComparator;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenRepositoryImporter extends RepositoryImporter{

    private static final Logger LOG = LoggerFactory.getLogger(MavenRepositoryImporter.class);

    private MavenXpp3Reader reader;

    @Inject
    private MavenProcessor processor;

    public MavenRepositoryImporter() {
        this.reader = new MavenXpp3Reader();
    }

    public void importProcess() {
        sendPoms(new File(configuration.getBasePath()));
    }

    private void sendPoms(File root) {
        for (File file : root.listFiles()) {
            if (FilenameUtils.getExtension(file.getName()).equals("pom")) {
                Model model = getPomModel(file);
                File[] files = getArtifactFile(file.getParentFile());
                if (model != null) {
                    sendFiles(files, model);
                    numberOfImport++;
                }
            } else if (file.isDirectory()) {
                sendPoms(file);
            }
        }
    }

    private Model getPomModel(File pom) {
        try {
            InputStream stream = new FileInputStream(pom);
            Model model = reader.read(stream);
            stream.close();
            return model;
        } catch (FileNotFoundException e) {
            LOG.error("File not found");
        } catch (XmlPullParserException e) {
            LOG.error("Bad pom xml", e);
        } catch (IOException e) {
            LOG.error("IO Exception", e);
        }
        return null;
    }

    private File[] getArtifactFile(File base) {
        File[] files = base.listFiles(new ExtensionFilter());
        return files;
    }

    private void sendFiles(File[] filesTab, Model model) {
        List<File> files = Arrays.asList(filesTab);
        Collections.sort(files, DefaultFileComparator.DEFAULT_COMPARATOR);
        MavenHelper mavenHelper = new MavenHelper();
        for (File file : files) {
            DormWebServiceRequest request = new DormWebServiceRequest.Builder()
                    .file(file)
                    .repositoryName(configuration.getRepositoryName())
                    .origin(MavenMetadata.EXTENSION_NAME)
                    .property("uri", mavenHelper.getArtifactPath(model, file.getName()))
                    .build();
            processor.push(request);
        }
    }

    private String getArtifactPath(Model model, String fileName) {
        StringBuilder sb = new StringBuilder();
        if (!(model.getGroupId() == null || model.getGroupId().isEmpty())) {
            sb.append(model.getGroupId().replace('.', '/'));
            sb.append('/');
        } else {
            if (!(model.getParent().getGroupId() == null || model.getParent().getGroupId().isEmpty())) {
                sb.append(model.getParent().getGroupId().replace('.', '/'));
                sb.append('/');
            }
        }
        if (!(model.getArtifactId() == null || model.getArtifactId().isEmpty())) {
            sb.append(model.getArtifactId());
            sb.append('/');
        }
        if (!(model.getVersion() == null || model.getVersion().isEmpty())) {
            sb.append(model.getVersion());
            sb.append('/');
        } else {
            if (!(model.getParent().getVersion() == null || model.getParent().getVersion().isEmpty())) {
                sb.append(model.getParent().getVersion());
                sb.append('/');
            }
        }
        sb.append(fileName);
        return sb.toString();
    }

}
