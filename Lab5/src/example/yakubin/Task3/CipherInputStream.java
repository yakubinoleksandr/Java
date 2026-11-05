package example.yakubin.Task3;

import java.io.*;

class CipherInputStream extends FilterInputStream {
    private final int keyShift;

    public CipherInputStream(InputStream in, char keyChar) {
        super(in);
        this.keyShift = keyChar;
    }

    @Override
    public int read() throws IOException {
        int b = super.read();
        if (b == -1) {
            return -1;
        }
        return b - keyShift;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int bytesRead = super.read(b, off, len);
        if (bytesRead != -1) {
            for (int i = off; i < off + bytesRead; i++) {
                b[i] = (byte) (b[i] - keyShift);
            }
        }
        return bytesRead;
    }
}
