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
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNodeComposite;
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

    /**
     * Cannot make this working
     *
     * Initial target was to call external extension only to obtain the origin
     * For example maven processor must only return the specific maven origin
     * And next the default processor will add theses origins to metadata and then to the node.
     *
     * But an extension can return single origin or multiples.
     * For example maven processor will always return 2 origins : the "entity / abstract" origin and the
     * specific origin with the type.
     * And processor X can return 10 origins with complex reliationship.
     *
     * But I can't modelize this.
     * The new push method (this one was renamed to oldPush) will call extension and get back a complete
     * DependencyNode.
     *
     * Required properties are at least :
     * - version
     *
     * @param origin     the push request's origin
     * @param properties the properties to push
     * @return the push status, succeed or failed
     */
    public Boolean pushOld(String origin, Map<String, String> properties) {

        ProcessorExtension extension = getExtension(origin);
        String version = properties.get("version");

        Map<DormOrigin, Set<DormOrigin>> origins = extension.getOrigins(properties);

        DependencyNode currentNode = null;
        DependencyNode previousNode = null;

        for (Iterator<Map.Entry<DormOrigin, Set<DormOrigin>>> it = origins.entrySet().iterator();
             it.hasNext(); ) {

            if (null != currentNode) {
                previousNode = currentNode;
            }

            Map.Entry<DormOrigin, Set<DormOrigin>> entry = it.next();
            currentNode = createNode(entry.getKey(), version);

            if (null != previousNode) {
                for (DependencyNode node : previousNode.getChildren()) {
                    if (node.equals(currentNode)) {
                        currentNode = node;
                    }
                }
            }

//            DormMetadata metadata = new DefaultDormMetadata(ve)

            for (Iterator<DormOrigin> setIt = entry.getValue().iterator(); setIt.hasNext(); ) {
                currentNode.addChild(createNode(setIt.next(), version));
            }
        }


//        DormOrigin rootOrigin = extension.getOrigins(properties);
//        DormMetadata metadata = new DefaultDormMetadata(properties.get("version"), rootOrigin);
//        Dependency dependency = new DefaultDependency(metadata);
//        DependencyNodeComposite node = new DefaultDependencyNodeComposite(dependency);
//
//
//
//        DormOrigin lastOrigin = rootOrigin;
//        DormOrigin childOrigin = null;
//
//        while ((childOrigin = extension.getOriginFromParent(properties, lastOrigin)) != null &&
//                !lastOrigin.equals(childOrigin)) {
//
//            DormMetadata childMetadata = new DefaultDormMetadata(metadata.getVersion(), childOrigin);
//            Dependency childDependency = new DefaultDependency(childMetadata);
//
//        }
//
//        // get metadata which will call getOrigin
//        DormMetadata metadata = getMetadata(origin, properties);
//
//        // create dependency from the metadata
//        Dependency dependency = new DefaultDependency(metadata);
//
//        // now he have metadata, we need the graph represented by the root node
//        DependencyNode root = getExtension(origin).getNode();
//
//        // now he need to know on which node we have to set the dependency
//        DependencyNode node = getExtension(origin).getDependencyNode();
//        node.setDependency(dependency);


//        return service.pushDependency(dependency);
        return false;
    }

    /**
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

    private DependencyNodeComposite createNode(DormOrigin origin, String version) {
        return new DefaultDependencyNodeComposite(new DefaultDependency(new
                DefaultDormMetadata(version, origin)));
    }

    public Map<String, ProcessorExtension> getExtensions() {
        return extensions;
    }

    @Override
    public RequestProcessor getRequestProcessor() {
        return requestProcessor;
    }
}
