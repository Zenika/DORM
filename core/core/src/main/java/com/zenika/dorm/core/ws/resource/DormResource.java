package com.zenika.dorm.core.ws.resource;

import com.google.inject.Inject;
import com.sun.jersey.multipart.FormDataParam;
import com.zenika.dorm.core.exception.DormProcessException;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.builder.DormWebServiceRequestBuilder;
import com.zenika.dorm.core.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * REST resource for dorm dependency
 *
 * Push (POST) :
 * - metadata (name + version)
 * - metadata + file (filename + java.io.file)
 * - metadata + file + parent + usage (full qualifier)
 * - metadata + parent + usage
 *
 * Get (GET) :
 * -
 *
 * Edit (PUT) :
 * -
 *
 * Remove (DELETE) :
 * -
 *
 * @see com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension#getQualifier() for the parent qualifier
 */
@Path("dorm")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DormResource extends AbstractResource {

    private static final Logger LOG = LoggerFactory.getLogger(DormResource.class);

//    @Inject
//    private Processor processor;
//
//    public DormResource() {
//        LOG.info("Init dorm resource as web service");
//    }
//
//    /**
//     * Push metadata
//     *
//     * @param name    the name of the dorm dependency
//     * @param version the version of the dorm dependency
//     * @return the http response which represents the status of the push
//     */
//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Path("{name}/{version}")
//    public Response pushMetadata(@PathParam("name") String name,
//                                 @PathParam("version") String version) {
//
//        LOG.trace("POST with params : name = " + name + "; version = " + version);
//
//        DormWebServiceRequest request = new DormWebServiceRequestBuilder(DefaultDormMetadataExtension
//                .EXTENSION_NAME)
//                .property(DefaultDormMetadataExtension.METADATA_NAME, name)
//                .build();
//
//        return pushRequest(request);
//    }
//
//    /**
//     * Push metadata + file
//     *
//     * @param name     the name of the dorm dependency
//     * @param version  the version of the dorm dependency
//     * @param filename the filename of the pushed file
//     * @param file     the java representation of the pushed file
//     * @return the http response which represents the status of the push
//     */
//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Path("{name}/{version}/{filename}")
//    public Response pushMetadataAndFile(@PathParam("name") String name,
//                                        @PathParam("version") String version,
//                                        @PathParam("filename") String filename,
//                                        @FormDataParam("file") File file) {
//
//        LOG.trace("POST with params : name = " + name + "; version = " + version);
//
//        DormWebServiceRequest request = new DormWebServiceRequestBuilder(DefaultDormMetadataExtension.EXTENSION_NAME)
//                .filename(filename)
//                .file(file)
//                .property(DefaultDormMetadataExtension.METADATA_NAME, name)
//                .build();
//
//        return pushRequest(request);
//    }
//
//    /**
//     * Push metadata + file + parent + usage
//     *
//     * @param name     the name of the dorm dependency
//     * @param version  the version of the dorm dependency
//     * @param filename the filename of the pushed file
//     * @param file     the java representation of the pushed file
//     * @param parent   the parent of the dependency
//     * @param usage    the usage with the parent
//     * @return the http response which represents the status of the push
//     */
//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Path("{name}/{version}/{filename}/{parent}/{usage}")
//    public Response pushMetadataAndFileWithParent(@PathParam("name") String name,
//                                                  @PathParam("version") String version,
//                                                  @PathParam("filename") String filename,
//                                                  @PathParam("parent") String parent,
//                                                  @PathParam("usage") String usage,
//                                                  @FormDataParam("file") File file) {
//
//        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
//    }
//
//    /**
//     * Push metadata + parent + usage
//     *
//     * @param name    the name of the dorm dependency
//     * @param version the version of the dorm dependency
//     * @param parent  the parent of the dependency
//     * @param usage   the usage with the parent
//     * @return the http response which represents the status of the push
//     */
//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Path("{name}/{version}/{parent}/{usage}")
//    public Response createArtifactFromUri(@PathParam("name") String name,
//                                          @PathParam("version") String version,
//                                          @PathParam("parent") String parent,
//                                          @PathParam("usage") String usage) {
//
//        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
//    }
//
//    private Response pushRequest(DormWebServiceRequest request) {
//
//        LOG.debug("Request to push = " + request);
//
//        if (!processor.push(request)) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//
//        return Response.status(Response.Status.OK).build();
//    }
//
//
//    /**
//     * @param propertiesFile
//     * @param file
//     * @return
//     * @deprecated need to be updated to the graph dependency model
//     */
//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Path("/from-properties")
//    public Response createArtifactFromProperties(@FormDataParam("properties") File propertiesFile,
//                                                 @FormDataParam("file") File file) {
//
//        Properties properties = getPropertiesFromFile(propertiesFile);
//
//        String name;
//        String version;
//        String filename;
//
//        try {
//            name = properties.getProperty("name").toString();
//            version = properties.getProperty("version").toString();
//            filename = properties.getProperty("filename").toString();
//        } catch (NullPointerException e) {
//            throw new DormProcessException("Missing artifact metadata").type(DormProcessException.Type.NULL);
//        }
//
//        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
//    }
//
//    /**
//     * @param name
//     * @param version
//     * @return
//     * @deprecated need to be updated to the graph dependency model
//     */
//    @GET
//    @Produces("application/octet-stream")
//    @Path("{name}/{version}")
//    public Response getArtifactByMetadata(
//            @PathParam("name") String name, @PathParam("version") String version) {
//
//        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
//    }
//
//    /**
//     * @param name
//     * @param version
//     * @return
//     * @deprecated need to be updated to the graph dependency model
//     */
//    @DELETE
//    @Path("{name}/{version}/{filename}")
//    public Response removeArtifactByMetadata(@PathParam("name") String name,
//                                             @PathParam("version") String version) {
//
//        return Response.status(Response.Status.OK).build();
//    }
//
//    /**
//     * @param name
//     * @param version
//     * @param file
//     * @param filename
//     * @return
//     * @deprecated need to be updated to the graph dependency model
//     */
//    @PUT
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Path("{name}/{version}/{filename}")
//    public Response updateArtifact(@PathParam("name") String name,
//                                   @PathParam("version") String version,
//                                   @FormDataParam("file") File file,
//                                   @PathParam("filename") String filename) {
//
//        return Response.status(Response.Status.OK).build();
//    }
//
//    /**
//     * @param propertiesFile
//     * @param file
//     * @return
//     * @deprecated need to be updated to the graph dependency model
//     */
//    @PUT
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Path("from-properties")
//    public Response updateArtifact(@FormDataParam("properties") File propertiesFile,
//                                   @FormDataParam("file") File file) {
//
//        Properties properties = getPropertiesFromFile(propertiesFile);
//
//        String name;
//        String version;
//        String filename;
//
//        try {
//            name = properties.getProperty("name").toString();
//            version = properties.getProperty("version").toString();
//            filename = properties.getProperty("filename").toString();
//        } catch (NullPointerException e) {
//            throw new DormProcessException("Missing artifact metadata").type(DormProcessException.Type.NULL);
//        }
//
//        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
//    }
//
//    private Properties getPropertiesFromFile(File file) {
//
//        Properties properties = new Properties();
//        FileInputStream stream = null;
//
//        try {
//            stream = new FileInputStream(file);
//            properties.load(stream);
//        } catch (Exception e) {
//            throw new DormProcessException("Missing artifact metadata").type(DormProcessException.Type.NULL);
//        } finally {
//            if (null != stream) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//
//                }
//            }
//        }
//
//        return properties;
//    }
}
