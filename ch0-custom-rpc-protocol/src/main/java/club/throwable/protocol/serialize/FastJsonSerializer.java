package club.throwable.protocol.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/3 9:43
 */
public enum FastJsonSerializer implements Serializer {

    // 单例
    X;

    @Override
    public byte[] encode(Object target) {
        return JSON.toJSONBytes(target);
    }

    @Override
    public Object decode(byte[] bytes, Class<?> targetClass) {
        return JSON.parseObject(bytes, targetClass);
    }
}
