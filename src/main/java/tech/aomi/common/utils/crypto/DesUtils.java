package tech.aomi.common.utils.crypto;


import org.apache.commons.codec.binary.Base64;
import tech.aomi.common.utils.binary.CodecUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * des 加密工具包
 */
public class DesUtils {

    public static final String CIPHER_ECB_PKCS5Padding = "DES/ECB/PKCS5Padding";

    public static final String CIPHER_ECB_NoPadding = "DES/ECB/NoPadding";

    public static final String DEFAULT_CIPHER = CIPHER_ECB_PKCS5Padding;

    public static final String DES = "DES";

    public static String encryptWithHex(byte[] key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encryptWithHex(DEFAULT_CIPHER, key, data);
    }

    public static String encryptWithHex(String cipher, byte[] key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encryptWithHex(Cipher.getInstance(cipher), key, data);
    }

    public static String encryptWithHex(Cipher cipher, byte[] key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        byte[] result = encrypt(cipher, key, data);
        return CodecUtils.hexString(result);
    }

    public static String encryptWithHex(String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encryptWithHex(DEFAULT_CIPHER, hexKey, data);
    }

    public static String encryptWithHex(String cipher, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encryptWithHex(Cipher.getInstance(cipher), hexKey, data);
    }

    public static String encryptWithHex(Cipher cipher, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        byte[] result = encrypt(cipher, CodecUtils.hex2byte(hexKey), data);
        return CodecUtils.hexString(result);
    }

    public static String encryptWithBase64(byte[] key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encryptWithBase64(DEFAULT_CIPHER, key, data);
    }

    public static String encryptWithBase64(String cipher, byte[] key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encryptWithBase64(Cipher.getInstance(cipher), key, data);
    }

    public static String encryptWithBase64(Cipher cipher, byte[] key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        byte[] result = encrypt(cipher, key, data);
        return Base64.encodeBase64String(result);
    }

    public static String encryptWithBase64(String base64Key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return encryptWithBase64(DEFAULT_CIPHER, base64Key, data);
    }

    public static String encryptWithBase64(String cipher, String base64Key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return encryptWithBase64(Cipher.getInstance(cipher), base64Key, data);
    }

    public static String encryptWithBase64(Cipher cipher, String base64Key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        byte[] key = Base64.decodeBase64(base64Key);
        byte[] result = encrypt(cipher, key, data);
        return Base64.encodeBase64String(result);
    }

    public static byte[] encrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encrypt(DEFAULT_CIPHER, key, data);
    }

    public static byte[] encrypt(String cipher, byte[] key, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return encrypt(Cipher.getInstance(cipher), key, data);
    }

    public static byte[] encrypt(Cipher cipher, byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return encrypt(cipher, key, data, 0, data.length);
    }

    public static byte[] encrypt(Cipher cipher, byte[] key, byte[] data, int offset, int length) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, DES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data, offset, length);
    }


    public static byte[] decryptWithHex(String hexKey, String hexData) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithHex(DEFAULT_CIPHER, hexKey, hexData);
    }

    public static byte[] decryptWithHex(String cipher, String hexKey, String hexData) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithHex(Cipher.getInstance(cipher), hexKey, hexData);
    }

    public static byte[] decryptWithHex(Cipher cipher, String hexKey, String hexData) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return decryptWithHex(cipher, hexKey, CodecUtils.hex2byte(hexData));
    }

    public static byte[] decryptWithHex(String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithHex(DEFAULT_CIPHER, hexKey, data);
    }

    public static byte[] decryptWithHex(String cipher, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithHex(Cipher.getInstance(cipher), hexKey, data);
    }

    public static byte[] decryptWithHex(Cipher cipher, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return decrypt(cipher, CodecUtils.hex2byte(hexKey), data);
    }


    public static byte[] decryptWithBase64(byte[] key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithBase64(DEFAULT_CIPHER, key, base64Data);
    }

    public static byte[] decryptWithBase64(String cipher, byte[] key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithBase64(Cipher.getInstance(cipher), key, base64Data);
    }

    public static byte[] decryptWithBase64(Cipher cipher, byte[] key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return decrypt(cipher, key, Base64.decodeBase64(base64Data));
    }

    public static byte[] decryptWithBase64(String base64Key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithBase64(DEFAULT_CIPHER, base64Key, base64Data);
    }

    public static byte[] decryptWithBase64(String cipher, String base64Key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithBase64(Cipher.getInstance(cipher), base64Key, base64Data);
    }

    public static byte[] decryptWithBase64(Cipher cipher, String base64Key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        byte[] key = Base64.decodeBase64(base64Key);
        return decrypt(cipher, key, Base64.decodeBase64(base64Data));
    }

    public static byte[] decrypt(byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decrypt(DEFAULT_CIPHER, key, data);
    }

    public static byte[] decrypt(String cipher, byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decrypt(Cipher.getInstance(cipher), key, data);
    }

    public static byte[] decrypt(Cipher cipher, byte[] key, byte[] data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return decrypt(cipher, key, data, 0, data.length);
    }

    public static byte[] decrypt(Cipher cipher, byte[] key, byte[] data, int offset, int length) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, DES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data, offset, length);
    }

}
