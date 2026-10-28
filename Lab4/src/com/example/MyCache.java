package com.example;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyCache<K, V> {

    private final int capacity;
    private final LinkedHashMap<K, V> map;

    public MyCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("The cache capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > MyCache.this.capacity;
            }
        };
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    public V get(Object key) {
        return map.get(key);
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public V remove(Object key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}