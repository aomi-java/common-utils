package tech.aomi.common.utils.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author Sean createAt 2021/6/23
 */
public class DesUtilsTest {

    @Test
    public void test() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidKeyException {

        byte[] key = DesUtils.generateDesKey();

        System.out.println(Base64.getEncoder().encodeToString(key));

        String data = "hello";
        DesUtils.setAlgorithm(DesUtils.CIPHER_ECB_PKCS5Padding);

        byte[] ci = TripDesUtils.encrypt(key, data.getBytes(StandardCharsets.UTF_8));

        System.out.println(Base64.getEncoder().encodeToString(ci));

        System.out.println(new String(DesUtils.decrypt(key, ci)));

    }
}
