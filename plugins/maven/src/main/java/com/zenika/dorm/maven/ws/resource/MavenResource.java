package com.zenika.dorm.maven.ws.resource;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
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
    private MavenProcessor processor;

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
        Object entity = processor.get(uri);
        if (entity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(entity).build();
        }
    }

    @PUT
    @Path("{path:.*}/{filename}")
    public Response put(@PathParam("path") String path,
                        @PathParam("filename") String filename,
                        File file) {
        String uri = path + "/" + filename;
        processor.push(uri, file);
        return Response.status(Response.Status.OK).build();
    }
}