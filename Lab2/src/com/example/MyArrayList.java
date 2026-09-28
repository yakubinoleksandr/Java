package com.example;

import java.util.Objects;
import java.util.RandomAccess;

public class MyArrayList implements MyList, RandomAccess {

    private static final int DEFAULT_CAPACITY= 10;
    private Object[] elements;
    private int size;

    public MyArrayList(){
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }

    private void ensureCapacity(int minCapacity){
        if (minCapacity > elements.length) {
            int newCapacity = elements.length + (elements.length >> 1);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public void add(Object e) {
        ensureCapacity(size + 1);
        elements[size++] = e;
    }

    @Override
    public void add(int index, Object element) {
        checkIndexForAdd(index);
        ensureCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public void addAll(Object[] c) {
        if (c == null || c.length == 0) return;
        ensureCapacity((size + c.length));
        System.arraycopy(c, 0, elements, size, c.length);
        size += c.length;

    }

    @Override
    public void addAll(int index, Object[] c) {
        checkIndexForAdd(index);
        if (c == null || c.length == 0) return;
        ensureCapacity(size + c.length);
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elements, index, elements, index + c.length, numMoved);
        }
        System.arraycopy(c, 0, elements, index, c.length);
        size += c.length;
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        return elements[index];
    }

    @Override
    public Object remove(int index) {
        checkIndex(index);
        Object oldValue = elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;
        return oldValue;
    }

    @Override
    public void set(int index, Object element) {
        checkIndex(index);
        elements[index] = element;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, elements[i])) return i;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] copy = new Object[size];
        System.arraycopy(elements, 0, copy, 0, size);
        return copy;
    }
}
