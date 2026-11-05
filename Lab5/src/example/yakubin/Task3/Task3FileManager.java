package example.yakubin.Task3;

import java.io.*;
import java.nio.charset.StandardCharsets;

class Task3FileManager {

    public static void encrypt(String text, String filePath, char keyChar) throws IOException {
        try (OutputStream fos = new FileOutputStream(filePath);
             CipherOutputStream cos = new CipherOutputStream(fos, keyChar)) {
            cos.write(text.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static String decrypt(String filePath, char keyChar) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (InputStream fis = new FileInputStream(filePath);
             CipherInputStream cis = new CipherInputStream(fis, keyChar)) {
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }
}
