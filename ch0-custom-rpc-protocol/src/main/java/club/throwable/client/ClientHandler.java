package club.throwable.client;

import club.throwable.protocol.ResponseMessagePacket;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/18 14:16
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<ResponseMessagePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMessagePacket packet) throws Exception {
        log.info("接收到响应包,内容:{}", JSON.toJSONString(packet));
        ResponseFuture responseFuture = ContractProxyFactory.RESPONSE_FUTURE_TABLE.get(packet.getSerialNumber());
        if (null != responseFuture) {
            responseFuture.putResponse(packet);
        } else {
            log.warn("接收响应包查询ResponseFuture不存在,请求ID:{}", packet.getSerialNumber());
        }
    }
}
