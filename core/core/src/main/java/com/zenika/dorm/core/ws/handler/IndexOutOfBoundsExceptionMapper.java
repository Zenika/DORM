package com.zenika.dorm.core.ws.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class IndexOutOfBoundsExceptionMapper implements ExceptionMapper<IndexOutOfBoundsException> {

    @Override
    public Response toResponse(IndexOutOfBoundsException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
