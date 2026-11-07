package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class ArraySumTaskTest {

    private ForkJoinPool pool;

    @BeforeEach
    void setUp() {
        pool = new ForkJoinPool();
    }

    @Test
    @DisplayName("Перевірка обчислення суми для великого масиву (1 000 000 елементів)")
    void testArraySumWithLargeArray() {
        int size = 1_000_000;
        int[] array = new int[size];
        Random random = new Random();
        long expectedSum = 0;

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(101);
            expectedSum += array[i];
        }

        ArraySumTask task = new ArraySumTask(array, 0, array.length);
        long actualSum = pool.invoke(task);

        assertEquals(expectedSum, actualSum, "Сума масиву через ForkJoin повинна збігатися з послідовною сумою");
    }

    @Test
    @DisplayName("Перевірка роботи з підмасивом, меншим за поріг (менше 20 елементів)")
    void testArraySumSmallArray() {
        int[] array = {5, 10, 15, 20, 25};
        long expectedSum = Arrays.stream(array).sum();

        ArraySumTask task = new ArraySumTask(array, 0, array.length);
        long actualSum = pool.invoke(task);

        assertEquals(expectedSum, actualSum, "Сума малого масиву обчислена некоректно");
    }

    @Test
    @DisplayName("Перевірка роботи з порожнім масивом")
    void testEmptyArray() {
        int[] array = new int[0];

        ArraySumTask task = new ArraySumTask(array, 0, array.length);
        long actualSum = pool.invoke(task);

        assertEquals(0, actualSum, "Сума порожнього масиву повинна дорівнювати 0");
    }

    @Test
    @DisplayName("Перевірка обчислення часткового діапазону масиву")
    void testSubArraySum() {
        int[] array = {10, 20, 30, 40, 50, 60, 70};
        long expectedSum = 90;

        ArraySumTask task = new ArraySumTask(array, 1, 4);
        long actualSum = pool.invoke(task);

        assertEquals(expectedSum, actualSum, "Сума підмасиву обчислена некоректно");
    }
}
