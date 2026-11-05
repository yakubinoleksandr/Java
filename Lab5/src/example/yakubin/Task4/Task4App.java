package example.yakubin.Task4;

import java.util.Map;
import java.util.Scanner;

public class Task4App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введіть URL (наприклад, https://example.com): ");
            String urlInput = scanner.nextLine();

            Map<String, Integer> tagMap = HtmlTagParser.fetchTagFrequencies(urlInput);

            if (tagMap.isEmpty()) {
                System.out.println("Тегів не знайдено або сторінка порожня.");
                return;
            }

            System.out.println("\n=== a. В порядку зростання (лексикографічно за назвами тегів) ===");
            tagMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> System.out.printf("%-15s : %d%n", entry.getKey(), entry.getValue()));

            System.out.println("\n=== b. В порядку зростання (за частотою появи) ===");
            tagMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> System.out.printf("%-15s : %d%n", entry.getKey(), entry.getValue()));

            System.out.print("\nВведіть шлях та ім'я файлу для збереження звіту: ");
            String savePath = scanner.nextLine();

            HtmlTagParser.saveReportToFile(savePath, tagMap);
            System.out.println("Результат успішно збережено у файл: " + savePath);

        } catch (Exception e) {
            System.err.println("Помилка при виконанні програми: " + e.getMessage());
        }
    }
}
