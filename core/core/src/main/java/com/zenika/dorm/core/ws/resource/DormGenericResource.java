package com.zenika.dorm.core.ws.resource;

import com.zenika.dorm.core.model.DormMetadataLabel;
import com.zenika.dorm.core.model.DormResource;
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
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    @Path("{path:.*}")
    public Response put(@PathParam("path") String path, File file) {

        String userAgent = getUserAgent();

        DormWebServiceRequest request = new DormWebServiceRequest.Builder()
                .userAgent(userAgent)
                .property("path", path)
                .file(file)
                .build();

        processor.push(request);

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/label/{label}/{plugin}")
    public Response getByLabel(@PathParam("label") String labelName, @PathParam("plugin") String plugin) throws IOException {

        DormWebServiceRequest request = new DormWebServiceRequest.Builder()
                .origin(plugin)
                .build();

        List<DormResource> resources = processor.getExtension(request).getByLabel(labelName);

        byte data[] = new byte[2048];
        ZipOutputStream os = new ZipOutputStream(new FileOutputStream("artifacts.zip"));

        for (DormResource resource : resources) {

            FileInputStream is = new FileInputStream(resource.getFile());
            BufferedInputStream bis = new BufferedInputStream(is, 2048);

            ZipEntry entry = new ZipEntry(resource.getName());
            os.putNextEntry(entry);

            int count;
            while ((count = bis.read(data, 0, 2048)) != -1) {
                os.write(data, 0, count);
            }

            os.closeEntry();
            bis.close();
        }

        os.close();

        return Response.status(Response.Status.OK).entity(os).build();
    }

    @PUT
    @Path("/label/{label}/{id}")
    public Response addLabel(@PathParam("label") String labelName, @PathParam("id") long id) throws IOException {

        processor.addLabel(labelName, id);
        return Response.status(Response.Status.OK).build();
    }

    private String getUserAgent() {

        if (null == headers.getRequestHeader("user-agent")) {
            return null;
        }

        return headers.getRequestHeader("user-agent").get(0);
    }
}
