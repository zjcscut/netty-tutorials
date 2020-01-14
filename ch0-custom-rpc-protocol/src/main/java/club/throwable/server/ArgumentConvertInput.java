package club.throwable.server;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/14 23:22
 */
@Data
public class ArgumentConvertInput {

    /**
     * 目标方法
     */
    private Method method;

    /**
     * 方法参数类型列表
     */
    private List<Class<?>> parameterTypes;

    /**
     * 方法参数列表
     */
    private List<Object> arguments;
}
