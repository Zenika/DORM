package com.zenika.dorm.importer;

import com.google.inject.Inject;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public abstract class RepositoryImporter {

    @Inject
    protected ImportConfiguration configuration;

    private long time;
    protected int numberOfImport = 0;

    public final void startImport() {
        time = System.currentTimeMillis();
        importProcess();
        time = System.currentTimeMillis() - time;
    }

    protected abstract void importProcess();

    public long getTime() {
        return time;
    }

    public int getNumberOfImport() {
        return numberOfImport;
    }
}