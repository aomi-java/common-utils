package tech.aomi.common.utils.sms;

import org.junit.jupiter.api.Test;

/**
 * @author 田尘殇Sean(sean.snow @ live.com) createAt 2018/8/13
 */
public class AliYunSMSUtilsTest {


    @Test
    public void sendSms(){
        AliYunSMSUtils.sendSms(
                "aaa",
                "bbb",
                "hello",
                "111",
                "",
                "13000000000"
        );
    }

}
