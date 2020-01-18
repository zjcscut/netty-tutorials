package club.throwable.exception;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/18 14:05
 */
public class SendRequestException extends RuntimeException {

    public SendRequestException(String message) {
        super(message);
    }

    public SendRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendRequestException(Throwable cause) {
        super(cause);
    }
}
