package ru.effective_mobile.exception;

public class PassedNullException extends RuntimeException {
    public PassedNullException() {
        super("The passed key or value cannot be null");
    }
}