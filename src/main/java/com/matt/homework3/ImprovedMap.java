package com.matt.homework3;

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
@ThreadSafe
public class ImprovedMap<K, V>{
    private final Map<K, V> wrappedMap = new HashMap<>();

    public synchronized V put(K key, V value){
        return this.wrappedMap.put(key, value);
    }
    public synchronized int size() {
        return this.wrappedMap.size();
    }

}
