package club.throwable.server.contract;

import club.throwable.contract.HelloService;
import club.throwable.contract.dto.SayHelloRequestDTO;
import club.throwable.contract.dto.SayHelloResponseDTO;
import org.springframework.stereotype.Service;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/14 23:55
 */
@Service
public class DefaultHelloService implements HelloService {

    @Override
    public String sayHello(String name) {
        return String.format("%s say hello!", name);
    }

    @Override
    public SayHelloResponseDTO sayHello(SayHelloRequestDTO request) {
        String content = String.format("%s say hello!", request.getName());
        SayHelloResponseDTO response = new SayHelloResponseDTO();
        response.setContent(content);
        return response;
    }
}
