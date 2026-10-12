package com.example;

import java.util.Map;

public class ConsoleView {
    public void printMessage(String msg) {
        System.out.println(msg);
    }

    public void printPassResult(boolean success) {
        if (success) {
            System.out.println(">>> [ТУРНІКЕТ]: ПРОХІД ДОЗВОЛЕНО! Зелений сигнал.");
        } else {
            System.out.println(">>> [ТУРНІКЕТ]: ПРОХІД ЗАБОРОНЕНО! Червоний сигнал.");
        }
    }

    public void printSummaryStats(int passes, int denials) {
        System.out.println("\n================ СУМАРНА СТАТИСТИКА ================");
        System.out.println("Всього успішних проходів: " + passes);
        System.out.println("Всього відмов у проході:  " + denials);
        System.out.println("Загальна кількість спроб:  " + (passes + denials));
        System.out.println("====================================================");
    }

    public void printDetailedStats(Map<String, Integer> passes, Map<String, Integer> denials) {
        System.out.println("\n============= СТАТИСТИКА ПО ТИПАХ КАРТОК =============");
        System.out.println("--- ДОЗВОЛИ ---");
        if (passes.isEmpty()) System.out.println("  (немає даних)");
        passes.forEach((type, count) ->
                System.out.printf("  %-32s: %d%n", type, count)
        );

        System.out.println("\n--- ВІДМОВИ ---");
        if (denials.isEmpty()) System.out.println("  (немає даних)");
        denials.forEach((type, count) ->
                System.out.printf("  %-32s: %d%n", type, count)
        );
        System.out.println("======================================================");
    }
}
