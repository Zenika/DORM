package com.zenika.dorm.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.zenika.dorm.guice.module.DormCoreJdbcModule;
import com.zenika.dorm.guice.module.DormCoreModule;
import com.zenika.dorm.guice.module.DormJerseyModule;
import com.zenika.dorm.maven.guice.module.MavenModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormGuiceServletContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

        Set<AbstractModule> modules = new HashSet<AbstractModule>();
        modules.add(new DormJerseyModule());
        modules.add(new DormCoreModule());
        modules.add(new DormCoreJdbcModule());
        modules.add(new MavenModule());

        return Guice.createInjector(modules);
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        JULConfigurer.configureJULtoSLF4J(servletContext);
        super.contextInitialized(servletContextEvent);
    }

}
