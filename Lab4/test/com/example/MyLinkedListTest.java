package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    private MyList<Number> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }

    @Test
    @DisplayName("Додавання елементів та перевірка порядку")
    void testAddAndOrder() {
        list.add(1);
        list.add(2);
        list.add(1, 15);

        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(15, list.get(1));
        assertEquals(2, list.get(2));
    }

    @Test
    @DisplayName("Перевірка Wildcard в addAll з індексом")
    void testAddAllWildcardWithIndex() {
        list.add(100);
        list.add(400);

        List<Integer> middleElements = Arrays.asList(200, 300);
        list.addAll(1, middleElements);

        assertEquals(4, list.size());
        assertEquals(100, list.get(0));
        assertEquals(200, list.get(1));
        assertEquals(300, list.get(2));
        assertEquals(400, list.get(3));
    }

    @Test
    @DisplayName("Пошук індексу елемента (indexOf)")
    void testIndexOf() {
        list.add(10);
        list.add(20);

        assertEquals(1, list.indexOf(20));
        assertEquals(-1, list.indexOf(999));
    }
}