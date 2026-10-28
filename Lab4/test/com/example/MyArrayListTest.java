package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {

    private MyList<Number> list;

    @BeforeEach
    void setUp() {
        list = new MyArrayList<>();
    }

    @Test
    @DisplayName("Додавання та отримання елементів базового типу")
    void testAddAndGet() {
        list.add(10);
        list.add(20.5);

        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20.5, list.get(1));
    }

    @Test
    @DisplayName("Перевірка Wildcard в addAll(Iterable<? extends E>)")
    void testAddAllWildcardIterable() {
        List<Integer> integers = Arrays.asList(1, 2, 3);
        List<Double> doubles = Arrays.asList(4.1, 5.2);

        list.addAll(integers);
        list.addAll(doubles);

        assertEquals(5, list.size());
        assertEquals(1, list.get(0));
        assertEquals(5.2, list.get(4));
    }

    @Test
    @DisplayName("Видалення елемента за індексом")
    void testRemove() {
        list.add(10);
        list.add(20);
        list.add(30);

        Number removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(30, list.get(1));
    }

    @Test
    @DisplayName("Перевірка toArray(T[] a)")
    void testToArrayGeneric() {
        MyList<String> stringList = new MyArrayList<>();
        stringList.add("A");
        stringList.add("B");

        String[] array = stringList.toArray(new String[0]);

        assertEquals(2, array.length);
        assertArrayEquals(new String[]{"A", "B"}, array);
    }
}