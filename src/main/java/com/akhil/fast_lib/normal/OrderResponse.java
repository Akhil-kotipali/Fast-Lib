package com.akhil.fast_lib.normal;

public class OrderResponse {
    public String status;
    public int total;

    public OrderResponse() {
    }

    public OrderResponse(String status, int total) {
        this.status = status;
        this.total = total;
    }
}
