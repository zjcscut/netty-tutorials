package club.throwable.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author throwable
 * @version v1.0
 * @description 响应消息数据包
 * @since 2020/1/2 23:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseMessagePacket extends BaseMessagePacket {

    /**
     * error code
     */
    private Long errorCode;

    /**
     * 消息描述
     */
    private String message;

    /**
     * 消息载荷 - 暂定为字符串
     */
    private Object payload;
}
