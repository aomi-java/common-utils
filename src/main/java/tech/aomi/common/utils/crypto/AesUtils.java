package tech.aomi.common.utils.crypto;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * AES 工具
 *
 * @author 田尘殇Sean sean.snow@live.com
 */
public class AesUtils {

    /**
     * 加密
     *
     * @param key  密钥
     * @param data 数据
     * @return BASE64 字符串
     */
    public static String encryptWithBase64(String key, String data) throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes(StandardCharsets.UTF_8));
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, secureRandom);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 解密
     *
     * @param key  密钥
     * @param data base64数据
     * @return 解密的数据
     */
    public static String decryptWithBase64(String key, String data) throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes(StandardCharsets.UTF_8));
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, secureRandom);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        byte[] decryptBytes = cipher.doFinal(Base64.decodeBase64(data));

        return new String(decryptBytes);
    }

}
