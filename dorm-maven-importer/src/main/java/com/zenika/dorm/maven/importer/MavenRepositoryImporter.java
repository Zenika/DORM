package com.zenika.dorm.maven.importer;


import com.zenika.dorm.maven.importer.utils.PomFinder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenRepositoryImporter {

    private static final Logger LOG = LoggerFactory.getLogger(MavenRepositoryImporter.class);

    private String baseUrl;

    public MavenRepositoryImporter() {

    }

    public MavenRepositoryImporter(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void start() {
        try {
            Path path = Paths.get(baseUrl);
            PomFinder finder = new PomFinder();
            Files.walkFileTree(path, finder);
            File file = finder.getFiles().get(0).toFile();
            MavenXpp3Reader reader = new MavenXpp3Reader();
            LOG.trace("File to test : " + file.getAbsolutePath());
            FileInputStream stream = new FileInputStream(file.getAbsolutePath());
            Model model = reader.read(stream);
            LOG.trace("ArtifactId : " + model.getArtifactId());
            LOG.trace("GroupID : " + model.getGroupId());
            LOG.trace("Version : " + model.getVersion());
        } catch (IOException e) {
            LOG.error("IO Error", e);
        } catch (XmlPullParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
