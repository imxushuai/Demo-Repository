package com.imxushuai.jrs303.entity;

import lombok.Data;

@Data
public class CustomerResult {

    private int code;
    private String msg;
    private Object data;

    public static CustomerResult result(int code, String msg) {
        CustomerResult customerResult = new CustomerResult();
        customerResult.setCode(code);
        customerResult.setMsg(msg);
        customerResult.setData(null);

        return customerResult;
    }

    public static CustomerResult result(int code, String msg, Object data) {
        CustomerResult customerResult = new CustomerResult();
        customerResult.setCode(code);
        customerResult.setMsg(msg);
        customerResult.setData(data);

        return customerResult;
    }

}
