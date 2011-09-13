package com.zenika.dorm.maven.processor.comparator;

import com.zenika.dorm.core.util.DormStringUtils;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenSnapshotTimestampComparator implements Comparator<MavenMetadata> {

    private static final Logger LOG = LoggerFactory.getLogger(MavenSnapshotTimestampComparator.class);

    @Override
    public int compare(MavenMetadata extension1, MavenMetadata extension2) {

        if (DormStringUtils.oneIsBlank(extension1.getTimestamp(), extension2.getTimestamp())) {
            throw new MavenException("Timestamp is missing in maven snapshot metadata");
        }

        String timestamp1 = extension1.getTimestamp().replace(".", "");
        String timestamp2 = extension2.getTimestamp().replace(".", "");

        if (LOG.isDebugEnabled()) {
            LOG.debug("Compare timestamps : " + timestamp1 + " and : " + timestamp2);
        }

        Long value1, value2;

        try {
            value1 = Long.valueOf(timestamp1);
            value2 = Long.valueOf(timestamp2);
        } catch (NumberFormatException e) {
            throw new MavenException("Timestamp are not well formated", e);
        }

        return value1.compareTo(value2);
    }
}
