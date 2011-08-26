package com.zenika.dorm.core.service.impl.get;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.service.get.DormServiceGetRequest;
import com.zenika.dorm.core.service.get.DormServiceGetValues;
import com.zenika.dorm.core.service.impl.DefaultDormServiceRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormServiceGetRequest extends DefaultDormServiceRequest
        implements DormServiceGetRequest {


    private boolean transitiveDependencies;
    private DormServiceGetValues values;

    public DefaultDormServiceGetRequest(String processName, DormMetadataExtension metadataExtension) {
        super(processName);
        values = new DefaultDormServiceGetValues(metadataExtension);
    }

    @Override
    public boolean getTransitiveDependencies() {
        return transitiveDependencies;
    }

    public void setTransitiveDependencies(boolean transitiveDependencies) {
        this.transitiveDependencies = transitiveDependencies;
    }

    @Override
    public boolean isUniqueResultRequest() {
        return getValues().hasQualifier();
    }

    @Override
    public DormServiceGetValues getValues() {
        return values;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("transitiveDependencies", transitiveDependencies)
                .append("values", values)
                .appendSuper(super.toString())
                .toString();
    }
}
