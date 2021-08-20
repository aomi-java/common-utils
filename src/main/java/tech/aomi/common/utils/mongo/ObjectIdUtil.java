/*
 * ©2018-2021 Aomi technology service co., LTD. All rights reserved.
 */

package tech.aomi.common.utils.mongo;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sean createAt 2021/3/9
 */
public class ObjectIdUtil {

    /**
     * 线程安全的下一个随机数,每次生成自增+1
     */
    private static final AtomicInteger NEXT_INC = new AtomicInteger(randomInt());
    /**
     * 机器信息
     */
    private static final int MACHINE = getMachinePiece() | getProcessPiece();

    private static final int MAX_VALUE = 0xffffffff;
    private static final int MIN_VALUE = 0x00000000;

    /**
     * 给定的字符串是否为有效的ObjectId
     *
     * @param s 字符串
     * @return 是否为有效的ObjectId
     */
    public static boolean isValid(String s) {
        if (s == null) {
            return false;
        }
        s = removeAll(s, "-");
        final int len = s.length();
        if (len != 24) {
            return false;
        }

        char c;
        for (int i = 0; i < len; i++) {
            c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                continue;
            }
            if (c >= 'a' && c <= 'f') {
                continue;
            }
            if (c >= 'A' && c <= 'F') {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * 获取一个objectId的bytes表现形式
     *
     * @return objectId
     */
    public static byte[] nextBytes() {
        final ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
        bb.putInt((int) currentSeconds());// 4位
        bb.putInt(MACHINE);// 4位
        bb.putInt(NEXT_INC.getAndIncrement());// 4位

        return bb.array();
    }

    /**
     * 获取一个objectId用下划线分割
     *
     * @return objectId
     */
    public static String next() {
        return next(false);
    }

    /**
     * 获取一个objectId
     *
     * @param withHyphen 是否包含分隔符
     * @return objectId
     */
    public static String next(boolean withHyphen) {
        byte[] array = nextBytes();
        return hexString(array, withHyphen);
    }

    public static String minId(Date date) {
        final ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
        bb.putInt((int) date.getTime() / 1000);// 4位
        bb.putInt(MIN_VALUE);// 4位
        bb.putInt(MIN_VALUE);// 4位
        return hexString(bb.array(), false);
    }

    public static String maxId(Date date) {
        final ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
        bb.putInt((int) date.getTime() / 1000);// 4位
        bb.putInt(MAX_VALUE);// 4位
        bb.putInt(MAX_VALUE);// 4位
        return hexString(bb.array(), false);
    }

    public static String hexString(byte[] array, boolean withHyphen) {
        final StringBuilder buf = new StringBuilder(withHyphen ? 26 : 24);
        int t;
        for (int i = 0; i < array.length; i++) {
            if (withHyphen && i % 4 == 0 && i != 0) {
                buf.append("-");
            }
            t = array[i] & 0xff;
            if (t < 16) {
                buf.append('0');
            }
            buf.append(Integer.toHexString(t));

        }
        return buf.toString();
    }

    /**
     * 当前时间的时间戳（秒）
     *
     * @return 当前时间秒数
     */
    public static long currentSeconds() {
        return System.currentTimeMillis() / 1000;
    }


    /**
     * 获得随机数int值
     *
     * @return 随机数
     */
    public static int randomInt() {
        return getRandom().nextInt();
    }


    /**
     * 获取随机数生成器对象<br>
     * ThreadLocalRandom是JDK 7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺。
     *
     * <p>
     * 注意：此方法返回的{@link ThreadLocalRandom}不可以在多线程环境下共享对象，否则有重复随机数问题。
     * 见：https://www.jianshu.com/p/89dfe990295c
     * </p>
     *
     * @return {@link ThreadLocalRandom}
     */
    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }


    /**
     * 移除字符串中所有给定字符串<br>
     * 例：removeAll("aa-bb-cc-dd", "-") =》 aabbccdd
     *
     * @param str         字符串
     * @param strToRemove 被移除的字符串
     * @return 移除后的字符串
     */
    public static String removeAll(CharSequence str, CharSequence strToRemove) {
        // strToRemove如果为空， 也不用继续后面的逻辑
        if (isEmpty(str) || isEmpty(strToRemove)) {
            return str(str);
        }
        return str.toString().replace(strToRemove, "");
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }
    // ----------------------------------------------------------------------------------------- Private method start

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
            machinePiece = netSb.toString().hashCode() << 16;
        } catch (Throwable e) {
            // 出问题随机生成,保留后两位
            machinePiece = (randomInt()) << 16;
        }
        return machinePiece;
    }

    /**
     * 获取进程码片段
     *
     * @return 进程码片段
     */
    private static int getProcessPiece() {
        // 进程码
        // 因为静态变量类加载可能相同,所以要获取进程ID + 加载对象的ID值
        final int processPiece;
        // 进程ID初始化
        int processId;
        try {
            // 获取进程ID
            final String processName = ManagementFactory.getRuntimeMXBean().getName();
            final int atIndex = processName.indexOf('@');
            if (atIndex > 0) {
                processId = Integer.parseInt(processName.substring(0, atIndex));
            } else {
                processId = processName.hashCode();
            }
        } catch (Throwable t) {
            processId = randomInt();
        }

        final ClassLoader loader = getClassLoader();
        // 返回对象哈希码,无论是否重写hashCode方法
        int loaderId = (loader != null) ? System.identityHashCode(loader) : 0;

        // 进程ID + 对象加载ID
        // 保留前2位
        final String processSb = Integer.toHexString(processId) + Integer.toHexString(loaderId);
        processPiece = processSb.hashCode() & 0xFFFF;

        return processPiece;
    }


    /**
     * 获取{@link ClassLoader}<br>
     * 获取顺序如下：<br>
     *
     * <pre>
     * 1、获取当前线程的ContextClassLoader
     * 2、获取当前类对应的ClassLoader
     * 3、获取系统ClassLoader（{@link ClassLoader#getSystemClassLoader()}）
     * </pre>
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ObjectIdUtil.class.getClassLoader();
            if (null == classLoader) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    /**
     * 获取当前线程的{@link ClassLoader}
     *
     * @return 当前线程的class loader
     * @see Thread#getContextClassLoader()
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
