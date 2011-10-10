package com.zenika.dorm.maven.processor.extension;

import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.processor.extension.ProcessorExtension;
import com.zenika.dorm.core.processor.extension.RequestAnalyser;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.pom.MavenPomReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenRequestAnalyser implements RequestAnalyser {

    private static final Logger LOG = LoggerFactory.getLogger(MavenRequestAnalyser.class);

    @Inject
    private MavenProcessor processor;

    @Override
    public boolean isKnownRequest(DormWebServiceRequest request) {

        // user agent
        if (StringUtils.isNotBlank(request.getUserAgent())) {
            return isKnownUserAgent(request.getUserAgent());
        }

        // origin
        else if (StringUtils.isNotBlank(request.getOrigin())) {
            return isKnownOrigin(request.getOrigin());
        }

        // pom
        else if (null != request.getFile()) {
            return isKnownFile(request.getFile());
        }

        return false;
    }

    @Override
    public ProcessorExtension getExtension() {
        return processor;
    }

    private boolean isKnownUserAgent(String userAgent) {

        if (StringUtils.startsWith(userAgent, "Apache-Maven")) {
            return true;
        }

        return false;
    }

    private boolean isKnownOrigin(String origin) {
        return StringUtils.equals(origin, MavenMetadata.EXTENSION_NAME);
    }

    private boolean isKnownFile(File file) {

        try {
            new MavenPomReader(file);
        } catch (MavenException e) {
            LOG.debug("Exception catched when trying to read file as maven pom in analyser", e);
            return false;
        }

        return true;
    }
}
