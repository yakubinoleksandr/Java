package com.example;

import java.lang.reflect.Array;
import java.util.Objects;

public class MyLinkedList<E> implements MyList<E> {

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<E> first;
    private Node<E> last;
    private int size = 0;

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

    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<E> current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkBefore(E e, Node<E> curr) {
        final Node<E> pred = curr.prev;
        final Node<E> newNode = new Node<>(pred, e, curr);
        curr.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private E unlink(Node<E> x) {
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }

    @Override
    public void add(E e) {
        linkLast(e);
    }

    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index);
        if (index == size) {
            linkLast(element);
        } else {
            linkBefore(element, node(index));
        }
    }

    @Override
    public void addAll(E[] c) {
        if (c == null || c.length == 0) return;
        for (E e : c) {
            linkLast(e);
        }
    }

    public void addAll(Iterable<? extends E> c) {
        if (c == null) return;
        for (E e : c) {
            linkLast(e);
        }
    }

    @Override
    public void addAll(int index, E[] c) {
        checkIndexForAdd(index);
        if (c == null || c.length == 0) return;

        Node<E> curr;
        Node<E> pred;
        if (index == size) {
            curr = null;
            pred = last;
        } else {
            curr = node(index);
            pred = curr.prev;
        }

        for (E item : c) {
            Node<E> newNode = new Node<>(pred, item, null);
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }

        if (curr == null) {
            last = pred;
        } else {
            pred.next = curr;
            curr.prev = pred;
        }

        size += c.length;
    }

    public void addAll(int index, Iterable<? extends E> c) {
        checkIndexForAdd(index);
        if (c == null) return;
        int i = index;
        for (E e : c) {
            add(i++, e);
        }
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    @Override
    public void set(int index, E element) {
        checkIndex(index);
        Node<E> target = node(index);
        target.item = element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (Node<E> x = first; x != null; x = x.next) {
            if (Objects.equals(o, x.item)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next) {
            result[i++] = x.item;
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        Object[] result = a;
        for (Node<E> x = first; x != null; x = x.next) {
            result[i++] = x.item;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }
}