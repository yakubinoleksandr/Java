package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyCacheTest {

    @Test
    @DisplayName("Перевірка LRU витіснення та типи Ключ/Значення")
    void testEvictionAndGenerics() {
        MyCache<String, Integer> cache = new MyCache<>(2);

        cache.put("A", 1);
        cache.put("B", 2);

        cache.get("A");

        cache.put("C", 3);

        assertEquals(2, cache.size());
        assertNotNull(cache.get("A"));
        assertNull(cache.get("B"));
        assertEquals(3, cache.get("C"));
    }

    @Test
    @DisplayName("Перевірка Wildcard у putAll")
    void testPutAllWildcard() {
        MyCache<CharSequence, Number> cache = new MyCache<>(5);

        Map<String, Integer> sourceMap = new HashMap<>();
        sourceMap.put("Key1", 100);
        sourceMap.put("Key2", 200);

        cache.putAll(sourceMap);

        assertEquals(2, cache.size());
        assertEquals(100, cache.get("Key1"));
    }
}