package com.example.Task4;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class HtmlTagParser {

    public static Map<String, Integer> fetchTagFrequencies(String urlString) throws IOException {
        Map<String, Integer> tagMap = new HashMap<>();
        URL url = URI.create(urlString).toURL();
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            Pattern pattern = Pattern.compile("<\\s*([a-zA-Z0-9]+)");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String tagName = matcher.group(1).toLowerCase();
                    tagMap.put(tagName, tagMap.getOrDefault(tagName, 0) + 1);
                }
            }
        }
        return tagMap;
    }

    public static void saveReportToFile(String filePath, Map<String, Integer> tagMap) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {
            writer.println("=== а. В алфавітному (лексикографічному) порядку ===");
            tagMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> writer.printf("%-15s : %d%n", entry.getKey(), entry.getValue()));

            writer.println("\n=== b. В порядку зростання частоти ===");
            tagMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> writer.printf("%-15s : %d%n", entry.getKey(), entry.getValue()));
        }
    }
}
