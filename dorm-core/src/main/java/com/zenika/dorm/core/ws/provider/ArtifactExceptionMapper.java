package com.zenika.dorm.core.ws.provider;

import com.zenika.dorm.core.exception.ArtifactException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ArtifactExceptionMapper implements
		ExceptionMapper<ArtifactException> {

	public Response toResponse(ArtifactException me) {

		Response.Status status = null;

		switch (me.getType()) {
		case ERROR:
			status = Response.Status.INTERNAL_SERVER_ERROR;
			break;
		case NULL:
			status = Response.Status.NOT_FOUND;
			break;
		case FORBIDDEN:
			status = Response.Status.FORBIDDEN;
			break;

		default:
			status = Response.Status.INTERNAL_SERVER_ERROR;
			break;
		}

		return Response.status(status).entity(me.getMessage())
				.type("text/plain").build();
	}
}
