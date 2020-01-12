package club.throwable.protocol;

import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/3 9:17
 */
@Data
public class ProtocolConstant {

    public static final int MAGIC_NUMBER = 10086;

    public static final int VERSION = 1;

    public static final Charset UTF_8 = StandardCharsets.UTF_8;
}
