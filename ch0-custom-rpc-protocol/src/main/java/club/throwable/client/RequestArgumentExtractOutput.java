package club.throwable.client;

import lombok.Data;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/15 23:32
 */
@Data
public class RequestArgumentExtractOutput {

    private String interfaceName;

    private String methodName;

    private List<String> methodArgumentSignatures;
}
