package com.zenika.dorm.core.processor.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNodeComposite;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.processor.Processor;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.service.DormService;

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
    private DormService service;

    public Boolean push(DormMetadata metadata) {
//        Dependency dependency = getExtension(metadata).push(metadata);
//        return service.pushDependency(dependency);
        return null;
    }

    @Override
    public Boolean push(String origin, Map<String, String> properties) {
        return null;
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
                for (DependencyNode node : previousNode.getChildrens()) {
                    if (node.equals(currentNode)) {
                        currentNode = node;
                    }
                }
            }

//            DormMetadata metadata = new DefaultDormMetadata(ve)

            for (Iterator<DormOrigin> setIt = entry.getValue().iterator(); setIt.hasNext(); ) {
                currentNode.addChildren(createNode(setIt.next(), version));
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

    private DormMetadata getMetadata(String origin, Map<String, String> properties) {

        if (null == origin) {
            throw new CoreException("origin is missing");
        }

        DormOrigin dormOrigin = getExtension(origin).getOrigin(properties);
        return new DefaultDormMetadata(properties.get("version"), dormOrigin);
    }

    private DependencyNodeComposite createNode(DormOrigin origin, String version) {
        return new DefaultDependencyNodeComposite(new DefaultDependency(new
                DefaultDormMetadata(version, origin)));
    }

    public Map<String, ProcessorExtension> getExtensions() {
        return extensions;
    }
}
