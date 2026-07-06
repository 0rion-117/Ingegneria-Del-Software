package it.afam.exception;

public class DBMSException extends Exception {
    public DBMSException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBMSException(String message) {
        super(message);
    }
}
