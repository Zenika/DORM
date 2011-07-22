package com.zenika.dorm.core.dao.neo4j.util;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlRootElement
public class HelloWorld {

    public String getMessage() {
        return "Hello world !";
    }
}
