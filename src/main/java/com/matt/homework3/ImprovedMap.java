package com.matt.homework3;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
@ThreadSafe
public class ImprovedMap<K, V>{
    private final ConcurrentMap<K, V> wrappedMap;

    public ImprovedMap(Map<K, V> map){
        this.wrappedMap = new ConcurrentHashMap<>(map);
    }

    public V put(K key, V value){
        return this.wrappedMap.put(key, value);
    }
    public int size() {
        return this.wrappedMap.size();
    }

}
