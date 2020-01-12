package club.throwable.protocol;

import club.throwable.protocol.serialize.FastJsonSerializer;
import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/12 22:10
 */
@Slf4j
public class TestProtocolServer {

    public static void main(String[] args) throws Exception {
        int port = 9092;
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                            ch.pipeline().addLast(new LengthFieldPrepender(4));
                            ch.pipeline().addLast(new RequestMessagePacketDecoder());
                            ch.pipeline().addLast(new ResponseMessagePacketEncoder(FastJsonSerializer.X));
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<RequestMessagePacket>() {

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, RequestMessagePacket packet) throws Exception {
                                    log.info("接收到来自客户端的请求消息,消息内容:{}", JSON.toJSONString(packet));
                                    ResponseMessagePacket response = new ResponseMessagePacket();
                                    response.setMagicNumber(packet.getMagicNumber());
                                    response.setVersion(packet.getVersion());
                                    response.setSerialNumber(packet.getSerialNumber());
                                    response.setAttachments(packet.getAttachments());
                                    response.setMessageType(MessageType.RESPONSE);
                                    response.setErrorCode(200L);
                                    response.setMessage("Success");
                                    response.setPayload("{\"name\":\"throwable\"}");
                                    ctx.writeAndFlush(response);
                                }
                            });
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("启动NettyServer[{}]成功...", port);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
