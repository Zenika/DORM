package com.zenika.dorm.maven.processor.helper;

import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenConstant;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public final class MavenRequestHelper {

    private static final Logger LOG = LoggerFactory.getLogger(MavenRequestHelper.class);

    private MavenRequestHelper() {

    }

    private static final String STRING_PATTERN_CLASSIFIER = "\\-[0-9]+\\.[0-9]+\\-[0-9]\\.";
    private static final String STRING_PATTERN_TIMESTAMP = "\\-[a-z]+\\.";

    private static final Pattern PATTERN_CLASSIFIER = Pattern.compile(STRING_PATTERN_CLASSIFIER);
    private static final Pattern PATTERN_TIMESTAMP = Pattern.compile(STRING_PATTERN_TIMESTAMP);

    /**
     * Return the artifact's classifier according to the file name. If the classifier doesn't exist an exception is throwing.
     * <p><i>Example : </i>
     * <pre>
     * commons-io-1.4-<i>bin</i>.sha1
     * return : bin
     * </pre>
     *
     * @param request the request to maven
     * @return the classifier or null if not exists
     */
    public static String getClassifierIfExists(DormRequest request) {

        Matcher matcher = PATTERN_CLASSIFIER.matcher(request.getFilename());

        if (!matcher.find()) {
            return null;
        }

        String classifier = matcher.group();
        return classifier.substring(1, classifier.length() - 1);
    }

    public static String getGroupId(DormRequest request) {

        if (null == request.getProperty(MavenMetadataExtension.METADATA_GROUPID)) {
            throw new MavenException("Maven groupId is required").type(MavenException.Type.NULL);
        }

        return request.getProperty(MavenMetadataExtension.METADATA_GROUPID).replace('/', '.');
    }

    public static String getMavenType(DormRequest request) {

        if (MavenMetadataExtension.MAVEN_METADATA_XML.equals(request.getFilename())) {
            return MavenMetadataExtension.MAVEN_METADATA_XML;
        }

        String type = FilenameUtils.getExtension(request.getFilename());

        if (null == type) {
            throw new MavenException("Type is required (jar, pom, etc...)");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Type of the maven file = " + type);
        }

        if (!MavenConstant.Type.isMavenType(type)) {
            throw new MavenException("Invalid maven type : " + type);
        }

        return type;
    }
}
