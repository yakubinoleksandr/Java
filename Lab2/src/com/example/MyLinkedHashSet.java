package com.example;

import java.util.Arrays;
import java.util.Objects;

public class MyLinkedHashSet {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private static class Entry {
        Object key;
        int hash;
        Entry nextInBucket;
        Entry before;
        Entry after;

        Entry(int hash, Object key, Entry nextInBucket) {
            this.hash = hash;
            this.key = key;
            this.nextInBucket = nextInBucket;
        }
    }

    private Entry[] table;
    private int size = 0;
    private final float loadFactor;
    private int threshold;

    private Entry head;
    private Entry tail;

    public MyLinkedHashSet() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyLinkedHashSet(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public MyLinkedHashSet(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.table = new Entry[initialCapacity];
        this.threshold = (int) (initialCapacity * loadFactor);
    }

    private int hash(Object key) {
        if (key == null) return 0;
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    private int getBucketIndex(int hash, int tableLength) {
        return Math.abs(hash) % tableLength;
    }

    private void linkNodeLast(Entry p) {
        Entry last = tail;
        tail = p;
        if (last == null) {
            head = p;
        } else {
            p.before = last;
            last.after = p;
        }
    }

    private void unlinkNode(Entry p) {
        Entry b = p.before;
        Entry a = p.after;

        if (b == null) {
            head = a;
        } else {
            b.after = a;
            p.before = null;
        }

        if (a == null) {
            tail = b;
        } else {
            a.before = b;
            p.after = null;
        }
    }

    private void resize() {
        int newCapacity = table.length * 2;
        Entry[] newTable = new Entry[newCapacity];

        for (Entry e = head; e != null; e = e.after) {
            int newBucket = getBucketIndex(e.hash, newCapacity);
            e.nextInBucket = newTable[newBucket];
            newTable[newBucket] = e;
        }

        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    public boolean add(Object key) {
        int h = hash(key);
        int bucketIndex = getBucketIndex(h, table.length);

        for (Entry e = table[bucketIndex]; e != null; e = e.nextInBucket) {
            if (e.hash == h && Objects.equals(e.key, key)) return false;
        }

        Entry newEntry = new Entry(h, key, table[bucketIndex]);
        table[bucketIndex] = newEntry;

        linkNodeLast(newEntry);
        size++;

        if (size >= threshold) {
            resize();
        }
        return true;
    }

    public boolean contains(Object key) {
        int h = hash(key);
        int bucketIndex = getBucketIndex(h, table.length);

        for (Entry e = table[bucketIndex]; e != null; e = e.nextInBucket) {
            if (e.hash == h && Objects.equals(e.key, key)) return true;
        }

        return false;
    }

    public boolean remove(Object key) {
        int h = hash(key);
        int bucketIndex = getBucketIndex(h, table.length);

        Entry prevInBucket = null;
        Entry current = table[bucketIndex];

        while (current != null) {
            if (current.hash == h && Objects.equals(current.key, key)) {
                if (prevInBucket == null) {
                    table[bucketIndex] = current.nextInBucket;
                } else {
                    prevInBucket.nextInBucket = current.nextInBucket;
                }

                unlinkNode(current);

                current.nextInBucket = null;
                current.key = null;
                size--;
                return true;
            }
            prevInBucket = current;
            current = current.nextInBucket;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Arrays.fill(table, null);
        head = null;
        tail = null;
        size = 0;
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Entry e = head; e != null; e = e.after) {
            result[i++] = e.key;
        }
        return result;
    }
}
