package com.zenika.dorm.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class GuiceServlet extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new GuiceModule());
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        JULConfigurer.configureJULtoSLF4J(servletContext);
        super.contextInitialized(servletContextEvent);
    }

}
