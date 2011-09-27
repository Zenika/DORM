package com.zenika.dorm.core.dao.query;

import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormBasicQuery {

    private String name;
    private String version;
    private String extensionName;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public static class Builder {

        private DormBasicQuery query = new DormBasicQuery();

        public Builder() {
        }

        public Builder(DormMetadata metadata) {
            name(metadata.getName());
            version(metadata.getVersion());
            extensionName(metadata.getExtensionName());
        }

        public Builder name(String name) {
            query.name = name;
            return this;
        }

        public Builder version(String version) {
            query.version = version;
            return this;
        }

        public Builder extensionName(String extensionName) {
            query.extensionName = extensionName;
            return this;
        }

        public DormBasicQuery build() {
            return query;
        }
    }
}
