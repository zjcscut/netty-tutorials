package club.throwable.utils;

import java.util.UUID;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/3 9:27
 */
public enum SerialNumberUtils {

    // 单例;
    X;

    public String generateSerialNumber() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
