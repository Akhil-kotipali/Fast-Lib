package com.akhil.fast_lib.fast.parser;

import com.akhil.fast_lib.fast.model.FastRequest;

/**
 * High-performance, zero-allocation byte-level JSON parser.
 * Scans raw HTTP payload bytes directly without intermediate String or AST creation.
 */
public class FastParser {

    public static void parse(byte[] data, int len, FastRequest req) {
        if (data == null || len <= 0) {
            return;
        }

        int i = 0;
        while (i < len) {
            byte c = data[i++];
            if (c == '"') {
                if (i < len && data[i] == 'a' && i + 1 < len && data[i + 1] == '"') {
                    i += 2;
                    req.a = readInt(data, i, len);
                } else if (i < len && data[i] == 'b' && i + 1 < len && data[i + 1] == '"') {
                    i += 2;
                    req.b = readInt(data, i, len);
                }
            }
        }
    }

    private static int readInt(byte[] data, int start, int len) {
        int i = start;
        while (i < len && data[i] != ':') {
            i++;
        }
        i++;

        while (i < len && (data[i] == ' ' || data[i] == '\t' || data[i] == '\r' || data[i] == '\n')) {
            i++;
        }

        int val = 0;
        boolean negative = false;
        if (i < len && data[i] == '-') {
            negative = true;
            i++;
        }

        while (i < len && data[i] >= '0' && data[i] <= '9') {
            val = val * 10 + (data[i] - '0');
            i++;
        }

        return negative ? -val : val;
    }
}
