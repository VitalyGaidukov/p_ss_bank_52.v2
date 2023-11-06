package com.bank.authorization.exception;

import lombok.Data;

import java.util.Date;
@Data
public class Error {

    private int status;
    private String message;
    private Date times;

    public Error(int status, String message) {
        this.status = status;
        this.message = message;
        this.times = new Date();
    }
}
