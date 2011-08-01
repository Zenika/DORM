package com.zenika.dorm.core.ws.resource;

import com.google.inject.Inject;
import com.sun.jersey.multipart.FormDataParam;
import com.zenika.dorm.core.exception.ArtifactException;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormFile;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.processor.Processor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * REST resource for dorm dependency
 *
 * Push (POST) :
 * - metadata (qualifier + version)
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
public class DormResource {

    @Inject
    private Processor processor;

    /**
     * Push metadata
     *
     * @param qualifier the name of the dorm dependency
     * @param version   the version of the dorm dependency
     * @return the http response which represents the status of the push
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{qualifier}/{version}")
    public Response createArtifactFromUri(@PathParam("qualifier") String qualifier,
                                          @PathParam("version") String version) {

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("qualifier", qualifier);
        properties.put("version", version);

//        if (!processor.push(DefaultDormOrigin.ORIGIN, properties)) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//        }

        return Response.status(Response.Status.OK).build();
    }

    /**
     * Push metadata + file
     *
     * @param qualifier the name of the dorm dependency
     * @param version   the version of the dorm dependency
     * @param filename  the filename of the pushed file
     * @param file      the java representation of the pushed file
     * @return the http response which represents the status of the push
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{qualifier}/{version}/{filename}")
    public Response createArtifactFromUri(@PathParam("qualifier") String qualifier,
                                          @PathParam("version") String version,
                                          @PathParam("filename") String filename,
                                          @FormDataParam("file") File file) {

        DormMetadata metadata = getMetadata(qualifier, version);
        DormFile dormFile = DefaultDormFile.create(filename, file);

        return Response.status(Response.Status.OK).build();
    }

    /**
     * Push metadata + file + parent + usage
     *
     * @param qualifier the name of the dorm dependency
     * @param version   the version of the dorm dependency
     * @param filename  the filename of the pushed file
     * @param file      the java representation of the pushed file
     * @param parent    the parent of the dependency
     * @param usage     the usage with the parent
     * @return the http response which represents the status of the push
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{qualifier}/{version}/{filename}/{parent}/{usage}")
    public Response createArtifactFromUri(@PathParam("qualifier") String qualifier,
                                          @PathParam("version") String version,
                                          @PathParam("filename") String filename,
                                          @PathParam("parent") String parent,
                                          @PathParam("usage") String usage,
                                          @FormDataParam("file") File file) {

        DormMetadata metadata = getMetadata(qualifier, version);
        DormFile dormFile = DefaultDormFile.create(filename, file);

        return Response.status(Response.Status.OK).build();
    }

    /**
     * Push metadata + parent + usage
     *
     * @param qualifier the name of the dorm dependency
     * @param version   the version of the dorm dependency
     * @param parent    the parent of the dependency
     * @param usage     the usage with the parent
     * @return the http response which represents the status of the push
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{qualifier}/{version}/{parent}/{usage}")
    public Response createArtifactFromUri(@PathParam("qualifier") String qualifier,
                                          @PathParam("version") String version,
                                          @PathParam("parent") String parent,
                                          @PathParam("usage") String usage) {

        DormMetadata metadata = getMetadata(qualifier, version);

        return Response.status(Response.Status.OK).build();
    }


    /**
     * @param propertiesFile
     * @param file
     * @return
     * @deprecated need to be updated to the graph dependency model
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/from-properties")
    public Response createArtifactFromProperties(@FormDataParam("properties") File propertiesFile,
                                                 @FormDataParam("file") File file) {

        Properties properties = getPropertiesFromFile(propertiesFile);

        String name;
        String version;
        String filename;

        try {
            name = properties.getProperty("name").toString();
            version = properties.getProperty("version").toString();
            filename = properties.getProperty("filename").toString();
        } catch (NullPointerException e) {
            throw new ArtifactException("Missing artifact metadata").type(ArtifactException.Type.NULL);
        }

//        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
//        serviceOld.pushArtifact(metadata, file, filename);

        return Response.status(Response.Status.OK).build();
    }

    /**
     * @param name
     * @param version
     * @return
     * @deprecated need to be updated to the graph dependency model
     */
    @GET
    @Produces("application/octet-stream")
    @Path("{name}/{version}")
    public StreamingOutput getArtifactByMetadata(
            @PathParam("name") String name, @PathParam("version") String version) {

//        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
//        final DormArtifact<MetadataExtension> artifact = serviceOld.getArtifact(metadata);

//        return new StreamingOutput() {
//            @Override
//            public void write(OutputStream output) throws IOException, WebApplicationException {
//
//                try {
//                    InputStream is = new FileInputStream(artifact.getFile().getFile());
//                    output.write(is.read());
//                } catch (IOException e) {
//                    throw new CoreException("Cannot read / write artifact stream", e);
//                }
//
//            }
//        };

        return null;
    }

    /**
     * @param name
     * @param version
     * @return
     * @deprecated need to be updated to the graph dependency model
     */
    @DELETE
    @Path("{name}/{version}/{filename}")
    public Response removeArtifactByMetadata(@PathParam("name") String name,
                                             @PathParam("version") String version) {

//        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
//        serviceOld.removeArtifact(metadata);

        return Response.status(Response.Status.OK).build();
    }

    /**
     * @param name
     * @param version
     * @param file
     * @param filename
     * @return
     * @deprecated need to be updated to the graph dependency model
     */
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{name}/{version}/{filename}")
    public Response updateArtifact(@PathParam("name") String name,
                                   @PathParam("version") String version,
                                   @FormDataParam("file") File file,
                                   @PathParam("filename") String filename) {

//        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
//        serviceOld.updateArtifact(metadata, file, filename);

        return Response.status(Response.Status.OK).build();
    }

    /**
     * @param propertiesFile
     * @param file
     * @return
     * @deprecated need to be updated to the graph dependency model
     */
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("from-properties")
    public Response updateArtifact(@FormDataParam("properties") File propertiesFile,
                                   @FormDataParam("file") File file) {

        Properties properties = getPropertiesFromFile(propertiesFile);

        String name;
        String version;
        String filename;

        try {
            name = properties.getProperty("name").toString();
            version = properties.getProperty("version").toString();
            filename = properties.getProperty("filename").toString();
        } catch (NullPointerException e) {
            throw new ArtifactException("Missing artifact metadata").type(ArtifactException.Type.NULL);
        }

//        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
//        serviceOld.updateArtifact(metadata, file, filename);

        return Response.status(Response.Status.OK).build();
    }

    private Properties getPropertiesFromFile(File file) {

        Properties properties = new Properties();
        FileInputStream stream = null;

        try {
            stream = new FileInputStream(file);
            properties.load(stream);
        } catch (Exception e) {
            throw new ArtifactException("Properties invalid")
                    .type(ArtifactException.Type.NULL);
        } finally {
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException e) {

                }
            }
        }

        return properties;
    }

    /**
     * Create simple dorm metadata with dorm origin from a name and a version
     *
     * @param name    the name of the dorm dependency
     * @param version the version of the dorm dependency
     * @return the dorm metadata which represents the name and the version
     */
    private DormMetadata getMetadata(String name, String version) {
        return new DefaultDormMetadata(version, new DefaultDormMetadataExtension(name));
    }
}
