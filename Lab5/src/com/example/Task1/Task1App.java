package com.example.Task1;

import java.io.IOException;
import java.util.Scanner;



public class Task1App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до файлу: ");
        String filePath = scanner.nextLine();

        try {
            Task1FileManager.Result result = Task1FileManager.findLineWithMaxWords(filePath);

            if (result.wordCount > 0) {
                System.out.println("Максимальна кількість слів у рядку: " + result.wordCount);
                System.out.println("Знайдений рядок: " + result.line);
            } else {
                System.out.println("Файл порожній або не містить слів.");
            }
        } catch (IOException e) {
            System.err.println("Помилка під час зчитування файлу: " + e.getMessage());
        }
    }
}
