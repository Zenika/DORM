package com.zenika.dorm.maven.service;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.security.role.DormSecurityRole;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenSecurity {

    public static final String USER_PROPERTIES = "/com/zenika/dorm/maven/configuration/user.properties";
    private String role;
    private String password;
    private Properties properties;
    private boolean allowUser;

    public MavenSecurity(String auth) {
        this.properties = getProperties();
        String[] user = decodeUserAndPassword(auth);
        this.role = user[0];
        this.password = user[1];
        this.allowUser = allowUser();
    }

    private boolean allowUser() {
        String propertyRolePassword = properties.getProperty("security.role." + role);
        if (propertyRolePassword == null) {
            return false;
        }
        return password.equals(propertyRolePassword);
    }

    public boolean isAllowedUser() {
        return allowUser;
    }

    private String[] decodeUserAndPassword(String auth) {
        String userPassEncoded = auth.substring(6);
        Base64 dec = new Base64();
        String userPassDecoded;
        userPassDecoded = new String(dec.decode(userPassEncoded));
        return userPassDecoded.split(":");
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream(USER_PROPERTIES));
        } catch (IOException e) {
            throw new CoreException("Unable to find " + USER_PROPERTIES);
        }
        return properties;
    }

    public DormSecurityRole getRole() {
        try {
            Class<?> securityRole = Class.forName(properties.getProperty("security.role." + role + ".class"));
            return (DormSecurityRole) securityRole.newInstance();
        } catch (ClassNotFoundException e) {
            throw new CoreException("Role class not found", e);
        } catch (InstantiationException e) {
            throw new CoreException("Unable to instantiate role class", e);
        } catch (IllegalAccessException e) {
            throw new CoreException("Unable to instantiate role class", e);
        }
    }
}
