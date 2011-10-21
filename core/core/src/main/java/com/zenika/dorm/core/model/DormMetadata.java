package com.zenika.dorm.core.model;


import com.zenika.dorm.core.model.impl.Usage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Extension point on the model to add specific metadatas
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormMetadata {

    private final Long id;
    private String name;
    private String version;
    private DerivedObject derivedObject;
    private Usage usage;

    private List<DormMetadata> metadataChildren = new ArrayList<DormMetadata>();

    /**
     * TODO: See with Gregory if is better to use class as key for the map :
     * private Map<String, PluginMetadata> pluginMetadatas = new HashMap<String, PluginMetadata>();
     * private Map<Class<? extends PluginMetadata>, ? extends PluginMetadata> pluginMetadatas = new HashMap<Class<? extends PluginMetadata>, PluginMetadata>();
     */
    private PluginHashMap pluginMetadatas = new PluginHashMap();

    public DormMetadata() {
        id = null;
    }

    public DormMetadata(Long id) {
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public String getVersion(){
        return this.version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void addPluginMetadata(PluginMetadata metadata) {
        pluginMetadatas.put(metadata.getClass(), metadata);
    }

    public boolean hasPlugin(Class type){
        return pluginMetadatas.get(type) != null;
    }

    public <T extends PluginMetadata> T getPlugin(Class<T> type){
        return pluginMetadatas.get(type);
    }

    public boolean hasDerivedObject(){
        return derivedObject != null;
    }

    public DerivedObject getDerivedObject() {
        return derivedObject;
    }

    public void setDerivedObject(DerivedObject derivedObject) {
        this.derivedObject = derivedObject;
    }

    public void addChild(DormMetadata dormMetadata){
        this.metadataChildren.add(dormMetadata);
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }
}