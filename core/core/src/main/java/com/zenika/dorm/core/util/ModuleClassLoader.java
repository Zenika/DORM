package com.zenika.dorm.core.util;

import javax.print.DocFlavor;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ModuleClassLoader extends ClassLoader {

    private String basePath;

    @Override
    public Class findClass(String name) {
        byte[] b = loadClassData(name);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassData(String name) {
        
        return new byte[0];  //To change body of created methods use File | Settings | File Templates.
    }


}
