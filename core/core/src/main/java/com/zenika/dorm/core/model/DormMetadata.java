package com.zenika.dorm.core.model;


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

    private List<DormMetadata> metadataChildren;

    private Map<String, PluginMetadata> pluginMetadatas = new HashMap<String, PluginMetadata>();

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
        pluginMetadatas.put(metadata.getType(), metadata);
    }

    public boolean hasPlugin(String type){
        return pluginMetadatas.get(type) != null;
    }

    public PluginMetadata getPlugin(String type){
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
}