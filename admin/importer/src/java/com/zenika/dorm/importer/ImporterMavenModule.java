package com.zenika.dorm.importer;

import com.google.inject.AbstractModule;
import com.zenika.dorm.maven.guice.module.MavenModule;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ImporterMavenModule extends MavenModule{

    @Override
    protected void configure() {
        super.configure();
        bind(RepositoryImporter.class).to(MavenRepositoryImporter.class);
    }
}
