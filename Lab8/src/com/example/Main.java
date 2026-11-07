package com.example;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        int size = 1_000_000;
        int[] array = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(101);
        }

        ForkJoinPool pool = new ForkJoinPool();
        ArraySumTask task = new ArraySumTask(array, 0, array.length);

        long startTime = System.currentTimeMillis();
        long totalSum = pool.invoke(task);
        long endTime = System.currentTimeMillis();

        System.out.println("Сума всіх елементів масиву: " + totalSum);
        System.out.println("Час виконання (ForkJoin): " + (endTime - startTime) + " мс");
    }
}
