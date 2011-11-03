package com.zenika.dorm.artifactoryimporter;

import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ArtifactoryFileResolver {

    private WebResource resource;
    private List<ArtifactoryFolder> folders;

    public ArtifactoryFileResolver(WebResource resource){
        this.resource = resource;
        folders = new ArrayList<ArtifactoryFolder>();
    }

    public List<ArtifactoryFolder> resolve(ArtifactoryFolder root){
        getFile(root);
        return folders;
    }

    private void getFile(ArtifactoryFolder folder) {
        for (ArtifactoryFile file : folder.getChildren()) {
            if (file.isFolder()) {
                getFile(getAsFolder(folder.getPath(), file));
            } else {
                folders.add(folder);
            }
        }
    }

    private ArtifactoryFolder getAsFolder(String basePath, ArtifactoryFile file) {
        return resource.path(ArtifactoryImporter.ARTIFACTORY_API_URI)
                .path(ArtifactoryImporter.ARTIFACTORY_REPOSITORY)
                .path(basePath)
                .path(file.getUri())
                .accept(MediaType.APPLICATION_JSON_TYPE.getType(), ArtifactoryImporter.FOLDER_INFO_MIME_TYPE)
                .get(ArtifactoryFolder.class);
    }
}
