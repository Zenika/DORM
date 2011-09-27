package com.zenika.dorm.core.ws.rs.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.zenika.dorm.core.dao.DormDaoFactory;
import com.zenika.dorm.core.model.DormMetadata;

@Path("dorm")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class DormResource {

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Path("save")
	public Response save(DormMetadata metadata) {

		DormDaoFactory.getDormDao().save(metadata);
		return Response.ok().build();
	}

	@GET
	@Path("get/{functionnalId}")
	public Response get(@PathParam("functionnalId") String functionnalId) {

		if (null == functionnalId || functionnalId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		String[] elements = functionnalId.split(":");

		if (null == elements || elements.length < 2) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		DormMetadata metadata = DormDaoFactory.getDormDao().getWithExtension(functionnalId, elements[0]);

		if (null == metadata) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(metadata).build();
	}
}
