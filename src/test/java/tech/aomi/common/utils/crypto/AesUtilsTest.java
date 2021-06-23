package tech.aomi.common.utils.crypto;

import org.junit.Test;

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
public class AesUtilsTest {

    private static final String KEY_BASE64 = Base64.getEncoder().encodeToString("C2EA3014FAD65433FE7B010539E1AA23".getBytes());


    @Test
    public void test() throws NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
//        byte[] key = AesUtils.generateKey();
//        System.out.println(Base64.getEncoder().encodeToString(key));
//        byte[] key = Base64.getDecoder().decode("51ask+Waz+y8g6oT4Ap/mg==");
//
////        AesUtils.setTransformation(AesUtils.AES_CBC_PKCS5Padding);
//        byte[] ciphertext = AesUtils.encrypt(key, "hello".getBytes(StandardCharsets.UTF_8));
//        System.out.println(Base64.getEncoder().encodeToString(ciphertext));
//
//        byte[] plaintext = AesUtils.decrypt(key, ciphertext);
//
//        System.out.println(new String(plaintext));
//        System.out.println(1);

        String c = "YeeVNDqA5kPMbe0M5inf/w==";

        System.out.println(new String(AesUtils.decryptWithBase64(KEY_BASE64, c)));
    }

}
