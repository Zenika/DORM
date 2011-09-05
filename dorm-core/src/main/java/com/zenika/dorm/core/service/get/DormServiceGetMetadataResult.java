package com.zenika.dorm.core.service.get;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormServiceGetMetadataResult {

    private List<DormMetadata> metadatas = new ArrayList<DormMetadata>();

    public boolean hasResult() {
        return !metadatas.isEmpty();
    }

    public boolean hasUniqueResult() {
        return metadatas.size() == 1;
    }

    public void addMetadata(DormMetadata metadata) {
        metadatas.add(metadata);
    }

    public DormMetadata getUniqueMetadata() throws CoreException {

        if (!hasUniqueResult()) {
            throw new CoreException("Result does not contain unique metadata");
        }

        return metadatas.get(0);
    }

    public List<DormMetadata> getMetadatas() {
        return metadatas;
    }

    public void setMetadatas(List<DormMetadata> metadatas) {

        if (null == metadatas) {
            metadatas = new ArrayList<DormMetadata>();
        }

        this.metadatas = metadatas;
    }
}
