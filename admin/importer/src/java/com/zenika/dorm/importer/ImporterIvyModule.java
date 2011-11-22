package com.zenika.dorm.importer;

import com.zenika.dorm.maven.guice.module.MavenModule;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ImporterIvyModule extends MavenModule {

    @Override
    protected void configure() {
        super.configure();
        bind(RepositoryImporter.class).to(IvyRepositoryImporter.class);
    }
}
