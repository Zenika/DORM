package com.zenika.dorm.maven.ws.resource;

import com.google.inject.Inject;
import com.zenika.dorm.core.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@Path("maven")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MavenResource {

    private static final Logger LOG = LoggerFactory.getLogger(MavenResource.class);

    @Inject
    private Processor processor;

    public MavenResource() {
        LOG.info("Init maven resource as web service");
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response push(MultivaluedMap<String, String> params) {
        LOG.info("Push maven with values : " + params);
        return Response.status(Response.Status.OK).build();
    }

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Path("{groupid}/{artifactid}/{version}/{filename}")
//    public Response createArtifact(@FormDataParam("file") File file, @PathParam("groupid") String groupId,
//                                   @PathParam("artifactid") String artifactId, @PathParam("version") String version,
//                                   @PathParam("filename") String filename) {
//
//        DormMavenMetadata metadata = new DormMavenMetadata(groupId, artifactId, version);
//
//        String extension = DormFileHelper.getExtensionFromFilename(filename);
//
//        service.pushArtifact(metadata, file, filename);
//
//        return Response.status(Response.Status.OK).build();
//    }
//
//    @GET
//    @Path("{groupid}/{artifactid}/{version}/{filename}")
//    public DormArtifact<DormMavenMetadata> getArtifactByMetadata(@PathParam("groupid") String groupId,
//                                                                 @PathParam("artifactid") String artifactId, @PathParam("version") String version,
//                                                                 @PathParam("filename") String filename) {
//
//        DormMavenMetadata metadata = new DormMavenMetadata(groupId, artifactId, version);
//
//
//
//        DormArtifact<DormMavenMetadata> artifact = service.getArtifact(metadata, filename);
//
//        return null;
//    }
//
//    @DELETE
//    @Path("{groupid}/{artifactid}/{version}/{filename}")
//    public Response removeArtifactByMetadata(@PathParam("groupid") String groupId,
//                                             @PathParam("artifactid") String artifactId, @PathParam("version") String version,
//                                             @PathParam("filename") String filename) {
//
//        DormMavenMetadata metadata = new DormMavenMetadata(groupId, artifactId, version);
//
//        service.removeArtifact(metadata, filename);
//
//        return Response.status(Response.Status.OK).build();
//    }

}
