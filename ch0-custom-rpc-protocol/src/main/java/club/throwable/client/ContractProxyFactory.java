package club.throwable.client;

import club.throwable.protocol.MessageType;
import club.throwable.protocol.ProtocolConstant;
import club.throwable.protocol.RequestMessagePacket;
import club.throwable.utils.SerialNumberUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;

import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentMap;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/15 23:44
 */
public class ContractProxyFactory {

    private static final RequestArgumentExtractor EXTRACTOR = new DefaultRequestArgumentExtractor();
    private static final ConcurrentMap<Class<?>, Object> CACHE = Maps.newConcurrentMap();

    @SuppressWarnings("unchecked")
    public static <T> T ofProxy(Class<T> interfaceKlass) {
        // 缓存契约接口的代理类实例
        return (T) CACHE.computeIfAbsent(interfaceKlass, x ->
                Proxy.newProxyInstance(interfaceKlass.getClassLoader(), new Class[]{interfaceKlass}, (target, method, args) -> {
                    RequestArgumentExtractInput input = new RequestArgumentExtractInput();
                    input.setInterfaceKlass(interfaceKlass);
                    input.setMethod(method);
                    RequestArgumentExtractOutput output = EXTRACTOR.extract(input);
                    // 封装请求参数
                    RequestMessagePacket packet = new RequestMessagePacket();
                    packet.setMagicNumber(ProtocolConstant.MAGIC_NUMBER);
                    packet.setVersion(ProtocolConstant.VERSION);
                    packet.setSerialNumber(SerialNumberUtils.X.generateSerialNumber());
                    packet.setMessageType(MessageType.REQUEST);
                    packet.setInterfaceName(output.getInterfaceName());
                    packet.setMethodName(output.getMethodName());
                    packet.setMethodArgumentSignatures(output.getMethodArgumentSignatures().toArray(new String[0]));
                    packet.setMethodArguments(args);
                    Channel channel = ClientChannelHolder.CHANNEL_REFERENCE.get();
                    // 发起请求
                    channel.writeAndFlush(packet);
                    // 这里方法返回值需要进行同步处理,相对复杂,后面专门开一篇文章讲解,暂时统一返回字符串
                    // 如果契约接口的返回值类型不是字符串,这里方法返回后会抛出异常
                    return String.format("[%s#%s]调用成功,发送了[%s]到NettyServer[%s]", output.getInterfaceName(),
                            output.getMethodName(), JSON.toJSONString(packet), channel.remoteAddress());
                }));
    }
}
