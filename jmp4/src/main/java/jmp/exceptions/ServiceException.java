package jmp.exceptions;

/**
 * Created by alex on 24.03.17.
 */
public class ServiceException extends Exception {
    private int errorCode;

    public ServiceException(final int errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
