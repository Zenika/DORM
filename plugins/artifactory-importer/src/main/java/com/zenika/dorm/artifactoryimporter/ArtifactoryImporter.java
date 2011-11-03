package com.zenika.dorm.artifactoryimporter;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.builder.DormWebServiceAbstractBuilder;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ArtifactoryImporter {

    public static final String ARTIFACTORY_ENTRY_POINT = "http://192.168.182.132:8080/artifactory/";
    public static final String ARTIFACTORY_REPOSITORY = "ext-release-local/";
    public static final String ARTIFACTORY_API_URI = "api/storage/";

    public static final String FOLDER_INFO_MIME_TYPE = "application/vnd.org.jfrog.artifactory.storage.FolderInfo+json";

    private static final Logger LOG = LoggerFactory.getLogger(ArtifactoryImporter.class);

    @Inject
    private ArtifactoryWebResource wrapper;
    @Inject
    private MavenProcessor processor;

    private WebResource resource;

    public void executeImport() {
        resource = wrapper.getResource();
        ArtifactoryFolder folder = getRootFolder();
        List<ArtifactoryFolder> folders = new ArtifactoryFileResolver(resource).resolve(folder);
        pushAllFile(folders);
    }

    private ArtifactoryFolder getRootFolder() {
        return resource.path(ARTIFACTORY_API_URI)
                .path(ARTIFACTORY_REPOSITORY)
                .accept(MediaType.APPLICATION_JSON_TYPE.getType(), FOLDER_INFO_MIME_TYPE)
                .get(ArtifactoryFolder.class);
    }

    private void pushAllFile(List<ArtifactoryFolder> folders) {
        for (ArtifactoryFolder folder : folders) {
            for (ArtifactoryFile file : folder.getChildren()) {
                if (!isArtifactMetadata(file)) {
                    DormWebServiceRequest dormWebServiceRequest = new DormWebServiceRequest.Builder()
                            .origin(MavenMetadata.EXTENSION_NAME)
                            .property("uri", new StringBuilder()
                                    .append(folder.getPath())
                                    .append(file.getUri())
                                    .toString())
                            .file(getFile(folder.getPath(), file))
                            .build();
                    processor.push(dormWebServiceRequest);
                }
            }
        }
    }

    private File getFile(String basePath, ArtifactoryFile artifactoryFile) {
        System.out.println(basePath + artifactoryFile.getUri());
        return resource.path(ARTIFACTORY_REPOSITORY)
                .path(basePath)
                .path(artifactoryFile.getUri())
                .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .get(File.class);
    }

    public boolean isArtifactMetadata(ArtifactoryFile file) {
        return file.getUri().equals("/_maven.repositories");
    }
}
