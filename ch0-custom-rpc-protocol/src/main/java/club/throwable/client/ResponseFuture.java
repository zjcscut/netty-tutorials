package club.throwable.client;

import club.throwable.protocol.ResponseMessagePacket;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/1/18 13:30
 */
@ToString
public class ResponseFuture {

    private final long beginTimestamp = System.currentTimeMillis();
    @Getter
    private final long timeoutMilliseconds;
    @Getter
    private final String requestId;
    @Setter
    @Getter
    private volatile boolean sendRequestSucceed = false;
    @Setter
    @Getter
    private volatile Throwable cause;
    @Getter
    private volatile ResponseMessagePacket response;

    private final CountDownLatch latch = new CountDownLatch(1);

    public ResponseFuture(String requestId, long timeoutMilliseconds) {
        this.requestId = requestId;
        this.timeoutMilliseconds = timeoutMilliseconds;
    }

    public boolean timeout() {
        return System.currentTimeMillis() - beginTimestamp > timeoutMilliseconds;
    }

    public ResponseMessagePacket waitResponse(final long timeoutMilliseconds) throws InterruptedException {
        latch.await(timeoutMilliseconds, TimeUnit.MILLISECONDS);
        return response;
    }

    public void putResponse(ResponseMessagePacket response) throws InterruptedException {
        this.response = response;
        latch.countDown();
    }
}
