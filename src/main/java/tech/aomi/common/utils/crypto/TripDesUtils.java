package tech.aomi.common.utils.crypto;

import org.apache.commons.codec.binary.Base64;
import tech.aomi.common.utils.binary.CodecUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
    public static String encryptWithHex(String hexKey, byte[] data) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException {
        return encryptWithHex(DesUtils.DEFAULT_CIPHER, hexKey, data);
    }

    public static String encryptWithHex(String cipher, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encryptWithHex(Cipher.getInstance(cipher), hexKey, data);
    }

    public static String encryptWithHex(Cipher cipher, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        byte[] result = encrypt(cipher, CodecUtils.hex2byte(hexKey), data);
        return CodecUtils.hexString(result);
    }

    public static String encryptWithBase64(String base64Key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encryptWithBase64(DesUtils.DEFAULT_CIPHER, base64Key, data);
    }

    public static String encryptWithBase64(String cipher, String base64Key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encryptWithBase64(Cipher.getInstance(cipher), base64Key, data);
    }

    public static String encryptWithBase64(Cipher cipher, String base64Key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        byte[] result = encrypt(cipher, Base64.decodeBase64(base64Key), data);
        return Base64.encodeBase64String(result);
    }

    public static byte[] encrypt(byte[] key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encrypt(DesUtils.DEFAULT_CIPHER, key, data);
    }

    public static byte[] encrypt(String cipher, byte[] key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        return encrypt(Cipher.getInstance(cipher), key, data);
    }

    public static byte[] encrypt(Cipher cipher, byte[] key, byte[] data) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if (key.length == 8) {
            return DesUtils.encrypt(cipher, key, data);
        } else {
            List<byte[]> keys = getKeys(key);
            return encrypt(cipher, keys.get(0), keys.get(1), keys.get(2), data);
        }
    }

    public static String decryptWithHex(String hexKey, String hexData) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithHex(DesUtils.DEFAULT_CIPHER, hexKey, hexData);
    }

    public static String decryptWithHex(String cipher, String hexKey, String hexData) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithHex(Cipher.getInstance(cipher), hexKey, hexData);
    }

    /**
     * 3des解密
     *
     * @param cipher  cipher
     * @param hexKey  十六进制格式的密钥
     * @param hexData 十六进制格式的数据
     */
    public static String decryptWithHex(Cipher cipher, String hexKey, String hexData) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return decryptWithHex(cipher, hexKey, CodecUtils.hex2byte(hexData));
    }

    public static String decryptWithHex(String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithHex(DesUtils.DEFAULT_CIPHER, hexKey, data);
    }

    public static String decryptWithHex(String cipher, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithHex(Cipher.getInstance(cipher), hexKey, data);
    }

    public static String decryptWithHex(Cipher cipher, String hexKey, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        byte[] result = decrypt(cipher, CodecUtils.hex2byte(hexKey), data);
        return CodecUtils.hexString(result);
    }


    public static String decryptWithBase64(String base64Key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithBase64(DesUtils.DEFAULT_CIPHER, base64Key, base64Data);
    }

    public static String decryptWithBase64(String cipher, String base64Key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithBase64(Cipher.getInstance(cipher), base64Key, base64Data);
    }

    public static String decryptWithBase64(Cipher cipher, String base64Key, String base64Data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return decryptWithBase64(cipher, base64Key, Base64.decodeBase64(base64Data));
    }

    public static String decryptWithBase64(String base64Key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithBase64(DesUtils.DEFAULT_CIPHER, base64Key, data);
    }

    public static String decryptWithBase64(String cipher, String base64Key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decryptWithBase64(Cipher.getInstance(cipher), base64Key, data);
    }

    public static String decryptWithBase64(Cipher cipher, String base64Key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        byte[] result = decrypt(cipher, Base64.decodeBase64(base64Key), data);
        return Base64.encodeBase64String(result);
    }


    public static byte[] decrypt(byte[] key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decrypt(DesUtils.DEFAULT_CIPHER, key, data);
    }

    public static byte[] decrypt(String cipher, byte[] key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        return decrypt(Cipher.getInstance(cipher), key, data);
    }

    public static byte[] decrypt(Cipher cipher, byte[] key, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        if (key.length == 8) {
            return DesUtils.decrypt(cipher, key, data);
        } else {
            List<byte[]> keys = getKeys(key);
            return decrypt(cipher, keys.get(0), keys.get(1), keys.get(2), data);
        }
    }


    private static byte[] encrypt(Cipher cipher, byte[] key1, byte[] key2, byte[] key3, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        byte[] rslt = DesUtils.encrypt(cipher, key1, data);
        rslt = DesUtils.decrypt(cipher, rslt, key2);
        rslt = DesUtils.encrypt(cipher, rslt, key3);
        return rslt;
    }

    private static byte[] decrypt(Cipher cipher, byte[] key1, byte[] key2, byte[] key3, byte[] data) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        byte[] rslt = DesUtils.decrypt(cipher, key3, data);
        rslt = DesUtils.encrypt(cipher, rslt, key2);
        rslt = DesUtils.decrypt(cipher, rslt, key1);
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
