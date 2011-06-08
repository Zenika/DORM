package com.zenika.dorm.core.ws.resource;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.ArtifactException;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;
import com.zenika.dorm.core.service.DormService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Path("dorm")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DormResource {

    @Inject
    private DormService service;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{name}/{version}/{filename}")
    public Response createArtifactFromPath(@PathParam("name") String name,
                                           @PathParam("version") String version,
                                           @FormParam("file") File file,
                                           @PathParam("filename") String filename) {

        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
        service.pushArtifact(metadata, file, filename);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/from-properties")
    public Response createArtifactFromProperties(@FormParam("properties") File propertiesFile,
                                                 @FormParam("file") File file) {

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

        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
        service.pushArtifact(metadata, file, filename);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{name}/{version}")
    public DormArtifact<MetadataExtension> getArtifactByMetadata(
            @PathParam("name") String name, @PathParam("version") String version) {

        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
        DormArtifact<MetadataExtension> artifact = service.getArtifact(metadata);

        return null;
    }

    @DELETE
    @Path("{name}/{version}/{filename}")
    public Response removeArtifactByMetadata(@PathParam("name") String name,
                                             @PathParam("version") String version) {

        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
        service.removeArtifact(metadata);

        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{name}/{version}/{filename}")
    public Response updateArtifact(@PathParam("name") String name,
                                   @PathParam("version") String version,
                                   @FormParam("file") File file,
                                   @PathParam("filename") String filename) {

        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
        service.updateArtifact(metadata, file, filename);

        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("{name}/{version}/{filename}")
    public Response updateArtifact(@FormParam("properties") File propertiesFile,
                                   @FormParam("file") File file) {

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

        DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(name, version);
        service.updateArtifact(metadata, file, filename);

        return Response.status(Response.Status.OK).build();
    }

    protected Properties getPropertiesFromFile(File file) {

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
}
