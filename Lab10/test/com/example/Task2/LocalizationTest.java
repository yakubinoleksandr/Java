package com.example.Task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationTest {

    @Test
    @DisplayName("Перевірка завантаження української локалізації (messages_uk_UA)")
    void testUkrainianLocalization() {
        Locale locale = new Locale("uk", "UA");
        ResourceBundle bundle = ResourceBundle.getBundle("location.messages", locale);

        assertNotNull(bundle, "ResourceBundle для української мови повинен завантажуватися");
        assertTrue(bundle.getString("menu.title").contains("МЕНЮ"),
                "Заголовок меню має містити український текст");
        assertEquals("1. Виконати дію", bundle.getString("menu.option1"));
    }

    @Test
    @DisplayName("Перевірка завантаження англійської локалізації (messages_en_US)")
    void testEnglishLocalization() {
        Locale locale = new Locale("en", "US");
        ResourceBundle bundle = ResourceBundle.getBundle("location.messages", locale);

        assertNotNull(bundle, "ResourceBundle для англійської мови повинен завантажуватися");
        assertTrue(bundle.getString("menu.title").contains("MENU"),
                "Заголовок меню має містити англійський текст");
        assertEquals("1. Execute action", bundle.getString("menu.option1"));
    }

    @Test
    @DisplayName("Перевірка наявності всіх необхідних ключів у локалізаціях")
    void testAllKeysExistInBundles() {
        String[] requiredKeys = {
                "menu.title", "menu.option1", "menu.option2",
                "menu.option3", "menu.option4", "menu.choose",
                "msg.action_success", "msg.invalid_option", "msg.exit"
        };

        ResourceBundle ukBundle = ResourceBundle.getBundle("location.messages", new Locale("uk", "UA"));
        ResourceBundle enBundle = ResourceBundle.getBundle("location.messages", new Locale("en", "US"));

        for (String key : requiredKeys) {
            assertTrue(ukBundle.containsKey(key), "Ключ '" + key + "' відсутній в українському bundle");
            assertTrue(enBundle.containsKey(key), "Ключ '" + key + "' відсутній в англійському bundle");
        }
    }
}