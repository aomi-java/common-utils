package tech.aomi.common.utils.crypto;


import org.apache.commons.codec.binary.Base64;
import tech.aomi.common.utils.binary.CodecUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * des 加密工具包
 */
public class DesUtils {

    public static final String CIPHER_ECB_PKCS5Padding = "DES/ECB/PKCS5Padding";

    public static final String CIPHER_CBC_PKCS5Padding = "DES/CBC/PKCS5Padding";

    public static final String CIPHER_ECB_NoPadding = "DES/ECB/NoPadding";

    public static final String DEFAULT_CIPHER = CIPHER_CBC_PKCS5Padding;

    public static final String DES = "DES";

    private static volatile String algorithm = null;

    private static String algorithm() {
        if (null == algorithm) {
            return DEFAULT_CIPHER;
        }
        return algorithm;
    }

    /**
     * 生成符合DES要求的密钥, 长度为64位(8字节).
     */
    public static byte[] generateDesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    public static String encryptWithHex(byte[] key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return encryptWithHex(algorithm(), key, data);
    }

    public static String encryptWithHex(String algorithm, byte[] key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] result = encrypt(algorithm, key, data);
        return CodecUtils.hexString(result);
    }

    public static String encryptWithHex(String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return encryptWithHex(algorithm(), hexKey, data);
    }

    public static String encryptWithHex(String algorithm, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] result = encrypt(algorithm, CodecUtils.hex2byte(hexKey), data);
        return CodecUtils.hexString(result);
    }

    public static String encryptWithBase64(byte[] key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return encryptWithBase64(algorithm(), key, data);
    }

    public static String encryptWithBase64(String algorithm, byte[] key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] result = encrypt(algorithm, key, data);
        return Base64.encodeBase64String(result);
    }

    public static String encryptWithBase64(String base64Key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        return encryptWithBase64(algorithm(), base64Key, data);
    }

    public static String encryptWithBase64(String algorithm, String base64Key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] key = Base64.decodeBase64(base64Key);
        byte[] result = encrypt(algorithm, key, data);
        return Base64.encodeBase64String(result);
    }

    public static byte[] encrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return encrypt(algorithm(), key, data);
    }


    public static byte[] encrypt(String algorithm, byte[] key, byte[] data) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return encrypt(algorithm, key, data, 0, data.length);
    }

    public static byte[] encrypt(String algorithm, byte[] key, byte[] data, int offset, int length) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, DES);

        Cipher cipher = Cipher.getInstance(algorithm);
        if (algorithm.contains("CBC")) {
            // 偏移量
            IvParameterSpec iv = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        }

        return cipher.doFinal(data, offset, length);
    }


    public static byte[] decryptWithHex(String hexKey, String hexData) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decryptWithHex(algorithm(), hexKey, hexData);
    }

    public static byte[] decryptWithHex(String algorithm, String hexKey, String hexData) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithHex(algorithm, hexKey, CodecUtils.hex2byte(hexData));
    }

    public static byte[] decryptWithHex(String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decryptWithHex(algorithm(), hexKey, data);
    }

    public static byte[] decryptWithHex(String algorithm, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decrypt(algorithm, CodecUtils.hex2byte(hexKey), data);
    }

    public static byte[] decryptWithBase64(byte[] key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decryptWithBase64(algorithm(), key, base64Data);
    }


    public static byte[] decryptWithBase64(String algorithm, byte[] key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decrypt(algorithm, key, Base64.decodeBase64(base64Data));
    }

    public static byte[] decryptWithBase64(String base64Key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decryptWithBase64(algorithm(), base64Key, base64Data);
    }

    public static byte[] decryptWithBase64(String algorithm, String base64Key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] key = Base64.decodeBase64(base64Key);
        return decrypt(algorithm, key, Base64.decodeBase64(base64Data));
    }

    public static byte[] decrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decrypt(algorithm(), key, data);
    }

    public static byte[] decrypt(String algorithm, byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decrypt(algorithm, key, data, 0, data.length);
    }

    public static byte[] decrypt(String algorithm, byte[] key, byte[] data, int offset, int length) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, DES);

        Cipher cipher = Cipher.getInstance(algorithm);
        if (algorithm.contains("CBC")) {
            // 偏移量
            IvParameterSpec iv = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        }

        return cipher.doFinal(data, offset, length);
    }

    public static void setAlgorithm(String a) {
        algorithm = a;
    }

}
