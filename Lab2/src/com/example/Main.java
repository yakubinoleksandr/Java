package com.example;


public class Main {
    public static void main(String[] args) {
        MyCache cache = new MyCache(3);
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        System.out.println(cache.toString());

        cache.get("A");
        System.out.println(cache.toString());

        cache.put("D", 4);
        System.out.println(cache.toString());

        System.out.println(cache.get("B"));
        System.out.println(cache.get("A"));
    }
}
