package com.zenika.dorm.maven.importer.utils;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScheme;
import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;
import org.apache.commons.httpclient.auth.CredentialsProvider;

/**
 * @author: Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormCredentials implements CredentialsProvider{
    
    private final String user;
    private final String password;

    public DormCredentials(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public Credentials getCredentials(AuthScheme scheme, String host, int port, boolean proxy) throws CredentialsNotAvailableException {
        Credentials credentials = new UsernamePasswordCredentials(user, password);
        return credentials;
    }
}
