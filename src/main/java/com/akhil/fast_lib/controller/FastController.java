package com.akhil.fast_lib.controller;

import com.akhil.fast_lib.fast.buffer.BufferPool;
import com.akhil.fast_lib.fast.model.FastRequest;
import com.akhil.fast_lib.fast.model.FastResponse;
import com.akhil.fast_lib.fast.parser.FastParser;
import com.akhil.fast_lib.fast.pool.RequestPool;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FastController {

    @PostMapping("/fast")
    public void fast(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] buffer = BufferPool.getBuffer();

        ServletInputStream input = request.getInputStream();
        int len = input.read(buffer);

        FastRequest req = RequestPool.acquire();
        try {
            FastParser.parse(buffer, len, req);
            int total = req.a + req.b;

            FastResponse.ResponseHolder res = FastResponse.format(total);
            response.setContentType("application/json");
            response.setContentLength(res.length);
            response.getOutputStream().write(res.buffer, 0, res.length);
        } finally {
            RequestPool.release(req);
        }
    }
}