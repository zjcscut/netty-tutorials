package club.throwable.exception;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/12 11:55
 */
public class MethodMatchException extends RuntimeException {

    public MethodMatchException(String message) {
        super(message);
    }

    public MethodMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodMatchException(Throwable cause) {
        super(cause);
    }
}
