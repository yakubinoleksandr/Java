package example.yakubin.Task3;

import java.io.IOException;
import java.util.Scanner;

public class Task3App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введіть текст для шифрування: ");
            String text = scanner.nextLine();

            System.out.print("Введіть один ключовий символ: ");
            String keyInput = scanner.nextLine();
            if (keyInput.isEmpty()) {
                throw new IllegalArgumentException("Ключовий символ не може бути порожнім!");
            }
            char keyChar = keyInput.charAt(0);

            System.out.print("Введіть шлях та ім'я файлу для збереження: ");
            String filePath = scanner.nextLine();

            Task3FileManager.encrypt(text, filePath, keyChar);
            System.out.println("Дані успішно зашифровано у файл: " + filePath);

            String decryptedText = Task3FileManager.decrypt(filePath, keyChar);
            System.out.println("Розшифрований текст з файлу: " + decryptedText);

        } catch (IOException e) {
            System.err.println("Помилка під час роботи з файлом: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка введення даних: " + e.getMessage());
        }
    }
}
