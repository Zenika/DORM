package com.zenika.dorm.core.processor.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.impl.DefaultDormFile;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.processor.RequestProcessor;
import com.zenika.dorm.core.service.DormService;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Main processor which delegate to the appropriate extension and then call the service to interact with
 * stored dependencies in the persistence layer and/or the file system repository
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultProcessor implements Processor {

    /**
     * Extensions are injected in the guice module
     */
    private Map<String, ProcessorExtension> extensions = new HashMap<String, ProcessorExtension>();

    @Inject
    private RequestProcessor requestProcessor;

    @Inject
    private DormService service;

    @Override
    public Boolean push(DormRequest request) {

        if (null == request) {
            throw new CoreException("Properties are null or incomplete");
        }

        DependencyNode node = getExtension(request.getOrigin()).push(request);

        return service.pushNode(node);
    }

    /**
     * @param origin
     * @param properties
     * @param file       may be null
     * @return
     * @deprecated
     */
    @Override
    public Boolean push(String origin, Map<String, String> properties, File file) {

        ProcessorExtension extension = getExtension(origin);
        DependencyNode root = extension.getOriginAsNode(properties);

        // get the main dependency node
        Dependency dependency = root.getDependency();

        if (null != file) {
            DormFile dormFile = getFile(file, properties);
            dependency.setFile(dormFile);
        }

        DependencyNode parent = getParentNode(properties, extension);

        if (null != parent) {
            parent.addChild(root);
            root = parent;
        }

        return service.pushNode(root);
    }

    /**
     * Not needed right now
     *
     * @param file
     * @param properties
     */
//    public void mapMainProperties(Dependency dependency, DependencyNode root,
//                                  Map<String, String> properties) {
//
//        // set usage for simple unique dependency
//        if (null != properties.get("usage") && root.getChildren().isEmpty()) {
//            dependency.setUsage(new Usage(properties.get("usage")));
//        }
//    }

    /**
     * @param file
     * @param properties
     */
    public DormFile getFile(File file, Map<String, String> properties) {

        if (null == properties.get("filename")) {
            throw new CoreException("File exists but filename is missing");
        }

        return new DefaultDormFile(properties.get("filename"), file);
    }

    public DependencyNode getParentNode(Map<String, String> properties, ProcessorExtension extension) {

        Usage usage = Usage.create(properties.get("usage"));

        DormOrigin origin;

        try {
            origin = extension.getParentOrigin(properties);
        } catch (UnsupportedOperationException e) {
            return null;
        }

        DormMetadata metadata = new DefaultDormMetadata(properties.get("version"), origin);

        DependencyNode root = service.getByMetadata(metadata, usage);

        return root;
    }

     * Get extension processor from the origin name
     * Extensions are injected to the processor in the guice module config
     *
     * @param origin
     * @return the extension corresponding to the origin
     */
    private ProcessorExtension getExtension(String origin) {

        ProcessorExtension extension = extensions.get(origin);

        if (null == extension) {
            throw new CoreException("Extension " + origin + " not found");
        }

        return extension;
    }

    public Map<String, ProcessorExtension> getExtensions() {
        return extensions;
    }

    @Override
    public RequestProcessor getRequestProcessor() {
        return requestProcessor;
    }
}
