package com.zenika.dorm.core.model;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class PluginHashMap implements Map<Class<? extends PluginMetadata>, PluginMetadata> {

    private Map<Class<? extends PluginMetadata>, PluginMetadata> map;

    public PluginHashMap() {
        super();
        map = new HashMap<Class<? extends PluginMetadata>, PluginMetadata>();
    }


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return map.containsValue(o);
    }

    @Override
    public PluginMetadata get(Object o) {
        return map.get(o);
    }

    public <T extends PluginMetadata> T get(Class<T> o){
        return (T) map.get(o);
    }

    @Override
    public PluginMetadata put(Class<? extends PluginMetadata> pluginMetadataClass, PluginMetadata metadata) {
        return map.put(pluginMetadataClass, metadata);
    }

    @Override
    public PluginMetadata remove(Object o) {
        return map.remove(o);
    }

    @Override
    public void putAll(Map<? extends Class<? extends PluginMetadata>, ? extends PluginMetadata> map) {
        map.putAll(new HashMap(map));
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Class<? extends PluginMetadata>> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<PluginMetadata> values() {
        return map.values();
    }

    @Override
    public Set<Entry<Class<? extends PluginMetadata>, PluginMetadata>> entrySet() {
        return map.entrySet();
    }
}
