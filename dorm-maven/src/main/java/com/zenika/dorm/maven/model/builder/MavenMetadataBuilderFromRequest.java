package com.zenika.dorm.maven.model.builder;

import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.maven.helper.MavenSpecificHelper;
import com.zenika.dorm.maven.model.formatter.MavenFilenameFormatter;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataBuilderFromRequest extends MavenMetadataBuilder {

    public MavenMetadataBuilderFromRequest(DormWebServiceRequest request) {

        super(request.getProperty(MavenMetadataExtension.METADATA_GROUPID));

        mavenMetadata = MavenSpecificHelper.isMavenMetadataFile(request.getFilename());
        if (mavenMetadata) {
            return;
        }

        MavenFilenameFormatter formatter = new MavenFilenameFormatter(request.getFilename());
        classifier = formatter.getClassifier();
        extension = formatter.getExtension();
        packaging = request.getProperty(MavenMetadataExtension.METADATA_PACKAGING);

        snapshot = MavenSpecificHelper.isSnapshot(version);
        if (snapshot) {
            timestamp = formatter.getTimestamp();
            buildNumber = formatter.getBuildNumber();
        }
    }

    private void buildMavenOptionnalMetadata() {

    }

    private void buildMavenSnpashot() {

    }
}
