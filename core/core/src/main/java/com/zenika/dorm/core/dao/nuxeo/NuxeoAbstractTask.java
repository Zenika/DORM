package com.zenika.dorm.core.dao.nuxeo;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.nuxeo.provider.NuxeoWebResourceWrapper;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public abstract class NuxeoAbstractTask {

    private static final Logger logger = LoggerFactory.getLogger(NuxeoAbstractTask.class);

    @Inject
    protected NuxeoWebResourceWrapper wrapper;
    @Inject
    protected ExtensionFactoryServiceLoader serviceLoader;

    protected abstract Object execute();

    protected static void logRequest(String type, WebResource resource, String path) {
        logger.info(type + " to " + resource.getURI() + "/" + path);
    }

    protected static void logRequest(String type, URI uri) {
        logger.info(type + " to " + uri);
    }

    protected static void logRequest(String type, String uri) {
        logger.info(type + " to " + uri);
    }
}
