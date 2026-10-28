package com.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 1. Демонстрація MyArrayList & Wildcards ===");
        MyList<Number> numberList = new MyArrayList<>();

        Integer[] intArray = {1, 2, 3};
        numberList.addAll(intArray);

        List<Double> doubleList = Arrays.asList(4.5, 5.5);
        numberList.addAll(doubleList);

        System.out.println("Елементи numberList (MyArrayList):");
        for (int i = 0; i < numberList.size(); i++) {
            System.out.println(" - " + numberList.get(i));
        }

        System.out.println("\n=== 2. Демонстрація MyLinkedList & Wildcards ===");
        MyList<Number> linkedList = new MyLinkedList<>();
        linkedList.addAll(intArray);
        linkedList.addAll(doubleList);

        System.out.println("Елементи linkedList (MyLinkedList):");
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(" - " + linkedList.get(i));
        }

        System.out.println("\n=== 3. Демонстрація MyLinkedHashSet ===");
        MyLinkedHashSet<String> set = new MyLinkedHashSet<>();
        set.add("Java");
        set.add("Generics");
        set.add("Wildcards");
        set.addAll(Arrays.asList("Java", "JUnit"));

        System.out.println("Елементи MyLinkedHashSet (size = " + set.size() + "):");
        for (Object item : set.toArray()) {
            System.out.println(" - " + item);
        }

        System.out.println("\n=== 4. Демонстрація MyCache ===");
        MyCache<String, Number> cache = new MyCache<>(2);
        cache.put("one", 1);
        cache.put("two", 2.0);
        System.out.println("Cache до перевищення capacity: " + cache);

        cache.put("three", 3L);
        System.out.println("Cache після додавання 3-го елемента: " + cache);
    }
}