package tech.aomi.common.utils.crypto;

import org.apache.commons.codec.binary.Base64;
import tech.aomi.common.utils.binary.CodecUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 三 des 工具包
 */
public class TripDesUtils {

    /**
     * 三des加密使用十六进制编码
     */
    public static String encryptWithHex(String hexKey, byte[] data) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidAlgorithmParameterException {
        return encryptWithHex(DesUtils.CIPHER_ECB_PKCS5Padding, hexKey, data);
    }

    public static String encryptWithHex(String algorithm, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] result = encrypt(algorithm, CodecUtils.hex2byte(hexKey), data);
        return CodecUtils.hexString(result);
    }

    public static String encryptWithBase64(String base64Key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return encryptWithBase64(DesUtils.CIPHER_ECB_PKCS5Padding, base64Key, data);
    }

    public static String encryptWithBase64(String algorithm, String base64Key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] result = encrypt(algorithm, Base64.decodeBase64(base64Key), data);
        return Base64.encodeBase64String(result);
    }

    public static byte[] encrypt(byte[] key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return encrypt(DesUtils.CIPHER_ECB_PKCS5Padding, key, data);
    }


    public static byte[] encrypt(String algorithm, byte[] key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        if (key.length == 8) {
            return DesUtils.encrypt(algorithm, key, data);
        } else {
            List<byte[]> keys = getKeys(key);
            return encrypt(algorithm, keys.get(0), keys.get(1), keys.get(2), data);
        }
    }

    public static byte[] decryptWithHex(String hexKey, String hexData) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decryptWithHex(DesUtils.CIPHER_ECB_PKCS5Padding, hexKey, hexData);
    }

    /**
     * 3des解密
     *
     * @param algorithm algorithm
     * @param hexKey    十六进制格式的密钥
     * @param hexData   十六进制格式的数据
     */
    public static byte[] decryptWithHex(String algorithm, String hexKey, String hexData) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithHex(algorithm, hexKey, CodecUtils.hex2byte(hexData));
    }

    public static byte[] decryptWithHex(String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decryptWithHex(DesUtils.CIPHER_ECB_PKCS5Padding, hexKey, data);
    }

    public static byte[] decryptWithHex(String algorithm, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decrypt(algorithm, CodecUtils.hex2byte(hexKey), data);
    }

    public static byte[] decryptWithBase64(String base64Key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decryptWithBase64(DesUtils.CIPHER_ECB_PKCS5Padding, base64Key, base64Data);
    }

    public static byte[] decryptWithBase64(String algorithm, String base64Key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithBase64(algorithm, base64Key, Base64.decodeBase64(base64Data));
    }

    public static byte[] decryptWithBase64(String base64Key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decryptWithBase64(DesUtils.CIPHER_ECB_PKCS5Padding, base64Key, data);
    }

    public static byte[] decryptWithBase64(String algorithm, String base64Key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decrypt(algorithm, Base64.decodeBase64(base64Key), data);
    }

    public static byte[] decrypt(byte[] key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decrypt(DesUtils.CIPHER_ECB_PKCS5Padding, key, data);
    }

    public static byte[] decrypt(String algorithm, byte[] key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (key.length == 8) {
            return DesUtils.decrypt(algorithm, key, data);
        } else {
            List<byte[]> keys = getKeys(key);
            return decrypt(algorithm, keys.get(0), keys.get(1), keys.get(2), data);
        }
    }


    private static byte[] encrypt(String algorithm, byte[] key1, byte[] key2, byte[] key3, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] rslt = DesUtils.encrypt(algorithm, key1, data);
        rslt = DesUtils.decrypt(algorithm, key2, rslt);
        rslt = DesUtils.encrypt(algorithm, key3, rslt);
        return rslt;
    }

    private static byte[] decrypt(String algorithm, byte[] key1, byte[] key2, byte[] key3, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] rslt = DesUtils.decrypt(algorithm, key3, data);
        rslt = DesUtils.encrypt(algorithm, key2, rslt);
        rslt = DesUtils.decrypt(algorithm, key1, rslt);
        return rslt;
    }

    private static List<byte[]> getKeys(byte[] key) {
        List<byte[]> keys = new ArrayList<>();

        byte[] key1 = new byte[8];
        byte[] key2 = new byte[8];
        byte[] key3 = new byte[8];

        if (key.length == 16) {
            System.arraycopy(key, 0, key1, 0, 8);
            System.arraycopy(key, 8, key2, 0, 8);
            key3 = key1;
        } else if (key.length == 24) {
            System.arraycopy(key, 0, key1, 0, 8);
            System.arraycopy(key, 8, key2, 0, 8);
            System.arraycopy(key, 16, key3, 0, 8);
        } else {
            throw new IllegalArgumentException("Trip Des only support key length:8,16,24");
        }
        keys.add(key1);
        keys.add(key2);
        keys.add(key3);
        return keys;
    }
}
