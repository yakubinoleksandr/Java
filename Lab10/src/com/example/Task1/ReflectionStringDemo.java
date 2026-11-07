package com.example.Task1;

import java.lang.reflect.Field;
import java.util.Scanner;

public class ReflectionStringDemo {

    public static void modifyStringValue(String str, String newValue) throws Exception {
        Field valueField = String.class.getDeclaredField("value");
        valueField.setAccessible(true);

        Object internalArray = valueField.get(str);

        if (internalArray instanceof byte[] valueBytes) {
            byte[] newBytes = newValue.getBytes();
            int lengthToCopy = Math.min(valueBytes.length, newBytes.length);
            System.arraycopy(newBytes, 0, valueBytes, 0, lengthToCopy);
        } else if (internalArray instanceof char[] valueChars) {
            char[] newChars = newValue.toCharArray();
            int lengthToCopy = Math.min(valueChars.length, newChars.length);
            System.arraycopy(newChars, 0, valueChars, 0, lengthToCopy);
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String literalString = "OriginalLiteral";

        System.out.print("Введіть початковий рядок: ");
        String userInputString = scanner.nextLine();

        System.out.print("Введіть нове значення для заміни: ");
        String replacement = scanner.nextLine();

        System.out.println("\n--- ДО ЗМІНИ ---");
        System.out.println("Літерал: " + literalString);
        System.out.println("Введений рядок: " + userInputString);

        modifyStringValue(literalString, replacement);
        modifyStringValue(userInputString, replacement);

        System.out.println("\n--- ПІСЛЯ ЗМІНИ ---");
        System.out.println("Літерал: " + literalString);
        System.out.println("Введений рядок: " + userInputString);
    }
}
