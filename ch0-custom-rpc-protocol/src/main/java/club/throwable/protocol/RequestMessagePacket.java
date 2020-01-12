package club.throwable.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author throwable
 * @version v1.0
 * @description 请求消息数据包
 * @since 2020/1/2 23:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RequestMessagePacket extends BaseMessagePacket {

    /**
     * 接口全类名
     */
    private String interfaceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法参数签名
     */
    private String[] methodArgumentSignatures;

    /**
     * 方法参数
     */
    private Object[] methodArguments;
}
