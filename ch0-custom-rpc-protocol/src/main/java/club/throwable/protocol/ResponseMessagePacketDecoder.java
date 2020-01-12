package club.throwable.protocol;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.Map;

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
        // 魔数
        packet.setMagicNumber(in.readInt());
        // 版本
        packet.setVersion(in.readInt());
        // 流水号
        int serialNumberLength = in.readInt();
        packet.setSerialNumber(in.readCharSequence(serialNumberLength, ProtocolConstant.UTF_8).toString());
        // 消息类型
        byte messageTypeByte = in.readByte();
        packet.setMessageType(MessageType.fromValue(messageTypeByte));
        // 附件
        Map<String, String> attachments = Maps.newHashMap();
        packet.setAttachments(attachments);
        int attachmentSize = in.readInt();
        if (attachmentSize > 0) {
            for (int i = 0; i < attachmentSize; i++) {
                int keyLength = in.readInt();
                String key = in.readCharSequence(keyLength, ProtocolConstant.UTF_8).toString();
                int valueLength = in.readInt();
                String value = in.readCharSequence(valueLength, ProtocolConstant.UTF_8).toString();
                attachments.put(key, value);
            }
        }
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
