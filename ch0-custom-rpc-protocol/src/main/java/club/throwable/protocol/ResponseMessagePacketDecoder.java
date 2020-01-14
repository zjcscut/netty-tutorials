package club.throwable.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/12 20:47
 */
public class ResponseMessagePacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ResponseMessagePacket packet = new ResponseMessagePacket();
        // 基础包decode
        packet.decode(in);
        // error code
        packet.setErrorCode(in.readLong());
        // message
        int messageLength = in.readInt();
        packet.setMessage(in.readCharSequence(messageLength, ProtocolConstant.UTF_8).toString());
        // payload - ByteBuf实例
        int payloadLength = in.readInt();
        packet.setPayload(in.readBytes(payloadLength));
        out.add(packet);
    }
}
