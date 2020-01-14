package club.throwable.protocol;

import club.throwable.protocol.serialize.Serializer;
import club.throwable.utils.ByteBufferUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/12 20:47
 */
@RequiredArgsConstructor
public class ResponseMessagePacketEncoder extends MessageToByteEncoder<ResponseMessagePacket> {

    private final Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseMessagePacket packet, ByteBuf out) throws Exception {
        // 基础包encode
        packet.encode(out);
        // error code
        out.writeLong(packet.getErrorCode());
        // message
        String message = packet.getMessage();
        ByteBufferUtils.X.encodeUtf8CharSequence(out, message);
        // payload
        byte[] bytes = serializer.encode(packet.getPayload());
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
