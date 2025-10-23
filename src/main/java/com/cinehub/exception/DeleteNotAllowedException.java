package com.cinehub.exception;

public class DeleteNotAllowedException extends RuntimeException {
    public DeleteNotAllowedException() { super(); }
    public DeleteNotAllowedException(String message) { super(message); }
}