package com.zenika.dorm.maven.ws.resource;

import com.google.inject.Inject;
import com.sun.jersey.multipart.FormDataParam;
import com.zenika.dorm.core.helper.DormFileHelper;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;
import com.zenika.dorm.maven.service.MavenService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("maven")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MavenResource {

    @Inject
    private MavenService service;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{groupid}/{artifactid}/{version}/{filename}")
    public Response createArtifact(@FormDataParam("file") File file, @PathParam("groupid") String groupId,
                                   @PathParam("artifactid") String artifactId, @PathParam("version") String version,
                                   @PathParam("filename") String filename) {

        DormMavenMetadata metadata = new DormMavenMetadata(groupId, artifactId, version);

        String extension = DormFileHelper.getExtensionFromFilename(filename);

        service.pushArtifact(metadata, file, filename);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{groupid}/{artifactid}/{version}/{filename}")
    public DormArtifact<DormMavenMetadata> getArtifactByMetadata(@PathParam("groupid") String groupId,
                                                                 @PathParam("artifactid") String artifactId, @PathParam("version") String version,
                                                                 @PathParam("filename") String filename) {

        DormMavenMetadata metadata = new DormMavenMetadata(groupId, artifactId, version);



        DormArtifact<DormMavenMetadata> artifact = service.getArtifact(metadata, filename);

        return null;
    }

    @DELETE
    @Path("{groupid}/{artifactid}/{version}/{filename}")
    public Response removeArtifactByMetadata(@PathParam("groupid") String groupId,
                                             @PathParam("artifactid") String artifactId, @PathParam("version") String version,
                                             @PathParam("filename") String filename) {

        DormMavenMetadata metadata = new DormMavenMetadata(groupId, artifactId, version);

        service.removeArtifact(metadata, filename);

        return Response.status(Response.Status.OK).build();
    }

}
