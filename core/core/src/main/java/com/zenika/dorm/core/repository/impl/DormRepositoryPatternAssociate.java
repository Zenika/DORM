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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
        String pattern = configuration.getPattern();
        addCommonsPropertiesInMap(name, version, extension, properties);
        pattern = computeAliasOption(pattern);
        pattern = computePropertiesInPattern(pattern, properties);
        File result;
        if (isRecursiveSearch(pattern)) {
            Queue<File> fileQueue = new FilePathResolver(pattern, configuration.getBasePath()).resolveWithPattern();
            if (fileQueue.size() > 1) {
                throw new CoreException("Unable to find a unique file with this pattern: " + configuration.getPattern());
            }
            result = fileQueue.poll();
        } else {
            result = new File(configuration.getBasePath(), pattern);
        }
        return result;
    }

    private Map<String, String> addCommonsPropertiesInMap(String name, String version, String extension, Map<String, String> properties) {
        properties.put("name", name);
        properties.put("version", version);
        properties.put("extension", extension);
        return properties;
    }

    private String computeAliasOption(String pattern) {
        Matcher matcher = Pattern.compile("\\[[A-Z-a-z-0-9.\\-+,?;.:§!%^¨$£{} ]+([\\{]|[, ])alias[: ]+[A-Z-a-z-0-9.\\-+,?;.:§!%^¨$£{} ]+\\}\\]").matcher(pattern);
        while (matcher.find()) {
            String result = matcher.group();
            String alias = getAlias(result);
            pattern = pattern.replace(getPropertyName(result), alias);
        }
        pattern = deleteAliasOptions(pattern);
        return pattern;
    }

    private String getAlias(String patternProperty) {
        Matcher matcher = Pattern.compile("(\\{|, |,)alias[: ]+[A-Z-a-z-0-9.\\-+?;.:§!%^¨$£{} ]+(\\}|,)").matcher(patternProperty);
        matcher.find();
        String alias = matcher.group();
        alias = alias.substring(1, alias.length() - 1);
        alias = deleteFirstBlankSpace(alias);
        alias = alias.substring("alias:".length());
        alias = deleteFirstBlankSpace(alias);
        return alias;
    }

    private String deleteAliasOptions(String pattern) {
        return pattern.replaceAll(":[ ]?\\{alias[: ]+[A-Z-a-z-0-9.\\-+?;.:§!%^¨$£{} ]+\\}", "")
                .replaceAll("(\\{|, |,)alias[: ]+[A-Z-a-z-0-9.\\-+?;.:§!%^¨$£{} ]+(?<!\\})", "");
    }

    private String deleteFirstBlankSpace(String string) {
        if (string.startsWith(" ")) {
            string = string.substring(1);
        }
        return string;
    }

    private String computePropertiesInPattern(String pattern, Map<String, String> mappedProperties) {
        Matcher matcherProperties = Pattern.compile("\\[[A-Z-a-z-0-9.\\-+,?;.:§!%^¨$£{} ]+\\]").matcher(pattern);
        while (matcherProperties.find()) {
            String result = matcherProperties.group();
            if (hasPropertyOptions(result)) {
                pattern = computeOptions(pattern, mappedProperties, result);
            } else {
                pattern = pattern.replace(result, mappedProperties.get(getPropertyName(result)));
            }
        }
        return pattern;
    }

    private String computeOptions(String pattern, Map<String, String> mappedProperties, String result) {
        String[] options = getOptions(result);
        String propertyValue = mappedProperties.get(getPropertyName(result));
        if (checkPropertyValue(result, propertyValue)){
            return pattern.replace(result, "");
        }
        for (String option : options) {
            if (isSeparatorOption(option)) {
                String separator = getSeparator(option);
                propertyValue = propertyValue.replace(separator, "/");
            } else if (isPrefixOption(option)) {
                String prefix = getPrefix(option);
                propertyValue = prefix + propertyValue;
            } else {
                throw new CoreException("Unsupported option: " + option);
            }
        }
        return pattern.replace(result, propertyValue);
    }

    private boolean checkPropertyValue(String result, String propertyValue) {
        if (propertyValue == null || propertyValue.isEmpty()) {
            if (hasOptionalOption(result)) {
                return true;
            } else {
                throw new CoreException("The property value associated with this pattern property: " + result + " doesn't found. Try to add \"optionally\" option to pattern property");
            }
        }
        return false;
    }

    private boolean hasOptionalOption(String result) {
        return result.contains("optionally");
    }

    private String getPrefix(String option) {
        option = option.substring("prefix:".length());
        if (option.startsWith(" ")) {
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
        String[] options = exclude(result.split(", "), new String[]{"optionally"});
        return options;
    }

    private String[] exclude(String[] split, String[] excludes) {
        List<String> options = new ArrayList<String>(Arrays.asList(split));
        options.removeAll(Arrays.asList(excludes));
        String[] optionsTab = new String[options.size()];
        return options.toArray(optionsTab);
    }

    private String getSeparator(String option) {
        option = option.substring("separator:".length());
        if (option.contains(" ")) {
            return option.substring(1);
        }
        return option;
    }

    private String getPropertyName(String property) {
        Matcher matcher;
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