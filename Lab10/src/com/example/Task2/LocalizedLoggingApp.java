package com.example.Task2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Locale;
import java.util.Scanner;

public class LocalizedLoggingApp {

    private static final Logger logger = LogManager.getLogger(LocalizedLoggingApp.class);
    private static ResourceBundle bundle;

    public static void setLanguage(Locale locale) {
        String resourcePath = "location/messages_" + locale.getLanguage() + "_" + locale.getCountry() + ".properties";
        try (var is = LocalizedLoggingApp.class.getClassLoader().getResourceAsStream(resourcePath);
             var reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            bundle = new PropertyResourceBundle(reader);
            logger.info("Мову інтерфейсу змінено на: {}", locale.getLanguage());
        } catch (Exception e) {
            logger.error("Не вдалося завантажити файл локалізації: {}", resourcePath, e);
            bundle = ResourceBundle.getBundle("location.messages", locale);
        }
    }

    public static void main(String[] args) {
        logger.debug("Старт програми. Ініціалізація локалі за замовчуванням.");
        setLanguage(new Locale("uk", "UA"));

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n" + bundle.getString("menu.title"));
            System.out.println(bundle.getString("menu.option1"));
            System.out.println(bundle.getString("menu.option2"));
            System.out.println(bundle.getString("menu.option3"));
            System.out.println(bundle.getString("menu.option4"));
            System.out.print(bundle.getString("menu.choose") + " ");

            String input = scanner.nextLine();
            logger.debug("Користувач ввів значення: {}", input);

            switch (input) {
                case "1":
                    logger.debug("Виконується робочий процес...");
                    System.out.println(bundle.getString("msg.action_success"));
                    logger.info("Дію користувача успішно оброблено.");
                    break;

                case "2":
                    logger.warn("Спроба виконати потенційно небезпечну операцію!");
                    try {
                        int result = 10 / 0;
                    } catch (ArithmeticException e) {
                        logger.error("Сталася помилка ділення на нуль: ", e);
                    }
                    break;

                case "3":
                    System.out.println(bundle.getString("menu.language_choice"));
                    String langChoice = scanner.nextLine();
                    if ("1".equals(langChoice)) {
                        setLanguage(new Locale("uk", "UA"));
                    } else if ("2".equals(langChoice)) {
                        setLanguage(new Locale("en", "US"));
                    } else {
                        logger.warn("Введено неіснуючий варіант мови: {}", langChoice);
                        System.out.println(bundle.getString("msg.invalid_option"));
                    }
                    break;

                case "4":
                    running = false;
                    System.out.println(bundle.getString("msg.exit"));
                    logger.info("Програму успішно завершено користувачем.");
                    break;

                default:
                    logger.warn("Некоректне значення меню: {}", input);
                    System.out.println(bundle.getString("msg.invalid_option"));
                    break;
            }
        }
    }
}
