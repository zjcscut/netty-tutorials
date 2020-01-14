package club.throwable.exception;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/14 23:26
 */
public class ArgumentConvertException extends RuntimeException {

    public ArgumentConvertException(String message) {
        super(message);
    }

    public ArgumentConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentConvertException(Throwable cause) {
        super(cause);
    }
}
