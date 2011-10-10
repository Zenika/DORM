package com.zenika.dorm.core.model.ws;

import com.zenika.dorm.core.model.ws.builder.DormWebServiceAbstractBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DormWebServiceProcess {

    protected final String origin;
    private final Map<String, String> properties = new HashMap<String, String>();

    protected DormWebServiceProcess(DormWebServiceAbstractBuilder builder) {
        this.origin = builder.getOrigin();
        this.properties.putAll(builder.getProperties());
    }

    public String getOrigin() {
        return origin;
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * The request properties in read only mode
     *
     * @return the request properties
     */
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }
}
