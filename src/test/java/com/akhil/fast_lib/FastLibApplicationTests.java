package com.akhil.fast_lib;

import com.akhil.fast_lib.controller.FastController;
import com.akhil.fast_lib.controller.NormalController;
import com.akhil.fast_lib.fast.model.FastRequest;
import com.akhil.fast_lib.fast.parser.FastParser;
import com.akhil.fast_lib.fast.pool.RequestPool;
import com.akhil.fast_lib.normal.OrderRequest;
import com.akhil.fast_lib.normal.OrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class FastLibApplicationTests {

    @Test
    void testNormalController() {
        NormalController controller = new NormalController();
        OrderRequest req = new OrderRequest();
        req.a = 10;
        req.b = 20;

        OrderResponse res = controller.normal(req);
        assertEquals("created", res.status);
        assertEquals(30, res.total);
    }

    @Test
    void testFastController() throws IOException {
        FastController controller = new FastController();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent("{\"a\":10,\"b\":20}".getBytes(StandardCharsets.UTF_8));

        MockHttpServletResponse response = new MockHttpServletResponse();
        controller.fast(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals("{\"status\":\"created\",\"total\":30}", response.getContentAsString());
    }

    @Test
    void testFastParserDirectly() {
        FastRequest req = new FastRequest();

        byte[] input1 = "{\"a\":15,\"b\":25}".getBytes(StandardCharsets.UTF_8);
        FastParser.parse(input1, input1.length, req);
        assertEquals(15, req.a);
        assertEquals(25, req.b);

        req.reset();
        byte[] input2 = "{\n  \"b\": 40,\n  \"a\": 60\n}".getBytes(StandardCharsets.UTF_8);
        FastParser.parse(input2, input2.length, req);
        assertEquals(60, req.a);
        assertEquals(40, req.b);
    }

    @Test
    void testRequestPool() {
        FastRequest req1 = RequestPool.acquire();
        req1.a = 100;
        req1.b = 200;
        RequestPool.release(req1);

        FastRequest req2 = RequestPool.acquire();
        assertEquals(0, req2.a);
        assertEquals(0, req2.b);
        RequestPool.release(req2);
    }
}
