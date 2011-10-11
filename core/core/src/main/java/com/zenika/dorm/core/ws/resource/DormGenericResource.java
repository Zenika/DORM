package com.zenika.dorm.core.ws.resource;

import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.processor.DormProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Path("dorm")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DormGenericResource {

    private static final Logger LOG = LoggerFactory.getLogger(DormGenericResource.class);

    @Inject
    private DormProcessor processor;

    @Context
    private HttpHeaders headers;

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("{path:.*}")
    public Response get(@PathParam("path") String path) {

        String userAgent = getUserAgent();

        DormWebServiceRequest request = new DormWebServiceRequest.Builder()
                .userAgent(userAgent)
                .property("path", path)
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
    @Path("{path:.*}")
    public Response get(@PathParam("path") String path, File file) {

        String userAgent = getUserAgent();

        DormWebServiceRequest request = new DormWebServiceRequest.Builder()
                .userAgent(userAgent)
                .property("path", path)
                .file(file)
                .build();

        processor.push(request);

        return Response.status(Response.Status.OK).build();
    }

    private String getUserAgent() {

        if (null == headers.getRequestHeader("user-agent")) {
            return null;
        }

        return headers.getRequestHeader("user-agent").get(0);
    }
}
