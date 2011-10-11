package com.zenika.dorm.maven.test.module;

import com.google.inject.AbstractModule;
import com.zenika.dorm.maven.provider.ProxyWebResourceWrapper;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenTestModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(ProxyWebResourceWrapper.class);
    }
}
