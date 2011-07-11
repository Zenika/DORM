package com.zenika.dorm.core.ws.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.zenika.dorm.core.exception.CoreException;

@Provider
public class CoreExceptionMapper implements ExceptionMapper<CoreException> {

	public Response toResponse(CoreException me) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(me.getMessage()).type("text/plain").build();
	}
}
