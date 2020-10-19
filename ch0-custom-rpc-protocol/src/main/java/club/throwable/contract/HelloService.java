package club.throwable.contract;

import club.throwable.contract.dto.SayHelloRequestDTO;
import club.throwable.contract.dto.SayHelloResponseDTO;

/**
 * @author throwable
 * @version v1.0
 * @description 契约接口
 * @since 2020/1/3 20:32
 */
public interface HelloService {

    String sayHello(String name);

    SayHelloResponseDTO sayHello(SayHelloRequestDTO request);
}
