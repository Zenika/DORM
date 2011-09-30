package com.zenika.dorm.maven.ws.resource;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.processor.DormProcessor;
import com.zenika.dorm.maven.model.MavenMetadata;
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
public class MavenResource {

    private static final Logger LOG = LoggerFactory.getLogger(MavenResource.class);

    @Inject
    private DormProcessor processor;

    public MavenResource() {
        if (LOG.isInfoEnabled()) {
            LOG.info("Call to maven webservice");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("{path:.*}/{filename}")
    public Response get(@PathParam("path") String path,
                        @PathParam("filename") String filename) {

        String uri = path + "/" + filename;

        if (LOG.isInfoEnabled()) {
            LOG.info("Maven webservice GET with uri : " + uri);
        }

        DormWebServiceRequest request = new DormWebServiceRequest.Builder
                (MavenMetadata.EXTENSION_NAME)
                .property("uri", uri)
                .build();

        DormWebServiceResult result = processor.get(request);

        Response response;

        switch (result.getResult()) {
            case FOUND:
                response = Response.ok(result.getFile()).build();
                break;
            case NOTFOUND:
                response = Response.status(Response.Status.NOT_FOUND).build();
                break;
            default:
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return response;
    }

    @PUT
    @Path("{path:.*}/{filename}")
    public Response put(@PathParam("path") String path,
                        @PathParam("filename") String filename,
                        File file) {

        String uri = path + "/" + filename;

        if (LOG.isInfoEnabled()) {
            LOG.info("Maven webservice PUT with uri : " + uri);
        }

        DormWebServiceRequest.Builder requestBuilder = new DormWebServiceRequest.Builder
                (MavenMetadata.EXTENSION_NAME)
                .property("uri", uri);

        if (file.length() > 0) {
            requestBuilder.file(file);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Size of the uploaded file : " + file.length());
        }

        DormWebServiceRequest request = requestBuilder.build();
        processor.push(request);

        return Response.status(Response.Status.OK).build();
    }
}