package tech.aomi.common.utils;

import java.math.BigInteger;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 流水号生成工具
 *
 * @author Sean createAt 2021/8/22
 */
public final class AuditNumberUtil {

    private static final int LOW_ORDER_TWO_BYTES = 0x00ffffff;
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final AtomicInteger NEXT_COUNTER = new AtomicInteger(new SecureRandom().nextInt());

    private static final int MACHINE = getMachinePiece();

    public static String next() {
        return next(LocalDateTime.now());
    }

    public static String next(LocalDateTime date) {
        String timeStr = TIMESTAMP_FORMAT.format(date);

        int counter = NEXT_COUNTER.getAndIncrement() & LOW_ORDER_TWO_BYTES;

        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.put(int2(MACHINE));
        buffer.put(int1(MACHINE));
        buffer.put(int0(MACHINE));
        buffer.put(int2(counter));
        buffer.put(int1(counter));
        buffer.put(int0(counter));
        long value = new BigInteger(1, buffer.array()).longValue();
        return timeStr + String.format("%015d", value);
    }

    private static byte int3(final int x) {
        return (byte) (x >> 24);
    }

    private static byte int2(final int x) {
        return (byte) (x >> 16);
    }

    private static byte int1(final int x) {
        return (byte) (x >> 8);
    }

    private static byte int0(final int x) {
        return (byte) (x);
    }

    private static byte short1(final short x) {
        return (byte) (x >> 8);
    }

    private static byte short0(final short x) {
        return (byte) (x);
    }

    /**
     * 获取机器码片段
     *
     * @return 机器码片段
     */
    private static int getMachinePiece() {
        // 机器码
        int machinePiece;
        try {
            StringBuilder netSb = new StringBuilder();
            // 返回机器所有的网络接口
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            // 遍历网络接口
            while (e.hasMoreElements()) {
                NetworkInterface ni = e.nextElement();
                // 网络接口信息
                netSb.append(ni.toString());
            }
            // 保留后两位
            machinePiece = netSb.toString().hashCode();
        } catch (Throwable e) {
            // 出问题随机生成,保留后两位
            machinePiece = (new SecureRandom().nextInt());
        }
        return machinePiece;
    }

}

