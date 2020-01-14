package club.throwable.server;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/12 11:47
 */
@Data
public class MethodMatchOutput {

    /**
     * 目标方法实例
     */
    private Method targetMethod;

    /**
     * 目标实现类 - 这个有可能是被Cglib增强过的类型,是宿主类的子类,如果没有被Cglib增强过,那么它就是宿主类
     */
    private Class<?> targetClass;

    /**
     * 宿主类
     */
    private Class<?> targetUserClass;

    /**
     * 宿主类Bean实例
     */
    private Object target;

    /**
     * 方法参数类型列表
     */
    private List<Class<?>> parameterTypes;
}
