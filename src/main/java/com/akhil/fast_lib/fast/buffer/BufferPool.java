package com.akhil.fast_lib.fast.buffer;

public class BufferPool {

    private static final int BUFFER_SIZE = 8192;

    private static final ThreadLocal<byte[]> BUFFER =
            ThreadLocal.withInitial(() -> new byte[BUFFER_SIZE]);

    public static byte[] getBuffer() {
        return BUFFER.get();
    }
}
