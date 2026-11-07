package com.example.Task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Task1FileManager {
    public static class Result {
        public final String line;
        public final int wordCount;

        public Result(String line, int wordCount) {
            this.line = line;
            this.wordCount = wordCount;
        }
    }

    public static Result findLineWithMaxWords(String filePath) throws IOException {
        String maxWordsLine = "";
        int maxWordCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String trimmed = currentLine.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] words = trimmed.split("\\s+");
                if (words.length > maxWordCount) {
                    maxWordCount = words.length;
                    maxWordsLine = currentLine;
                }
            }
        }
        return new Result(maxWordsLine, maxWordCount);
    }
}
