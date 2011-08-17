package com.zenika.dorm.core.ws.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class AbstractResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("ping")
    public Response ping() {
        return Response.status(Response.Status.OK).entity("pong").build();
    }
}
