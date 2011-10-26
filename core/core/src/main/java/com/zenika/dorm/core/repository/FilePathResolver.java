package com.zenika.dorm.core.repository;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class FilePathResolver {

    private String pattern;
    private String basePath;

    private Queue<File> fileQueue;

    public FilePathResolver(String pattern, String basePath) {
        this.pattern = pattern;
        this.basePath = basePath;
        fileQueue = new LinkedList<File>();
    }

    public Queue<File> resolveWithPattern() {
        File root = new File(basePath);
        resolveWithPattern(root);
        return fileQueue;
    }

    private void resolveWithPattern(File file){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File currentFile: files){
                if (currentFile.isDirectory()){
                    resolveWithPattern(currentFile);
                } else {
                    if (isMatchPattern(pattern, currentFile.getPath())){
                        fileQueue.add(currentFile);
                    }
                }
            }
        }
    }

    private boolean isMatchPattern(String pattern, String path) {
        for (String currentPattern: pattern.split("/")){
            if (currentPattern.equals("[*]")){
                continue;
            }
            boolean match = false;
            for (String currentPath: path.split("/")){
                if (currentPath.equals(currentPattern)){
                    match = true;
                    break;
                }
            }
            if (!match){
                return false;
            }
        }
        return true;
    }


}
