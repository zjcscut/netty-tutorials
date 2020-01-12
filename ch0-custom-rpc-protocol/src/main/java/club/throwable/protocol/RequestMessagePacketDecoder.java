package club.throwable.protocol;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/3 0:08
 */
@RequiredArgsConstructor
public class RequestMessagePacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> list) throws Exception {
        RequestMessagePacket packet = new RequestMessagePacket();
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
        // 接口全类名
        int interfaceNameLength = in.readInt();
        packet.setInterfaceName(in.readCharSequence(interfaceNameLength, ProtocolConstant.UTF_8).toString());
        // 方法名
        int methodNameLength = in.readInt();
        packet.setMethodName(in.readCharSequence(methodNameLength, ProtocolConstant.UTF_8).toString());
        // 方法参数签名
        int methodArgumentSignatureArrayLength = in.readInt();
        if (methodArgumentSignatureArrayLength > 0) {
            String[] methodArgumentSignatures = new String[methodArgumentSignatureArrayLength];
            for (int i = 0; i < methodArgumentSignatureArrayLength; i++) {
                int methodArgumentSignatureLength = in.readInt();
                methodArgumentSignatures[i] = in.readCharSequence(methodArgumentSignatureLength, ProtocolConstant.UTF_8).toString();
            }
            packet.setMethodArgumentSignatures(methodArgumentSignatures);
        }
        // 方法参数
        int methodArgumentArrayLength = in.readInt();
        if (methodArgumentArrayLength > 0) {
            // 这里的Object[]实际上是ByteBuf[] - 后面需要二次加工为对应类型的实例
            Object[] methodArguments = new Object[methodArgumentArrayLength];
            for (int i = 0; i < methodArgumentArrayLength; i++) {
                int byteLength = in.readInt();
                methodArguments[i] = in.readBytes(byteLength);
            }
            packet.setMethodArguments(methodArguments);
        }
        list.add(packet);
    }
}
