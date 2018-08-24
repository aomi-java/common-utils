package software.sitb.common.utils;

import java.util.regex.Pattern;

/**
 * @author 田尘殇Sean(sean.snow @ live.com) createAt 2018/6/25
 */
public class PatternUtil {

    /**
     * email
     */
    public static final Pattern EMAIL = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    /**
     * phoneNo
     */
    public static final Pattern PHONE_NO = Pattern.compile("^[1](([3][0-9])|([4][5,7,9])|([5][^4,6,9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$");


    public static boolean isEmail(String email) {
        return EMAIL.matcher(email).matches();
    }

    public static boolean isPhoneNo(String phoneNo) {
        return PHONE_NO.matcher(phoneNo).matches();
    }

}
