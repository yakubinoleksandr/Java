package com.example.Task4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HtmlTagParserTest {

    @Test
    void testSaveReportToFile(@TempDir Path tempDir) throws IOException {
        File reportFile = tempDir.resolve("report.txt").toFile();

        Map<String, Integer> tagMap = new HashMap<>();
        tagMap.put("div", 5);
        tagMap.put("a", 12);
        tagMap.put("p", 3);

        HtmlTagParser.saveReportToFile(reportFile.getAbsolutePath(), tagMap);

        assertTrue(reportFile.exists());

        List<String> lines = Files.readAllLines(reportFile.toPath(), StandardCharsets.UTF_8);
        assertFalse(lines.isEmpty());

        assertTrue(lines.stream().anyMatch(line -> line.contains("лексикографічному")));
        assertTrue(lines.stream().anyMatch(line -> line.contains("частоти")));
    }

    @Test
    void testFetchTagFrequencies_InvalidUrl() {
        assertThrows(Exception.class, () -> HtmlTagParser.fetchTagFrequencies("invalid_url_protocol://test"));
    }
}
