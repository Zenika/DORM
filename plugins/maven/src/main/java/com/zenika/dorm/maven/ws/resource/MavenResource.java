package com.zenika.dorm.maven.ws.resource;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Path("maven")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MavenResource {

    private static final Logger LOG = LoggerFactory.getLogger(MavenResource.class);
    private static final String USER_PROPERTIES = "/com/zenika/dorm/maven/configuration/user.properties";

    @Inject
    private MavenProcessor processor;

    @Context
    private HttpHeaders httpHeaders;

    public MavenResource() {
        if (LOG.isInfoEnabled()) {
            LOG.info("Call to maven webservice");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("{path:.*}/{filename}")
    public Response get(@PathParam("path") String path, @PathParam("filename") String filename) {
        Response response;

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
    }

    @PUT
    @Path("{path:.*}/{filename}")
    public Response put(@PathParam("path") String path, @PathParam("filename") String filename, File file) {
        if (httpHeaders.getRequestHeader("Authorization") != null) {
            String auth = httpHeaders.getRequestHeader("Authorization").get(0);
            if (allowUser(auth)) {
                String role = getRole(auth);
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

    private boolean allowUser(String auth) {
        Properties properties = getProperties();
        String[] user = decodeUserAndPassword(auth);
        String roleName = user[0];
        String rolePassword = user[1];
        String propertyRolePassword = properties.getProperty("security.role." + roleName);
        if (propertyRolePassword == null) {
            return false;
        }        
        return rolePassword.equals(propertyRolePassword);
    }

    private String[] decodeUserAndPassword(String auth) {
        String userPassEncoded = auth.substring(6);
        BASE64Decoder dec = new BASE64Decoder();
        String userPassDecoded;
        try {
            userPassDecoded = new String(dec.decodeBuffer(userPassEncoded));
            LOG.debug("Password: " + userPassDecoded);
        } catch (IOException e) {
            throw new CoreException("Unable to decrypt the password");
        }
        return userPassDecoded.split(":");
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream(USER_PROPERTIES));
        } catch (IOException e) {
            throw new CoreException("Unable to find " + USER_PROPERTIES);
        }
        return properties;
    }

    public String getRole(String auth) {
        return decodeUserAndPassword(auth)[0];
    }
}