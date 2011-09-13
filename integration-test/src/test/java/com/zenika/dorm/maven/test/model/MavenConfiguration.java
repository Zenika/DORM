package com.zenika.dorm.maven.test.model;

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

    public String getTargetHostname() {
        return targetHostname;
    }

    @JsonSetter("target.hostname")
    public void setTargetHostname(String targetHostname) {
        this.targetHostname = targetHostname;
    }

    public int getTargetPort() {
        return targetPort;
    }

    @JsonSetter("target.port")
    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public String getTargetBaseUrl() {
        return targetBaseUrl;
    }

    @JsonSetter("target.baseUrl")
    public void setTargetBaseUrl(String targetBaseUrl) {
        this.targetBaseUrl = targetBaseUrl;
    }

    public String getProxyHostname() {
        return proxyHostname;
    }

    @JsonSetter("proxy.hostname")
    public void setProxyHostname(String proxyHostname) {
        this.proxyHostname = proxyHostname;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    @JsonSetter("proxy.port")
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public boolean isProxyActive() {
        return proxyActive;
    }

    @JsonSetter("proxy.active")
    public void setProxyActive(boolean proxyActive) {
        this.proxyActive = proxyActive;
    }
}
