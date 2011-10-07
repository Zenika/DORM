package com.zenika.dorm.maven.test.utils;

import com.zenika.dorm.maven.test.model.MavenConfiguration;
import com.zenika.dorm.maven.test.model.MavenSample;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenUtils {

    private static final Logger LOG = LoggerFactory.getLogger(MavenUtils.class);

    public static MavenSample getSample(String jsonPath) {
        ObjectMapper mapper = new ObjectMapper();
        InputStream stream = getInputStream(jsonPath);
        try {
            return mapper.readValue(stream, MavenSample.class);
        } catch (IOException e) {
            LOG.error("Unable to read the Json file at this location : ", e);
        }
        return null;
    }

    public static MavenSample getSample(File jsonFile){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonFile, MavenSample.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to mapper this Json file: " + jsonFile);
        }
    }

    public static InputStream getInputStream(String filePath){
        InputStream stream = ClassLoader.getSystemResourceAsStream(filePath);
        return stream;
    }

    public static URL getUrl(String filePath){
        URL url = ClassLoader.getSystemResource(filePath);
        url.getPath();
        return url;
    }

    public static String buildBaseUri(MavenConfiguration configuration) {
        return new StringBuilder(50)
                .append("http://")
                .append(configuration.getTargetHostname())
                .append(":")
                .append(configuration.getTargetPort())
                .append("/")
                .append(configuration.getTargetBaseUrl())
                .toString();
    }
}
