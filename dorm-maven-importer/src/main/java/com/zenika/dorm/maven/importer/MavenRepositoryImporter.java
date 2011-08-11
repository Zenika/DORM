package com.zenika.dorm.maven.importer;


import com.zenika.dorm.maven.importer.utils.PomFinder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenRepositoryImporter {

    private static final Logger LOG = LoggerFactory.getLogger(MavenRepositoryImporter.class);

    private String baseUrl;
    private String host;
    private String port;

    private URI serverUri;

    private MavenXpp3Reader reader;

    public MavenRepositoryImporter() {

    }

    public MavenRepositoryImporter(String baseUrl, String host, String port) {
        this.baseUrl = baseUrl;
        serverUri = UriBuilder.fromUri("").host(host).port(Integer.parseInt(port)).build();
        LOG.trace("Server URI : " + serverUri);
    }

    public void start() {
        try {
            List<File> poms = getPomFile(new File(baseUrl));
            File file = poms.get(0);
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
            e.printStackTrace();
        }
    }

    public List<File> getPomFile(File root) {
        List<File> poms = new ArrayList<File>();
        for (File file : root.listFiles()) {
            if (FilenameUtils.getExtension(file.getName()).equals("pom")) {
                Model model = getPomModel(file);
                File[] files = getArtifactFile(file.getParentFile());
                if (model != null){
                    sendFiles(files, model);
                }
            } else if (file.isDirectory()) {
                poms.addAll(getPomFile(file));
            }
        }
        return poms;
    }

    public Model getPomModel(File pom) {
        try {
            FileInputStream stream = new FileInputStream(pom);
            Model model = reader.read(stream);
            stream.close();
            return model;
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XmlPullParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public File[] getArtifactFile(File base){
        return base.listFiles();
    }

    public void sendFiles(File[] files, Model model){

    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
