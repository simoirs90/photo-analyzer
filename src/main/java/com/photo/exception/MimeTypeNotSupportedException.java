package com.photo.exception;

public class MimeTypeNotSupportedException extends RuntimeException  {

    public MimeTypeNotSupportedException(String mimeType) {
        super("Mime type not supported: " + mimeType);
    }
}
