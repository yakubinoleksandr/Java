package com.yakubin;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введіть n: ");
        int number = sc.nextInt();

        int searchPrime = -1;
        int maxZeros = -1;

        for (int i = 2; i <= number; i++) {
            if (isPrime(i)) {
                int zeros = countBinaryZeros(i);
                if (zeros > maxZeros) {
                    maxZeros = zeros;
                    searchPrime = i;
                }
            }
        }

        if (searchPrime != -1) {
            System.out.println("Шукане просте число: " + searchPrime);
            System.out.println("Двійкова форма: " + Integer.toBinaryString(searchPrime));
            System.out.println("Кількість нулів: " + maxZeros);
        } else {
            System.out.println("Простих чисел у діапазоні не знайдено (n < 2).");
        }

        sc.close();
    }

    public static boolean isPrime(int num) {
        if (num == 2) return true;
        if (num % 2 == 0) return false;

        for (int i = 3; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int countBinaryZeros(int num) {
        String binary = Integer.toBinaryString(num);
        int zeros = 0;

        for (char c : binary.toCharArray()) {
            if (c == '0') {
                zeros ++;
            }
        }
        return zeros;
    }
}
