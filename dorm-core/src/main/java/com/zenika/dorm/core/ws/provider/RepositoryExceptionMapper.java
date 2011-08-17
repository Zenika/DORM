package com.zenika.dorm.core.ws.provider;

import com.zenika.dorm.core.exception.ArtifactException;
import com.zenika.dorm.core.exception.RepositoryException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Provider
public class RepositoryExceptionMapper implements ExceptionMapper<RepositoryException> {

    @Override
    public Response toResponse(RepositoryException exception) {
        Response.Status status = null;

		switch (exception.getType()) {
		case ERROR:
			status = Response.Status.INTERNAL_SERVER_ERROR;
			break;
		case NULL:
			status = Response.Status.NOT_FOUND;
			break;

		default:
			status = Response.Status.INTERNAL_SERVER_ERROR;
			break;
		}

		return Response.status(status).entity(exception.getMessage())
				.type("text/plain").build();
    }
}
