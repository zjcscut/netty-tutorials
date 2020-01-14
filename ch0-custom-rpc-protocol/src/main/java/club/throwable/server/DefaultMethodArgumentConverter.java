package club.throwable.server;

import club.throwable.exception.ArgumentConvertException;
import club.throwable.protocol.serialize.FastJsonSerializer;
import club.throwable.protocol.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/14 23:25
 */
@Slf4j
@Component
public class DefaultMethodArgumentConverter implements MethodArgumentConverter {

    private final Serializer serializer = FastJsonSerializer.X;

    @Override
    public ArgumentConvertOutput convert(ArgumentConvertInput input) {
        ArgumentConvertOutput output = new ArgumentConvertOutput();
        try {
            if (null == input.getArguments() || input.getArguments().isEmpty()) {
                output.setArguments(new Object[0]);
                return output;
            }
            List<Class<?>> inputParameterTypes = input.getParameterTypes();
            int size = inputParameterTypes.size();
            if (size > 0) {
                Object[] arguments = new Object[size];
                for (int i = 0; i < size; i++) {
                    ByteBuf byteBuf = (ByteBuf) input.getArguments().get(i);
                    int readableBytes = byteBuf.readableBytes();
                    byte[] bytes = new byte[readableBytes];
                    byteBuf.readBytes(bytes);
                    arguments[i] = serializer.decode(bytes, inputParameterTypes.get(i));
                    byteBuf.release();
                }
                output.setArguments(arguments);
                return output;
            }
            Class<?>[] parameterTypes = input.getMethod().getParameterTypes();
            int len = parameterTypes.length;
            Object[] arguments = new Object[len];
            for (int i = 0; i < len; i++) {
                ByteBuf byteBuf = (ByteBuf) input.getArguments().get(i);
                int readableBytes = byteBuf.readableBytes();
                byte[] bytes = new byte[readableBytes];
                byteBuf.readBytes(bytes);
                arguments[i] = serializer.decode(bytes, parameterTypes[i]);
                byteBuf.release();
            }
            output.setArguments(arguments);
            return output;
        } catch (Exception e) {
            throw new ArgumentConvertException(e);
        }
    }
}
