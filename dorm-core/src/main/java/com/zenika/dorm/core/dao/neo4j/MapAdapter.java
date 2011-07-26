package com.zenika.dorm.core.dao.neo4j;

import sun.security.krb5.internal.KdcErrException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlSeeAlso({MapAdapter.Adapter.class, MapAdapter.MapElement.class})
public class MapAdapter<K, V> extends XmlAdapter<MapAdapter.Adapter<K, V>, Map<K, V>> {

    @Override
    public Map<K, V> unmarshal(Adapter<K, V> kvAdapter) throws Exception {
        System.out.println(kvAdapter);
        List<MapElement<K, V>> mapElements = kvAdapter.getItem();
        System.out.println(mapElements);
        return null;
    }

    @Override
    public Adapter<K, V> marshal(Map<K, V> kvMap) throws Exception {
        if (kvMap == null) {
            return null;
        }
        return new Adapter<K, V>(kvMap);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Adapter", namespace = "MapAdapter")
    public static final class Adapter<K, V> {
        List<MapElement<K, V>> item;

        public Adapter() {

        }

        public Adapter(Map<K, V> map) {
            item = new ArrayList<MapElement<K, V>>(map.size());
            for (Map.Entry<K, V> entry : map.entrySet()) {
                item.add(new MapElement<K, V>(entry));
            }
        }

        public List<MapElement<K, V>> getItem() {
            return item;
        }

        public void setItem(List<MapElement<K, V>> item){
            this.item = item;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "MapElement", namespace = "MapAdapter")
    public static final class MapElement<K, V> {
        @XmlAnyElement
        private K key;
        @XmlAnyElement
        private V value;

        public MapElement() {

        }

        public MapElement(Map.Entry<K, V> entry) {
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}