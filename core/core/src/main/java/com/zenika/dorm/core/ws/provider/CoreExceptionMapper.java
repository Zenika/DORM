package com.zenika.dorm.core.ws.provider;

import com.google.inject.Singleton;
import com.zenika.dorm.core.exception.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
public class CoreExceptionMapper implements ExceptionMapper<CoreException> {

    private static final Logger LOG = LoggerFactory.getLogger(CoreExceptionMapper.class);

    @Override
    public Response toResponse(CoreException e) {
        LOG.error(e.getMessage(), e);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage())
                .type("text/plain").build();
    }
}
