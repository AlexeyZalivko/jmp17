package exchange.exceptions;

/**
 * Created by alex on 2017-06-11.
 */
public class BussinessException extends Exception {
    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
