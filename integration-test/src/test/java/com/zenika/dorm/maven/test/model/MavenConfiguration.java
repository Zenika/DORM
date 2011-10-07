package com.zenika.dorm.maven.test.model;

import org.codehaus.jackson.annotate.JsonGetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenConfiguration {

    private String targetHostname;
    private int targetPort;
    private String targetBaseUrl;
    private String proxyHostname;
    private int proxyPort;
    private boolean proxyActive;

    @JsonProperty("target.hostname")
    public String getTargetHostname() {
        return targetHostname;
    }

    @JsonSetter("target.hostname")
    public void setTargetHostname(String targetHostname) {
        this.targetHostname = targetHostname;
    }

    @JsonProperty("target.port")
    public int getTargetPort() {
        return targetPort;
    }

    @JsonSetter("target.port")
    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    @JsonProperty("target.baseUrl")
    public String getTargetBaseUrl() {
        return targetBaseUrl;
    }

    @JsonSetter("target.baseUrl")
    public void setTargetBaseUrl(String targetBaseUrl) {
        this.targetBaseUrl = targetBaseUrl;
    }

    @JsonProperty("proxy.hostname")
    public String getProxyHostname() {
        return proxyHostname;
    }

    @JsonSetter("proxy.hostname")
    public void setProxyHostname(String proxyHostname) {
        this.proxyHostname = proxyHostname;
    }

    @JsonProperty("proxy.port")
    public int getProxyPort() {
        return proxyPort;
    }

    @JsonSetter("proxy.port")
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    @JsonProperty("proxy.active")
    public boolean isProxyActive() {
        return proxyActive;
    }

    @JsonSetter("proxy.active")
    public void setProxyActive(boolean proxyActive) {
        this.proxyActive = proxyActive;
    }
}
