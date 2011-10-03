package com.zenika.dorm.core.ws.rs.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.zenika.dorm.core.dao.DormDaoFactory;
import com.zenika.dorm.core.model.DormMetadata;

@Path("dorm")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class DormResource {

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("save")
	public Response save(DormMetadata metadata) {

		metadata = DormDaoFactory.getDormDao().save(metadata);
		return Response.ok(metadata).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("get/{extensionName}/{name}/{version}")
	public Response get(@PathParam("extensionName") String extensionName, @PathParam("name") String name,
			@PathParam("version") String version) {

		if (null == extensionName || extensionName.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).entity("Extension name is required").build();
		}

		if (null == name || name.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).entity("Name is required").build();
		}

		if (null == version || version.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).entity("Version is required").build();
		}

		DormMetadata metadata = DormDaoFactory.getDormDao().get(extensionName, name, version);

		if (null == metadata) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(metadata).build();
	}
}
