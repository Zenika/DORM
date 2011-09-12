package com.zenika.dorm.servlet;

import javax.servlet.ServletContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JULConfigurer {

    /**
     * Parameter specifying if JUL java.util.logging.ConsoleHandler must be removed before adding
     * SLF4JBridgeHandler.
     */
    public static final String REMOVE_JUL_CONSOLEHANDLER_PARAM = "slf4jRemoveJULConsoleHandler";

    /**
     * Comma separated list of JUL logger names which will be mapped with SLF4J Bridge
     */
    public static final String BRIDGED_JUL_LOGGERS_PARAM = "slf4jBridgedJULLoggers";

    protected static void configureJULtoSLF4J(ServletContext servletContext) {
        // try to run org.slf4j.bridge.SLF4JBridgeHandler.install();
        try {
            // Try to locate class and method using current classloader
            Class<?> clazz = Class.forName("org.slf4j.bridge.SLF4JBridgeHandler", true,
                    JULConfigurer.class.getClassLoader());
            Method installMethod = clazz.getDeclaredMethod("install", (Class[]) null);

            // Check if JUL to SLF4J is already configured in current classloader
            if (isJULtoSLF4JAlreadyConfigured(clazz)) {
                return;
            }

            // Method is found, check if we must clean previous JUL handlers.
            Boolean removeConsoleHandler = Boolean.valueOf(servletContext.getInitParameter(REMOVE_JUL_CONSOLEHANDLER_PARAM));
            if (removeConsoleHandler == Boolean.TRUE) {
                removeJULConsoleHandler(servletContext);
            }

            installMethod.invoke(null, (Object[]) null);

            addSLF4JHandlerToLoggers(servletContext, clazz);
            servletContext.log("jul-to-slf4j successfully initialized");
        } catch (ClassNotFoundException e) {
            servletContext.log("jul-to-slf4j is disabled : org.slf4j.bridge.SLF4JBridgeHandler not found in classpath");
        } catch (NoSuchMethodException e) {
            servletContext.log("jul-to-slf4j is disabled : org.slf4j.bridge.SLF4JBridgeHandler.install() method not found. "
                    + "Check your jul-to-slf4j version");
        } catch (SecurityException e) {
            throw new RuntimeException("Unexpected error while trying to configure jul-to-slf4j", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unexpected error while trying to configure jul-to-slf4j", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unexpected error while trying to configure jul-to-slf4j", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unexpected error while trying to configure jul-to-slf4j", e);
        }
    }

    /**
     * Add SLF$J Bridge Handler to each logger declared in BRIDGED_JUL_LOGGERS_PARAM param.
     * This prevents log events to reach ROOT logger, as we don't want to log to ConsoleHandler
     * which is associated with ROOT logger by default.
     *
     * @param servletContext
     * @param bridgeHandlerClass
     */
    protected static void addSLF4JHandlerToLoggers(ServletContext servletContext, Class<?> bridgeHandlerClass) {
        String loggerList = servletContext.getInitParameter(BRIDGED_JUL_LOGGERS_PARAM);
        Handler bridgHandler = getSLF4JBridgeHandler(bridgeHandlerClass);
        if (loggerList != null) {
            String[] loggerNames = loggerList.split(",");
            for (String loggerName : loggerNames) {
                Logger logger = Logger.getLogger(loggerName);
                logger.addHandler(bridgHandler);
                logger.setUseParentHandlers(false);
                servletContext.log("SLF4JBridgeHandler added JUL logger '" + loggerName + "'");
            }
        }
    }

    protected static Handler[] getJULRootLoggerHandlers() {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        return rootLogger.getHandlers();
    }

    /**
     * Checks if a brigde is already configured using Class.equals(). This implies that several
     * brigdes may be present, but only one per classloader.
     *
     * @param bridgeHandlerClass
     * @return
     */
    protected static boolean isJULtoSLF4JAlreadyConfigured(Class<?> bridgeHandlerClass) {
        return getSLF4JBridgeHandler(bridgeHandlerClass) != null;
    }

    protected static Handler getSLF4JBridgeHandler(Class<?> bridgeHandlerClass) {
        Handler[] handlers = getJULRootLoggerHandlers();
        if (handlers != null) {
            for (Handler handler : handlers) {
                if (handler.getClass().equals(bridgeHandlerClass)) {
                    return handler;
                }
            }
        }
        return null;
    }


    /**
     * This method allows to remove java.util.logging.ConsoleHandler from JUL rootLogger. We need to
     * do this programmaticaly since we don't have access to logging.properties in JVM installation.
     */
    protected static void removeJULConsoleHandler(ServletContext servletContext) {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers != null) {
            for (Handler handler : handlers) {
                if (handler instanceof ConsoleHandler) {
                    servletContext.log("Removing java.util.logging.ConsoleHandler to avoid System.err output");
                    rootLogger.removeHandler(handler);
                }
            }
        }
    }

}
