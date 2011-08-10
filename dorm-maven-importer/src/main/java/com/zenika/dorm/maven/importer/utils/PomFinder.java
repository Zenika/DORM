package com.zenika.dorm.maven.importer.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class PomFinder extends SimpleFileVisitor<Path> {

    private static final Logger LOG = LoggerFactory.getLogger(PomFinder.class);

    private final PathMatcher matcher;
    private List<Path> files;

    public PomFinder(){
        matcher = FileSystems.getDefault().getPathMatcher("glob:*.pom");
        files = new ArrayList<Path>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)){
            files.add(file);
        }
        return FileVisitResult.CONTINUE;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        LOG.error("Path Error" + exc);
        return FileVisitResult.CONTINUE;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Path> getFiles() {
        return files;
    }
}
