package tech.aomi.common.utils.crypto;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;

/**
 * @author Sean createAt 2021/6/23
 */
public class RSAUtilTest {

    @Test
    public void test() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        RSA.Key key = RSA.generateKey(2048);

        byte[] sign = RSAUtil.sign(key.getPrivateKey(), "SHA512WithRSA", "hello".getBytes(StandardCharsets.UTF_8));

        System.out.println(Base64.getEncoder().encodeToString(sign));
    }
}
