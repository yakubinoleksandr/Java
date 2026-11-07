package com.example.Task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionStringDemoTest {

    @Test
    @DisplayName("Зміна значення строкового літерала через рефлексію")
    void testModifyStringValueLiteral() throws Exception {
        String original = "OriginalLiteral";
        String replacement = "ModifiedVal";

        ReflectionStringDemo.modifyStringValue(original, replacement);

        assertTrue(original.startsWith("ModifiedVal"),
                "Початковий рядок повинен містити нове значення");
    }

    @Test
    @DisplayName("Зміна значення рядка, введеного/створеного динамічно")
    void testModifyStringValueDynamic() throws Exception {
        String original = new String("TestInput");
        String replacement = "Name";

        ReflectionStringDemo.modifyStringValue(original, replacement);

        assertTrue(original.startsWith("Name"),
                "Динамічний рядок повинен змінити своє значення");
    }
}
