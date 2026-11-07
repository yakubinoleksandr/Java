package com.example.Task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Task1FileManagerTest {

    @Test
    void testFindLineWithMaxWords_Success(@TempDir Path tempDir) throws IOException {
        File file = tempDir.resolve("test_input.txt").toFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Перший рядок із трьох слів\n");
            writer.write("Другий рядок містить набагато більше різних слів для перевірки\n");
            writer.write("Короткий рядок\n");
        }

        Task1FileManager.Result result = Task1FileManager.findLineWithMaxWords(file.getAbsolutePath());

        assertEquals(9, result.wordCount);
        assertEquals("Другий рядок містить набагато більше різних слів для перевірки", result.line);
    }

    @Test
    void testFindLineWithMaxWords_EmptyFile(@TempDir Path tempDir) throws IOException {
        File file = tempDir.resolve("empty.txt").toFile();
        file.createNewFile();

        Task1FileManager.Result result = Task1FileManager.findLineWithMaxWords(file.getAbsolutePath());

        assertEquals(0, result.wordCount);
        assertEquals("", result.line);
    }

    @Test
    void testFindLineWithMaxWords_FileNotFound() {
        assertThrows(IOException.class, () -> Task1FileManager.findLineWithMaxWords("non_existent_file.txt"));
    }
}
