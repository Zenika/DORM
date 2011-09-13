package com.zenika.dorm.maven.model.builder;

import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.maven.helper.MavenSpecificHelper;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.formatter.MavenFilenameFormatter;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataBuilderFromRequest extends MavenMetadataBuilder {

    public MavenMetadataBuilderFromRequest(DormWebServiceRequest request) {

        super(request.getProperty(MavenMetadata.METADATA_GROUPID));

        mavenMetadata = MavenSpecificHelper.isMavenMetadataFile(request.getFilename());
        if (mavenMetadata) {
            return;
        }

        MavenFilenameFormatter formatter = new MavenFilenameFormatter(request.getFilename());
        classifier = formatter.getClassifier();
        extension = formatter.getExtension();
        packaging = request.getProperty(MavenMetadata.METADATA_PACKAGING);

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
