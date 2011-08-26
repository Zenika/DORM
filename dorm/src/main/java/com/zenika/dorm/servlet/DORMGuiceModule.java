package com.zenika.dorm.servlet;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.sql.DormDaoJdbc;
import com.zenika.dorm.core.exception.JDBCException;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.processor.impl.DefaultProcessor;
import com.zenika.dorm.core.processor.impl.DormProcessor;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.impl.DefaultDormRepository;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.impl.DefaultDormService;
import com.zenika.dorm.core.ws.provider.CoreExceptionMapper;
import com.zenika.dorm.core.ws.provider.DormProcessExceptionMapper;
import com.zenika.dorm.core.ws.resource.DormResource;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import com.zenika.dorm.maven.ws.resource.MavenResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DORMGuiceModule extends JerseyServletModule {

    private static final Logger LOG = LoggerFactory.getLogger(DORMGuiceModule.class);

    @Override
    protected void configureServlets() {

        LOG.info("Zenika DORM POC");
        LOG.info("Init guice module");

        bindResources();
        bindProcessor();
        bindService();
        bindDao();
        bindRepository();

        serve("/*").with(GuiceContainer.class);
    }

    private void bindResources() {
        // web services
        bind(DormResource.class);
        bind(MavenResource.class);

        // exception mappers
        bind(CoreExceptionMapper.class);
        bind(DormProcessExceptionMapper.class);

    }

    private void bindProcessor() {
        DefaultProcessor processor = new DefaultProcessor();
        processor.getExtensions().put(DefaultDormMetadataExtension.EXTENSION_NAME, new DormProcessor());
        processor.getExtensions().put(MavenMetadataExtension.EXTENSION_NAME, new MavenProcessor());
        bind(Processor.class).toInstance(processor);
    }

    private void bindService() {
        bind(DormService.class).to(DefaultDormService.class);
    }

    private void bindDao() {

        DataSource dataSource = null;

        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/postgres");
        } catch (NamingException e) {
            throw new JDBCException("Unable to find the datasource", e);
        }

        bind(DataSource.class).toInstance(dataSource);
        bind(DormDao.class).to(DormDaoJdbc.class);
    }

    private void bindRepository() {
        bind(DormRepository.class).to(DefaultDormRepository.class);
    }
}
