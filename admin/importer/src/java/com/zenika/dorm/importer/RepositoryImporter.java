package com.zenika.dorm.importer;

import com.google.inject.Inject;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public abstract class RepositoryImporter {

    @Inject
    private ImportConfiguration configuration;

    public abstract void startImport();
}
