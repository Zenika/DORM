package com.zenika.dorm.core.repository.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;
import com.zenika.dorm.core.model.impl.DefaultDormResource;
import com.zenika.dorm.core.repository.CompositeProperties;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.repository.DormRepositoryConfiguration;
import com.zenika.dorm.core.repository.FilePathResolver;
import com.zenika.dorm.core.service.config.DormServiceStoreResourceConfig;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
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
        String patternWithNameAndVersion = configuration.getPattern();
        patternWithNameAndVersion = patternWithNameAndVersion
                .replaceAll("\\[name\\]", name)
                .replaceAll("\\[version\\]", version)
                .replaceAll("\\[extension\\]", extension);
        patternWithNameAndVersion = computePropertiesInPattern(patternWithNameAndVersion, properties);
        File result = null;
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


//    private File resolve(String name, String version, String extension, Map<String, String> properties) {
//        String patternWithNameAndVersion = configuration.getPattern();
//        patternWithNameAndVersion = patternWithNameAndVersion
//                .replaceAll("\\[name\\]", name)
//                .replaceAll("\\[version\\]", version)
//                .replaceAll("\\[extension\\]", extension);
//        patternWithNameAndVersion = setSimpleProperties(properties, patternWithNameAndVersion);
//        patternWithNameAndVersion = setCompositeProperties(properties, patternWithNameAndVersion);
//        if (isRecursiveSearch(patternWithNameAndVersion)) {
//            patternWithNameAndVersion = searchPlace(patternWithNameAndVersion, properties);
//        }
//        File file = new File(configuration.getBasePath(), patternWithNameAndVersion);
//        return file;
//    }

    // \[[A-Z-a-z-0-9.\-+,?;.:§!*%^¨$£{} ]+\]
    public String searchPlace(String patternWithNameAndVersion, Map<String, String> mappedProperties) {
        String[] paths = patternWithNameAndVersion.split("\\[\\*\\]");
        paths[0] = new StringBuilder(256)
                .append(configuration.getBasePath())
                .append(paths[0])
                .toString();
        for (int i = 0; i < paths.length - 1; i++) {
            File[] folders = new File(paths[i]).listFiles();
            String computedPath = computePropertiesInPattern(paths[0], mappedProperties);
            String[] splitPath = computedPath.split("/");
            for (File folder : folders) {

            }
        }

        return null;
    }

    private String computePropertiesInPattern(String path, Map<String, String> mappedProperties) {
        Matcher matcherProperties = Pattern.compile("\\[[A-Z-a-z-0-9.\\-+,?;.:§!%^¨$£{} ]+\\]").matcher(path);

        while (matcherProperties.find()) {
            String result = matcherProperties.group();
            if (hasPropertyOption(result)) {
                if (isSeparatorOption(result)) {
                    String separator = getSeparator(result);
                    String propertyValue = mappedProperties.get(getPropertyName(result));
                    path = path.replace(result, propertyValue.replace(separator, "/"));
                }
            } else {
                path = path.replace(result, mappedProperties.get(getPropertyName(result)));
            }
        }

        return path;
    }

    private String getSeparator(String property) {
        Matcher matcher = Pattern.compile("\\{[A-Z-a-z-0-9.\\-+,?;.:§!*%^¨$£{} ]+\\}").matcher(property);
        matcher.find();
        String result = matcher.group();
        result = result.substring(10, result.length() - 1);
        int startIndex = 1;
        if (result.contains(": ")) {
            startIndex = 2;
        }
        return result.substring(startIndex);
    }

    private String getPropertyName(String property) {
        Matcher matcher = null;
        if (hasPropertyOption(property)) {
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

    private boolean hasPropertyOption(String property) {
        Matcher matcher = Pattern.compile("[: ]+\\{[A-Z-a-z-0-9.\\-+,?;.:§!*%^¨$£{} ]+\\}").matcher(property);
        return matcher.find();
    }

    private String findFolder(String folder) {
        Matcher matcher = Pattern.compile("^/[A-Z-a-z-0-9.\\-+,?;.:§!*%^¨$£]+/").matcher(folder);
        if (matcher.find()) {
            return matcher.group();
        } else {
            throw new CoreException("Unable to find a folder to test in this part of pattern: " + folder);
        }
    }

    private boolean isRecursiveSearch(String patternWithNameAndVersion) {
        return patternWithNameAndVersion.contains("[*]");
    }


    private Map<String, String> getPropertiesMap(DormMetadata dormMetadata) {
        ExtensionMetadataFactory extensionMetadataFactory = extensionFactoryServiceLoader.getInstanceOf(dormMetadata.getType());
        return extensionMetadataFactory.toMap(dormMetadata);
    }

//    private String setSimpleProperties(Map<String, String> properties, String patternWithNameAndVersion) {
//        for (String property : configuration.getProperties()) {
//            if (properties.containsKey(property)) {
//                patternWithNameAndVersion = patternWithNameAndVersion.replace(
//                        new StringBuilder(256)
//                                .append("[")
//                                .append(property)
//                                .append("]")
//                                .toString(),
//                        properties.get(property)
//                );
//            }
//        }
//        return patternWithNameAndVersion;
//    }

//    private String setCompositeProperties(Map<String, String> properties, String patternWithNameAndVersion) {
//        if (configuration.hasCompositeProperties()) {
//            CompositeProperties compositeProperties = configuration.getCompositeProperties();
////            for (String property : compositeProperties.getProperty()) {
////                patternWithNameAndVersion = patternWithNameAndVersion.replace(
////                        new StringBuilder(256)
////                                .append("[")
////                                .append(property)
////                                .append("]")
////                                .toString(),
////                        properties.get(property).replace(compositeProperties.getSeparator(), "/")
////                );
////            }
//        }
//        return patternWithNameAndVersion;
//    }
}