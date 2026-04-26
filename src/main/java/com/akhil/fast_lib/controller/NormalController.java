package com.akhil.fast_lib.controller;

import com.akhil.fast_lib.normal.OrderRequest;
import com.akhil.fast_lib.normal.OrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NormalController {

    @PostMapping("/normal")
    public OrderResponse normal(@RequestBody OrderRequest body) {
        int total = body.a + body.b;
        return new OrderResponse("created", total);
    }
}
