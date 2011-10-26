package com.zenika.dorm.core.repository.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryConfiguration;
import com.zenika.dorm.core.repository.FilePathResolver;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormRepositoryPatternAssociate implements DormRepository {

    @Inject
    private ExtensionFactoryServiceLoader extensionFactoryServiceLoader;

    @Inject
    private DormRepositoryConfiguration configuration;

    public DormRepositoryPatternAssociate() {

    }

    public DormRepositoryPatternAssociate(DormRepositoryConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void store(String extension, String path, DormResource resource, boolean override) {
        throw new UnsupportedOperationException("Not implement yet");
    }

    @Override
    public DormResource get(DormMetadata metadata) {
        File file = resolve(metadata.getName(), metadata.getVersion(), "jar", getPropertiesMap(metadata));
        if (file.exists()) {
            return new DefaultDormResource(metadata.getName(), "jar", file);
        } else {
            return null;
        }
    }

    @Override
    public void store(DormResource resource, DormMetadata metadata, DormServiceStoreResourceConfig config) {
        File file = resolve(metadata.getName(), metadata.getVersion(), resource.getExtension(), getPropertiesMap(metadata));
        try {
            FileUtils.copyFile(resource.getFile(), file);
        } catch (IOException e) {
            throw new CoreException("Unable to copy file " + resource.getFile() + " to " + file, e);
        }
    }

    private File resolve(String name, String version, String extension, Map<String, String> properties) {
        String patternWithNameAndVersion = configuration.getPattern();
        patternWithNameAndVersion = patternWithNameAndVersion
                .replaceAll("\\[name\\]", name)
                .replaceAll("\\[version\\]", version)
                .replaceAll("\\[extension\\]", extension);
        patternWithNameAndVersion = computePropertiesInPattern(patternWithNameAndVersion, properties);
        File result;
        if (isRecursiveSearch(patternWithNameAndVersion)) {
            Queue<File> fileQueue = new FilePathResolver(patternWithNameAndVersion, configuration.getBasePath()).resolveWithPattern();
            if (fileQueue.size() > 1) {
                throw new CoreException("Unable to find a unique file with this pattern: " + configuration.getPattern());
            }
            result = fileQueue.poll();
        } else {
            result = new File(configuration.getBasePath(), patternWithNameAndVersion);
        }
        return result;
    }

    private String computePropertiesInPattern(String path, Map<String, String> mappedProperties) {
        Matcher matcherProperties = Pattern.compile("\\[[A-Z-a-z-0-9.\\-+,?;.:§!%^¨$£{} ]+\\]").matcher(path);

        while (matcherProperties.find()) {
            String result = matcherProperties.group();
            if (hasPropertyOptions(result)) {
                path = computeOptions(path, mappedProperties, result);
            } else {
                path = path.replace(result, mappedProperties.get(getPropertyName(result)));
            }
        }

        return path;
    }

    private String computeOptions(String path, Map<String, String> mappedProperties, String result) {
        String[] options = getOptions(result);
        for (String option : options) {
            if (isSeparatorOption(option)) {
                String separator = getSeparator(option);
                String propertyValue = mappedProperties.get(getPropertyName(result));
                path = path.replace(result, propertyValue.replace(separator, "/"));
            } else if (isPrefixOption(option)){
                String prefix = getPrefix(option);
                String propertyValue = mappedProperties.get(getPropertyName(result));
                path = prefix + path.replace(result, propertyValue);
            } else if (isOptional(option)){
                if (mappedProperties.get(getPropertyName(result)) == null){
                    path = "";
                } else {
                    path = path.replace(result, mappedProperties.get(getPropertyName(result)));
                }
            }
        }
        return path;
    }

    private boolean isOptional(String option) {
        return option.contains("option");
    }

    private String getPrefix(String option) {
        option = option.substring("prefix:".length());
        if (option.startsWith(" ")){
            return option.substring(1);
        } else {
            return option;
        }
    }

    private boolean isPrefixOption(String option) {
        return option.contains("prefix");
    }

    private String[] getOptions(String property) {
        Matcher matcher = Pattern.compile("\\{[A-Z-a-z-0-9.\\-+,?;.:§!*%^¨$£{} ]+\\}").matcher(property);
        matcher.find();
        String result = matcher.group();
        result = result.substring(1, result.length() - 1);
        return result.split(", ");
    }

    private String getSeparator(String option) {
        option = option.substring("separator:".length());
        if (option.contains(" ")) {
            return option.substring(1);
        }
        return option;
    }

    private String getPropertyName(String property) {
        Matcher matcher = null;
        if (hasPropertyOptions(property)) {
            matcher = Pattern.compile("\\[[A-Z-a-z-0-9.\\-+,?;.§!*%^¨$£{} ]+:").matcher(property);
        } else {
            matcher = Pattern.compile("\\[[A-Z-a-z-0-9.\\-+,?;.§!*%^¨$£{} ]+\\]").matcher(property);
        }
        matcher.find();
        String result = matcher.group();
        return result.substring(1, result.length() - 1);
    }

    private boolean isSeparatorOption(String property) {
        return property.contains("separator");
    }

    private boolean hasPropertyOptions(String property) {
        Matcher matcher = Pattern.compile("[: ]+\\{[A-Z-a-z-0-9.\\-+,?;.:§!*%^¨$£{} ]+\\}").matcher(property);
        return matcher.find();
    }

    private boolean isRecursiveSearch(String patternWithNameAndVersion) {
        return patternWithNameAndVersion.contains("[*]");
    }


    private Map<String, String> getPropertiesMap(DormMetadata dormMetadata) {
        ExtensionMetadataFactory extensionMetadataFactory = extensionFactoryServiceLoader.getInstanceOf(dormMetadata.getType());
        return extensionMetadataFactory.toMap(dormMetadata);
    }

}