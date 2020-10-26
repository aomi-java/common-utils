package tech.aomi.common.utils.crypto;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES 工具
 *
 * @author 田尘殇Sean sean.snow@live.com
 */
public class AesUtils {

    public static final String AES = "AES";

    /**
     * 加密
     *
     * @param bash64Key 密钥Base64
     * @param data      数据Base64
     * @return BASE64 字符串
     */
    public static String encryptWithBase64(String bash64Key, byte[] data) throws Exception {
        return Base64.getEncoder()
                .encodeToString(encrypt(AES,
                        Base64.getDecoder().decode(bash64Key),
                        data
                        )
                );
    }

    public static byte[] encrypt(String cipher, byte[] key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        return encrypt(Cipher.getInstance(cipher), key, data);
    }

    public static byte[] encrypt(Cipher cipher, byte[] key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key);
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        kgen.init(128, secureRandom);

        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), AES));
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param bash64Key 编码的秘钥
     * @param data      base64编码的数据
     * @return 解密的数据
     */
    public static byte[] decryptWithBase64(String bash64Key, String data) throws Exception {
        return decrypt(AES, Base64.getDecoder().decode(bash64Key), Base64.getDecoder().decode(data));
    }

    public static byte[] decrypt(String cipher, byte[] key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
        return decrypt(Cipher.getInstance(cipher), key, data);
    }

    public static byte[] decrypt(Cipher cipher, byte[] key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key);
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        kgen.init(128, secureRandom);

        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), AES));
        return cipher.doFinal(data);
    }
}
