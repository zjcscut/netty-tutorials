package club.throwable.server;

import lombok.Data;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/12 11:59
 */
@Data
public class HostClassMethodInfo {

    private Class<?> hostClass;
    private Class<?> hostUserClass;
    private Object hostTarget;
}
