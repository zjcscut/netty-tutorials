package club.throwable.server;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/12 11:39
 */
@EqualsAndHashCode
@Data
public class MethodMatchInput {

    private String interfaceName;

    private String methodName;

    private List<String> methodArgumentSignatures;

    private int methodArgumentArraySize;
}
