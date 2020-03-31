package tech.aomi.common.utils.binary;

import org.apache.commons.codec.binary.Base64;
import tech.aomi.common.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Sean sean.snow@live.com
 */
public class Base64Utils {


    /**
     * base64 转图片
     *
     * @param file   输出的文件
     * @param base64 base64的图片信息字符串
     * @throws IOException 写入文件失败时抛出
     */
    public static void toImageFile(File file, String base64) throws IOException {
        if (StringUtils.isEmpty(base64)) {
            return;
        }

        byte[] base64Bytes = Base64.decodeBase64(base64);

        OutputStream out = new FileOutputStream(file);
        out.write(base64Bytes);
        out.flush();
        out.close();
    }

}
