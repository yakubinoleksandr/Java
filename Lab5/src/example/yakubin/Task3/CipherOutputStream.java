package example.yakubin.Task3;

import java.io.*;

class CipherOutputStream extends FilterOutputStream {
    private final int keyShift;

    public CipherOutputStream(OutputStream out, char keyChar) {
        super(out);
        this.keyShift = keyChar;
    }

    @Override
    public void write(int b) throws IOException {
        super.write(b + keyShift);
    }
}