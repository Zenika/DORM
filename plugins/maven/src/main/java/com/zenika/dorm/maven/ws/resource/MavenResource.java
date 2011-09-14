package com.zenika.dorm.maven.ws.resource;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.ws.resource.AbstractResource;
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
public class MavenResource extends AbstractResource {

    private static final Logger LOG = LoggerFactory.getLogger(MavenResource.class);

    @Inject
    private Processor processor;

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

        if (LOG.isInfoEnabled()) {
            LOG.info("Maven webservice GET with path : " + path + " and filename : " + filename);
        }

        return Response.status(Response.Status.NOT_FOUND).build();
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