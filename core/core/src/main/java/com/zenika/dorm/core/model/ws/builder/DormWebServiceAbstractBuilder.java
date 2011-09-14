package com.zenika.dorm.core.model.ws.builder;

import com.zenika.dorm.core.model.ws.DormWebServiceProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DormWebServiceAbstractBuilder<T extends DormWebServiceAbstractBuilder> {

    private String origin;
    private Map<String, String> properties = new HashMap<String, String>();

    protected abstract T self();

    public abstract DormWebServiceProcess build();

    public DormWebServiceAbstractBuilder(String origin) {
        this.origin = origin;
    }

    public DormWebServiceAbstractBuilder(DormWebServiceProcess request) {
        this.origin = request.getOrigin();
        this.properties.putAll(request.getProperties());
    }

    public T origin(String origin) {
        this.origin = origin;
        return self();
    }

    public T property(String name, String value) {
        properties.put(name, value);
        return self();
    }

    public String getOrigin() {
        return origin;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
