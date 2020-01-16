package club.throwable.client;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/15 23:32
 */
@Data
public class RequestArgumentExtractInput {

    private Class<?> interfaceKlass;

    private Method method;
}
