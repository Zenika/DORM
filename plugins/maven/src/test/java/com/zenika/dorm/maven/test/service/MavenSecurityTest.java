package com.zenika.dorm.maven.test.service;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.maven.service.MavenSecurity;
import org.apache.commons.codec.binary.Base64;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;
/**
 * @author: Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenSecurityTest {

    private static Properties properties;
    
    @BeforeClass
    public static void setUpClass() {
        properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(MavenSecurity.USER_PROPERTIES.substring(1)));
        } catch (IOException e) {
            throw new CoreException("Unable to retrieve properties", e);
        }        
    }
    
    @Test
    public void mavenSecurityTest(){
        for (Map.Entry entry: properties.entrySet()){
            if (!((String) entry.getKey()).endsWith(".class")){
                String role = ((String)entry.getKey()).substring(14);
                String password = (String) entry.getValue();
                String clearString = new StringBuilder(40)
                        .append(role)
                        .append(":")
                        .append(password)
                        .toString();
                String encodeString = encodeString(clearString);
                MavenSecurity mavenSecurity = new MavenSecurity(encodeString);
                assertTrue(mavenSecurity.isAllowedUser());
                String classExpected = (String) properties.get(generatePropertyClassKey(role));
                String classResult = mavenSecurity.getRole().getClass().getName();
                assertEquals(classExpected, classResult);
            }
        }
    }
    
    @Test
    public void mavenSecurityTestBadCase() {
        String role1 = encodeString("test:test");
        assertFalse(new MavenSecurity(role1).isAllowedUser());
        String role2 = encodeString("admin:badPassword");
        assertFalse(new MavenSecurity(role2).isAllowedUser());
        String role3 = encodeString("badRole:password");
        assertFalse(new MavenSecurity(role3).isAllowedUser());
    }
    
    private String encodeString(String str){
        Base64 encoder = new Base64();
        String encodeString = new StringBuilder(40)
                .append("BASIC ")
                .append(encoder.encodeAsString(str.getBytes()))
                .toString();        
        return encodeString;
    }
    
    private String generatePropertyClassKey(String role){
        return new StringBuilder(40)
                .append("security.role.")
                .append(role)
                .append(".class")
                .toString();
    }
}
