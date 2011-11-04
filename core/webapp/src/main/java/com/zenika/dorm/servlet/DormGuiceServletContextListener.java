package com.zenika.dorm.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.guice.module.DormCoreModule;
import com.zenika.dorm.core.guice.module.DormRepositoryConfigurationModule;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryConfiguration;
import com.zenika.dorm.core.repository.impl.DormRepositoryPatternAssociate;
import com.zenika.dorm.guice.module.DormJerseyModule;
import com.zenika.dorm.maven.guice.module.MavenModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class DormGuiceServletContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        Set<AbstractModule> modules = new HashSet<AbstractModule>();
        addRepositoryModule(modules);
        modules.add(new DormJerseyModule());
        modules.add(new DormCoreModule());
        addDAOModule(modules);
        modules.add(new MavenModule());
        return Guice.createInjector(modules);
    }

    private void addRepositoryModule(Set<AbstractModule> modules) {
        Properties properties = new Properties();
        String repositoryClassStr = null;
        try {
            properties.load(this.getClass().getResourceAsStream("repository.properties"));
            repositoryClassStr = properties.getProperty("repository.class");
            Class<? extends DormRepository> repositoryClass = (Class<? extends DormRepository>) Class.forName(repositoryClassStr);
            String configFilePath = properties.getProperty("repository.config.file");
            modules.add(new DormRepositoryConfigurationModule(repositoryClass, configFilePath));
        } catch (IOException e) {
            throw new CoreException("Unable to read this properties file: repository.properties", e);
        } catch (ClassNotFoundException e) {
            throw new CoreException("Unable to find this class: " + repositoryClassStr, e);
        }
    }


    private void addDAOModule(Set<AbstractModule> modules) {
        try {
            Class<AbstractModule> guiceDAOClass = getDAOModuleClass();
            if (guiceDAOClass == null) {
                throw new CoreException("Can't load the Guice DAO module.");
            }
            modules.add(guiceDAOClass.newInstance());
        } catch (IOException ioe) {
            throw new CoreException(ioe);
        } catch (ClassNotFoundException cnfe) {
            throw new CoreException(cnfe);
        } catch (InstantiationException ie) {
            throw new CoreException(ie);
        } catch (IllegalAccessException iae) {
            throw new CoreException(iae);
        }
    }

    private Class<AbstractModule> getDAOModuleClass() throws IOException, ClassNotFoundException {

        String daoClassStr = System.getProperty("dao.class");
        if (daoClassStr == null) {
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("dao.properties"));
            daoClassStr = String.valueOf(properties.get("dao.class"));
        }

        if (daoClassStr != null) {
            Class<?> daoClass = Class.forName(daoClassStr);
            if (AbstractModule.class.isAssignableFrom(daoClass)) {
                return (Class<AbstractModule>) daoClass;
            }
        }

        return null;
    }

    public void contextInitialized
            (ServletContextEvent
                     servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        JULConfigurer.configureJULtoSLF4J(servletContext);
        super.contextInitialized(servletContextEvent);
    }

}