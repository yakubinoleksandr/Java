package example.yakubin.Task3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class Task3CipherTest {

    @Test
    void testEncryptAndDecrypt_Cycle(@TempDir Path tempDir) throws IOException {
        File file = tempDir.resolve("cipher_test.bin").toFile();
        String originalText = "Hello World! Привіт 123";
        char keyChar = 'K';

        Task3FileManager.encrypt(originalText, file.getAbsolutePath(), keyChar);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        String decryptedText = Task3FileManager.decrypt(file.getAbsolutePath(), keyChar);

        assertEquals(originalText, decryptedText);
    }

    @Test
    void testDecryptWithWrongKey_FailsMatch(@TempDir Path tempDir) throws IOException {
        File file = tempDir.resolve("wrong_key_test.bin").toFile();
        String originalText = "Secret Message";
        char correctKey = 'A';
        char wrongKey = 'B';

        Task3FileManager.encrypt(originalText, file.getAbsolutePath(), correctKey);
        String decryptedText = Task3FileManager.decrypt(file.getAbsolutePath(), wrongKey);

        assertNotEquals(originalText, decryptedText);
    }
}