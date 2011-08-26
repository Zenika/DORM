package com.zenika.dorm.core.ws.provider;

import com.google.inject.Singleton;
import com.zenika.dorm.core.exception.DormProcessException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
public class DormProcessExceptionMapper implements ExceptionMapper<DormProcessException> {

    @Override
    public Response toResponse(DormProcessException e) {

        Response.Status status = null;

        switch (e.getType()) {
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

        return Response.status(status).entity(e.getMessage()).type("text/plain").build();
    }
}
