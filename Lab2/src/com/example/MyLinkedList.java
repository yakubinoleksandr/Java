package com.example;

import java.util.Objects;

public class MyLinkedList implements MyList{

    private static class Node {
        Object item;
        Node next;
        Node prev;

        Node(Node prev, Object element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node first;
    private Node last;
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

    private Node node(int index) {
        if (index < (size >> 1)) {
            Node current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void linkLast(Object e) {
        final Node l = last;
        final Node newNode = new Node(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkBefore(Object e, Node curr) {
        final Node pred = curr.prev;
        final Node newNode = new Node(pred, e, curr);
        curr.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private Object unlink(Node x) {
        final Object element = x.item;
        final Node next = x.next;
        final Node prev = x.prev;

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
    public void add(Object e) {
        linkLast(e);
    }

    @Override
    public void add(int index, Object element) {
        checkIndexForAdd(index);
        if (index == size) {
            linkLast(element);
        } else {
            linkBefore(element, node(index));
        }
    }

    @Override
    public void addAll(Object[] c) {
        if (c == null || c.length == 0) return;
        for (Object e : c) {
            linkLast(e);
        }
    }

    @Override
    public void addAll(int index, Object[] c) {
        checkIndexForAdd(index);
        if (c == null || c.length == 0) return;

        Node curr;
        Node pred;
        if (index == size) {
            curr = null;
            pred = last;
        } else {
            curr = node(index);
            pred = curr.prev;
        }

        for (Object item : c) {
            Node newNode = new Node(pred, item, null);
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

    @Override
    public Object get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public Object remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    @Override
    public void set(int index, Object element) {
        checkIndex(index);
        Node target = node(index);
        target.item = element;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (Node x = first; x != null; x = x.next) {
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
        for (Node x = first; x != null; x = x.next) {
            result[i++] = x.item;
        }
        return result;
    }
}
