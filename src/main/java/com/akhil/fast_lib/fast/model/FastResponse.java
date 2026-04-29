package com.akhil.fast_lib.fast.model;

/**
 * Pre-allocated, zero-allocation response formatter.
 * Serializes status and computed total directly into a thread-local byte buffer.
 */
public class FastResponse {

    private static final byte[] PREFIX = "{\"status\":\"created\",\"total\":".getBytes();
    private static final byte SUFFIX = '}';

    public static class ResponseHolder {
        public final byte[] buffer = new byte[128];
        public int length = 0;
    }

    private static final ThreadLocal<ResponseHolder> HOLDER =
            ThreadLocal.withInitial(ResponseHolder::new);

    public static ResponseHolder format(int total) {
        ResponseHolder holder = HOLDER.get();
        byte[] buf = holder.buffer;

        System.arraycopy(PREFIX, 0, buf, 0, PREFIX.length);
        int idx = PREFIX.length;

        if (total == 0) {
            buf[idx++] = '0';
        } else {
            int temp = total;
            int start = idx;
            if (temp < 0) {
                buf[idx++] = '-';
                start = idx;
                temp = -temp;
            }
            while (temp > 0) {
                buf[idx++] = (byte) ('0' + (temp % 10));
                temp /= 10;
            }
            for (int l = start, r = idx - 1; l < r; l++, r--) {
                byte b = buf[l];
                buf[l] = buf[r];
                buf[r] = b;
            }
        }

        buf[idx++] = SUFFIX;
        holder.length = idx;
        return holder;
    }
}
