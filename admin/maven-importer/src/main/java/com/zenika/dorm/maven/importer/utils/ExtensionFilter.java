package com.zenika.dorm.maven.importer.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ExtensionFilter implements FilenameFilter{

    private final static String[] extensions = {"jar", "sha1", "md5", "pom"};

    @Override
    public boolean accept(File file, String s) {
        return FilenameUtils.isExtension(s, extensions);
    }
}