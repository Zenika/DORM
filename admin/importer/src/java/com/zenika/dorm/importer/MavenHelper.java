package com.zenika.dorm.importer;

import org.apache.maven.model.Model;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenHelper {

    public String getArtifactPath(Model model, String fileName) {
        StringBuilder sb = new StringBuilder();
        if (!(model.getGroupId() == null || model.getGroupId().isEmpty())) {
            sb.append(model.getGroupId().replace('.', '/'));
            sb.append('/');
        } else {
            if (!(model.getParent().getGroupId() == null || model.getParent().getGroupId().isEmpty())) {
                sb.append(model.getParent().getGroupId().replace('.', '/'));
                sb.append('/');
            }
        }
        if (!(model.getArtifactId() == null || model.getArtifactId().isEmpty())) {
            sb.append(model.getArtifactId());
            sb.append('/');
        }
        if (!(model.getVersion() == null || model.getVersion().isEmpty())) {
            sb.append(model.getVersion());
            sb.append('/');
        } else {
            if (!(model.getParent().getVersion() == null || model.getParent().getVersion().isEmpty())) {
                sb.append(model.getParent().getVersion());
                sb.append('/');
            }
        }
        sb.append(fileName);
        return sb.toString();
    }
}
