package com.zenika.dorm.core.guice.module;

import com.google.inject.AbstractModule;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryConfiguration;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormRepositoryConfigurationModule extends AbstractModule {

    private static final String DEFAULT_FILE_CONFIGURATION = "/com/zenika/dorm/core/repository/default_repository_configuration.json";

    private String configurationFile;
    private Class<? extends DormRepository> repositoryClass;

    public DormRepositoryConfigurationModule(Class<? extends DormRepository> repositoryClass, String configurationFile) {
        this.repositoryClass = repositoryClass;
        this.configurationFile = configurationFile;
    }

    public DormRepositoryConfigurationModule(Class<? extends DormRepository> repositoryClass) {
        this(repositoryClass, DEFAULT_FILE_CONFIGURATION);
    }

    @Override
    protected void configure() {
        URL url = getClass().getResource(configurationFile);
        ObjectMapper mapper = new ObjectMapper();
        try {
            DormRepositoryConfiguration configuration = mapper.readValue(url, DormRepositoryConfiguration.class);
            bind(DormRepositoryConfiguration.class).toInstance(configuration);
            bind(DormRepository.class).to(repositoryClass);
        } catch (IOException e) {
            throw new CoreException("Unable to read this json file: " + url, e);
        }
    }
}
