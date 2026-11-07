package com.example.Task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class CircularBufferTest {

    @Test
    @DisplayName("Перевірка послідовності додавання та вилучення (FIFO)")
    void testPutAndTake() throws InterruptedException {
        CircularBuffer<String> buffer = new CircularBuffer<>(3);

        buffer.put("First");
        buffer.put("Second");

        assertEquals("First", buffer.take());
        assertEquals("Second", buffer.take());
    }

    @Test
    @DisplayName("Перевірка очікування (блокировки) при вилученні з порожнього буфера")
    void testTakeBlocksOnEmptyBuffer() throws InterruptedException {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(2);
        AtomicBoolean itemTaken = new AtomicBoolean(false);
        CountDownLatch latch = new CountDownLatch(1);

        Thread consumer = new Thread(() -> {
            try {
                latch.countDown();
                buffer.take();
                itemTaken.set(true);
            } catch (InterruptedException ignored) {}
        });

        consumer.start();
        latch.await();
        Thread.sleep(100);

        assertFalse(itemTaken.get(), "Потік-споживач повинен чекати, поки з'являться дані");

        buffer.put(42);
        consumer.join(1000);

        assertTrue(itemTaken.get(), "Потік-споживач повинен вичитати дані після їх додавання");
    }

    @Test
    @DisplayName("Перевірка очікування (блокировки) при додаванні в повний буфер")
    void testPutBlocksOnFullBuffer() throws InterruptedException {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(2);

        buffer.put(1);
        buffer.put(2);

        AtomicBoolean itemPut = new AtomicBoolean(false);
        CountDownLatch latch = new CountDownLatch(1);

        Thread producer = new Thread(() -> {
            try {
                latch.countDown();
                buffer.put(3);
                itemPut.set(true);
            } catch (InterruptedException ignored) {}
        });

        producer.start();
        latch.await();
        Thread.sleep(100);

        assertFalse(itemPut.get(), "Потік-виробник повинен чекати, поки звільниться місце");

        assertEquals(1, buffer.take());
        producer.join(1000);

        assertTrue(itemPut.get(), "Потік-виробник повинен успішно додати елемент після звільнення місця");
        assertEquals(2, buffer.take());
        assertEquals(3, buffer.take());
    }
}
