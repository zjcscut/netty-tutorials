package club.throwable.protocol;

import club.throwable.protocol.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/3 0:05
 */
@RequiredArgsConstructor
public class RequestMessagePacketEncoder extends MessageToByteEncoder<RequestMessagePacket> {

    private final Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext context, RequestMessagePacket packet, ByteBuf out) throws Exception {
        // 基础包encode
        packet.encode(out);
        // 接口全类名
        out.writeInt(packet.getInterfaceName().length());
        out.writeCharSequence(packet.getInterfaceName(), ProtocolConstant.UTF_8);
        // 方法名
        out.writeInt(packet.getMethodName().length());
        out.writeCharSequence(packet.getMethodName(), ProtocolConstant.UTF_8);
        // 方法参数签名(String[]类型) - 非必须
        if (null != packet.getMethodArgumentSignatures()) {
            int len = packet.getMethodArgumentSignatures().length;
            // 方法参数签名数组长度
            out.writeInt(len);
            for (int i = 0; i < len; i++) {
                String methodArgumentSignature = packet.getMethodArgumentSignatures()[i];
                out.writeInt(methodArgumentSignature.length());
                out.writeCharSequence(methodArgumentSignature, ProtocolConstant.UTF_8);
            }
        } else {
            out.writeInt(0);
        }
        // 方法参数(Object[]类型) - 非必须
        if (null != packet.getMethodArguments()) {
            int len = packet.getMethodArguments().length;
            // 方法参数数组长度
            out.writeInt(len);
            for (int i = 0; i < len; i++) {
                byte[] bytes = serializer.encode(packet.getMethodArguments()[i]);
                out.writeInt(bytes.length);
                out.writeBytes(bytes);
            }
        } else {
            out.writeInt(0);
        }
    }
}
