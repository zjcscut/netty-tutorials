package club.throwable.server;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/14 23:21
 */
public interface MethodArgumentConverter {

    ArgumentConvertOutput convert(ArgumentConvertInput input);
}
