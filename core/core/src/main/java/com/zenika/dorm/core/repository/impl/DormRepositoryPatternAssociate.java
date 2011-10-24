package com.zenika.dorm.core.repository.impl;

import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryConfiguration;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import scala.reflect.Field;

import javax.inject.Inject;
import java.io.File;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormRepositoryPatternAssociate implements DormRepository {

    @Inject
    private ExtensionFactoryServiceLoader extensionFactoryServiceLoader;

    @Inject
    private DormRepositoryConfiguration configuration;

    public DormRepositoryPatternAssociate(){

    }

    public DormRepositoryPatternAssociate(DormRepositoryConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void store(String extension, String path, DormResource resource, boolean override) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public DormResource get(DormMetadata metadata) {
        ExtensionMetadataFactory extensionMetadataFactory = extensionFactoryServiceLoader.getInstanceOf(metadata.getType());
        File file = resolve(metadata.getName(), metadata.getVersion(), extensionMetadataFactory.toMap(metadata));
        return new DefaultDormResource(metadata.getName(), "jar", file);
    }

    @Override
    public void store(DormResource resource, DormMetadata metadata, DormServiceStoreResourceConfig config) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public File resolve(String name, String version, Map<String, String> properties) {
        String patternWithNameAndVersion = configuration.getPattern()
                .replace("{name}", name)
                .replace("{version}", version)
                .replace("{extension}", "jar");
        for (String property : configuration.getProperties()) {
            if (properties.containsKey(property)) {
                patternWithNameAndVersion = patternWithNameAndVersion.replace(
                        new StringBuilder(256)
                                .append("{")
                                .append(property)
                                .append("}")
                                .toString(),
                        properties.get(property)
                );
            }
        }
        File file = new File(configuration.getBasePath(), patternWithNameAndVersion);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }
}