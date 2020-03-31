package tech.aomi.common.utils.math;

import tech.aomi.common.utils.StringUtils;

import java.math.BigDecimal;

/**
 * 比较工具
 *
 * @author 田尘殇Sean(sean.snow @ live.com) createAt 2016/12/28
 */
public class CompareUtils {

    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(String left, String right) {
        BigDecimal leftBigDec = getBigDecimal(left);
        BigDecimal rightBigDec = getBigDecimal(right);

        return leftBigDec.compareTo(rightBigDec) > 0;
    }

    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(Short left, String right) {
        BigDecimal leftBigDec = new BigDecimal(left);
        BigDecimal rightBigDec = getBigDecimal(right);

        return leftBigDec.compareTo(rightBigDec) > 0;
    }

    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(Integer left, String right) {
        BigDecimal leftBigDec = new BigDecimal(left);
        BigDecimal rightBigDec = getBigDecimal(right);

        return leftBigDec.compareTo(rightBigDec) > 0;
    }

    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(Long left, String right) {
        BigDecimal leftBigDec = new BigDecimal(left);
        BigDecimal rightBigDec = getBigDecimal(right);

        return leftBigDec.compareTo(rightBigDec) > 0;
    }

    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(Float left, String right) {
        BigDecimal leftBigDec = new BigDecimal(left);
        BigDecimal rightBigDec = getBigDecimal(right);

        return leftBigDec.compareTo(rightBigDec) > 0;
    }

    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(Double left, String right) {
        BigDecimal leftBigDec = new BigDecimal(left);
        BigDecimal rightBigDec = getBigDecimal(right);

        return leftBigDec.compareTo(rightBigDec) > 0;
    }


    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(String left, Short right) {
        BigDecimal leftBigDec = getBigDecimal(left);
        BigDecimal rightBigDec = new BigDecimal(right);

        return leftBigDec.compareTo(rightBigDec) > 0;
    }

    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(String left, Integer right) {
        BigDecimal leftBigDec = getBigDecimal(left);
        BigDecimal rightBigDec = new BigDecimal(right);

        return leftBigDec.compareTo(rightBigDec) > 0;
    }

    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(String left, Long right) {
        BigDecimal leftBigDec = getBigDecimal(left);
        BigDecimal rightBigDec = new BigDecimal(right);

        return leftBigDec.compareTo(rightBigDec) > 0;
    }

    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(String left, Float right) {
        BigDecimal leftBigDec = getBigDecimal(left);
        BigDecimal rightBigDec = new BigDecimal(right);

        return leftBigDec.compareTo(rightBigDec) > 0;
    }

    /**
     * 比较两个数,判断{@code left} 是否大于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果 {@code left} 大于 {@code right} 返回 {@code true}
     */
    public static boolean gt(String left, Double right) {
        BigDecimal leftBigDec = getBigDecimal(left);
        BigDecimal rightBigDec = new BigDecimal(right);
        return gt(leftBigDec, rightBigDec);
    }

    public static boolean gt(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) > 0;
    }

    /**
     * 比较两个数,判断{@code left} 是否大于等于 {@code right}
     *
     * @param left  左边的比较数
     * @param right 右边的被比较数
     * @return 如果{@code left} 大于等于 {@code right} 返回 {@code true}
     */
    public static boolean ge(String left, String right) {
        BigDecimal leftBigDec = getBigDecimal(left);
        BigDecimal rightBigDec = getBigDecimal(right);
        return ge(leftBigDec, rightBigDec);
    }

    public static boolean ge(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) >= 0;
    }

    public static boolean lt(String left, String right) {
        BigDecimal leftBigDec = getBigDecimal(left);
        BigDecimal rightBigDec = getBigDecimal(right);
        return lt(leftBigDec, rightBigDec);
    }

    public static boolean lt(String left, BigDecimal right) {
        return lt(getBigDecimal(left), right);
    }

    public static boolean lt(BigDecimal left, String right) {
        return lt(left, getBigDecimal(right));
    }

    public static boolean lt(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) < 0;
    }

    public static boolean le(String left, String right) {
        BigDecimal leftBigDec = getBigDecimal(left);
        BigDecimal rightBigDec = getBigDecimal(right);
        return le(leftBigDec, rightBigDec);
    }

    public static boolean le(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) <= 0;
    }

    public static boolean equal(String left, String right) {
        BigDecimal leftBigDec = getBigDecimal(left);
        BigDecimal rightBigDec = getBigDecimal(right);
        return leftBigDec.compareTo(rightBigDec) == 0;
    }

    /**
     * 判断指定的数字是否等于0
     *
     * @param number 需要判断的数
     * @return 等于0返回true否则返回false
     */
    public static boolean equalsZero(String number) {
        return getBigDecimal(number).compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 判断指定的数字是否大于等于0
     *
     * @param number 需要判断的数
     * @return boolean 大于等于返回true,否则返回false
     */
    public static boolean gtEqZero(String number) {
        BigDecimal tmp = getBigDecimal(number);
        boolean result = false;
        switch (tmp.compareTo(BigDecimal.ZERO)) {
            case 0:
            case 1:
                result = true;
        }
        return result;
    }

    /**
     * 转换字符串的数字为 {@link BigDecimal}
     * 如果转换失败返回0
     *
     * @param arg 字符串表示的数字
     * @return 转换的值
     */
    public static BigDecimal getBigDecimal(String arg) {
        if (StringUtils.isEmpty(arg)) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(arg);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

}
