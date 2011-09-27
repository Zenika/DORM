package com.zenika.dorm.core.test.model;

import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormMetadataTest extends DormMetadata{

    public static final String EXTENSION_NAME = "DormTest";
    public static final String VERSION_FIELD = "version";
    public static final String FIELD_FIELD = "field";
    public static final String DATA_FIELD = "data";

    private String version;

    private String field;
    private String data;

    public DormMetadataTest(String version, String field, String data) {
        this.version = version;
        this.field = field;
        this.data = data;
    }

    public DormMetadataTest(Long id, String version, String field, String data) {
            super(id);
            this.version = version;
            this.field = field;
            this.data = data;
        }


    public static DormMetadata getDefault(){
        return new DormMetadataTest("1.0.0", "property", "DATA");
    }

    @Override
    public String getName() {
        return EXTENSION_NAME + "-" + version;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getExtensionName() {
        return EXTENSION_NAME;
    }

    public String getData() {
        return data;
    }

    public String getField() {
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DormMetadataTest)) return false;

        DormMetadataTest that = (DormMetadataTest) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String
    toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DormMetadataTest");
        sb.append("{id='").append(id).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append(", extensionName='").append(getExtensionName()).append('\'');
        sb.append(", fields='").append(field).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
