package com.example;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyCache {

    private final int capacity;
    private final LinkedHashMap map;

    public MyCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("The cache capacity must be greater than 0");
    }
        this.capacity = capacity;
        this.map = new LinkedHashMap(capacity, 0.75f, true) {
          @Override
          protected boolean removeEldestEntry(Map.Entry eldest) {
              return size() > MyCache.this.capacity;
          }
        };
    }

    public void put(Object key, Object value) {
        map.put(key, value);
    }

    public Object get(Object key) {
        return map.get(key);
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public Object remove(Object key) {
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
