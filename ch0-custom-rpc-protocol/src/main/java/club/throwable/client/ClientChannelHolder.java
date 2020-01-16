package club.throwable.client;

import io.netty.channel.Channel;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/15 23:45
 */
public class ClientChannelHolder {

    public static final AtomicReference<Channel> CHANNEL_REFERENCE = new AtomicReference<>();
}
