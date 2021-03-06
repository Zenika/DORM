package com.zenika.dorm.maven.ws.resource;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.security.DormSecurity;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import com.zenika.dorm.maven.service.MavenSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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

    private HttpHeaders httpHeaders;

    private MavenSecurity mavenSecurity;

    @Inject
    private DormSecurity dormSecurity;

    @Inject
    public MavenResource(@Context HttpHeaders headers) {
        this.httpHeaders = headers;
        if (httpHeaders.getRequestHeader("Authorization") != null) {
            String auth = httpHeaders.getRequestHeader("Authorization").get(0);
            mavenSecurity = new MavenSecurity(auth);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("Call to maven webservice");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("{path:.*}/{filename}")
    public Response get(@PathParam("path") String path, @PathParam("filename") String filename) {
        Response response;
        if (isSecure()) {
            if (mavenSecurity.isAllowedUser()) {
                String uri = path + "/" + filename;

                if (LOG.isInfoEnabled()) {
                    LOG.info("Maven webservice GET with uri : " + uri);
                }

                DormWebServiceRequest request = new DormWebServiceRequest.Builder()
                        .origin(MavenMetadata.EXTENSION_NAME)
                        .property("uri", uri)
                        .build();

                DormWebServiceResult result = processor.get(request);
                switch (result.getResult()) {
                    case FOUND:
                        response = Response.ok(result.getEntity()).build();
                        break;
                    case NOTFOUND:
                        response = Response.status(Response.Status.NOT_FOUND).build();
                        break;
                    default:
                        response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
                return response;
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"Dorm Realm\"").build();
    }

    @PUT
    @Path("{path:.*}/{filename}")
    public Response put(@PathParam("path") String path, @PathParam("filename") String filename, File file) {
        if (isSecure()) {
            if (mavenSecurity.isAllowedUser()) {
                dormSecurity.setRole(mavenSecurity.getRole());
                String uri = path + "/" + filename;
                if (LOG.isInfoEnabled()) {
                    LOG.info("Maven webservice PUT with uri : " + uri);
                }
                DormWebServiceRequest.Builder requestBuilder = new DormWebServiceRequest.Builder()
                        .origin(MavenMetadata.EXTENSION_NAME)
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
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"Dorm Realm\"").build();
    }

    private boolean isSecure() {
        return mavenSecurity != null;
    }
}