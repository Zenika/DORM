package com.zenika.dorm.maven.ws.resource;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.DormWebServiceRequest;
import com.zenika.dorm.core.model.builder.DormRequestBuilder;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.ws.resource.AbstractResource;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Path("maven")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MavenResource extends AbstractResource {

    private static final Logger LOG = LoggerFactory.getLogger(MavenResource.class);

    @Inject
    private Processor processor;

    public MavenResource() {
        LOG.info("Init maven resource as web service");
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("{path:.*}/{filename}")
    public Response get(@PathParam("path") String path,
                        @PathParam("filename") String filename) {

        LOG.info("Call to maven web service : GET");

        return Response.status(Response.Status.NOT_FOUND).build();

//        DormWebServiceRequest request = new DormRequestBuilder(version)
//                .filename(filename)
//                .property(MavenMetadataExtension.METADATA_GROUPID, MavenResourceHelper.formatGroupId(groupId))
//                .property(MavenMetadataExtension.METADATA_ARTIFACTID, artifactId)
//                .property(MavenMetadataExtension.METADATA_VERSION, version)
//                .build();
//
//        if (LOG.isInfoEnabled()) {
//            LOG.info("GET request to the maven web service : " + request);
//        }
//
//        Dependency dependency = processor.get(request);
//
//        if (null == dependency.getResource() || null == dependency.getResource().getFile() || !dependency.getResource().getFile().exists()) {
//            LOG.info("Return http response 404");
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//
//        if (LOG.isInfoEnabled()) {
//            LOG.info("Return response http 200 with the dependency : " + dependency);
//        }
//
//        return Response.status(Response.Status.OK).entity(dependency.getResource().getFile()).build();
    }

//    @PUT
//    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
//    @Path("{groupId:.*}/{artifactId}/{version}/{fileName}")
//    public Response put(File file,
//                        @PathParam("groupId") String groupId,
//                        @PathParam("artifactId") String artifactId,
//                        @PathParam("version") String version,
//                        @PathParam("fileName") String fileName) {
//
//        LOG.info("Call to maven web service : PUT");
//
//        DormWebServiceRequest request = new DormRequestBuilder(MavenMetadataExtension.EXTENSION_NAME)
//                .file(file)
//                .filename(fileName)
//                .property(MavenMetadataExtension.METADATA_GROUPID, MavenResourceHelper.formatGroupId(groupId))
//                .property(MavenMetadataExtension.METADATA_ARTIFACTID, artifactId)
//                .property(MavenMetadataExtension.METADATA_VERSION, version)
//                .build();
//
//        LOG.info("PUT request to the maven web service : " + request);
//
//        if (processor.push(request)) {
//            return Response.status(Response.Status.OK).build();
//        }
//
//        return Response.status(Response.Status.NOT_FOUND).build();
//    }


    @PUT
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Path("{path:.*}/{filename}")
    public Response put(@PathParam("path") String path,
                        @PathParam("filename") String filename,
                        File file) {

        DormWebServiceRequest request = new DormRequestBuilder(MavenMetadataExtension.EXTENSION_NAME)
                .file(file)
                .filename(filename)
                .property("path", path)
                .build();

        processor.push(request);

        return Response.status(Response.Status.OK).build();
    }
}