package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedHashSetTest {

    @Test
    @DisplayName("Заборонa дублікатів та збереження унікальності")
    void testUniqueElements() {
        MyLinkedHashSet<String> set = new MyLinkedHashSet<>();

        assertTrue(set.add("One"));
        assertFalse(set.add("One"));
        assertTrue(set.add("Two"));

        assertEquals(2, set.size());
        assertTrue(set.contains("One"));
    }

    @Test
    @DisplayName("Масове додавання елементів через Wildcard")
    void testAddAllWildcard() {
        MyLinkedHashSet<Number> set = new MyLinkedHashSet<>();
        Iterable<Integer> ints = Arrays.asList(1, 2, 2, 3);

        set.addAll(ints);

        assertEquals(3, set.size());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
        assertTrue(set.contains(3));
    }
}