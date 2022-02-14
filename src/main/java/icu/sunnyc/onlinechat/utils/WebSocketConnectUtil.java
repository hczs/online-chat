package icu.sunnyc.onlinechat.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：hc
 * @date ：Created in 2022/2/13 21:55
 * @modified ：TODO
 */
public class WebSocketConnectUtil {

    /**
     * 当前在线人数
     */
    public static final AtomicInteger ONLINE_NUMBER = new AtomicInteger(0);

    /**
     * 获取当前在线人数
     * @return 当前在线人数
     */
    public static Integer getOnlineNumber() {
        return ONLINE_NUMBER.get();
    }

    /**
     * 当前在线人数+1
     * @return 加一后的当前在线人数
     */
    public static Integer onlineNumberIncrement() {
        return ONLINE_NUMBER.incrementAndGet();
    }

    /**
     * 当前在线人数-1
     * @return 减一后的当前在线人数
     */
    public static Integer onlineNumberDecrement() {
        return ONLINE_NUMBER.decrementAndGet();
    }
}
