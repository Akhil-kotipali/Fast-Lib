package com.akhil.fast_lib.fast.pool;

import com.akhil.fast_lib.fast.model.FastRequest;

/**
 * Thread-local object pool for FastRequest instances.
 * Eliminates per-request heap allocation and Young-Gen GC churn.
 */
public class RequestPool {

    private static final ThreadLocal<FastRequest> POOL =
            ThreadLocal.withInitial(FastRequest::new);

    public static FastRequest acquire() {
        FastRequest req = POOL.get();
        req.reset();
        return req;
    }

    public static void release(FastRequest req) {
        req.reset();
    }
}
