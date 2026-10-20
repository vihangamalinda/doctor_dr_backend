package com.doctor.dr.exception;

public class DataInvalidException extends IllegalArgumentException {
    public DataInvalidException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DataInvalidException(String message) {
        super(message);
    }
}
