package wxc.android.commons.lib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private static final int M2048 = 2048;
//    private static final int M16 = 16;
    private static final int M0XF0 = 0xf0;
    private static final int M0XF = 0xf;
    private static final int M4 = 4;

    /**
     * 计算文件的MD5校验码 大文件可能较为耗时
     */
    public static String getFileMd5Code(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return "";
        }
        MessageDigest mdInstance = getMessageDigestInstance();
        if (mdInstance == null) {
            return "";
        }
        FileInputStream fis = null;
        String result = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[M2048];
            int count;
            mdInstance.reset();
            while ((count = fis.read(buffer)) > 0) {
                mdInstance.update(buffer, 0, count);
            }
            result = bufferToHex(mdInstance.digest());
//            result = new BigInteger(1, mdInstance.digest()).toString(M16); // 返回的字符串长度可能小于32位, 服务器端的都为32位
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static MessageDigest getMessageDigestInstance() {
        MessageDigest instance = null;
        try {
            instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private static char[] sHexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int offset, int count) {
        StringBuffer stringbuffer = new StringBuffer(2 * count);
        int end = offset + count;
        for (int i = offset; i < end; i++) {
            appendHexPair(bytes[i], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c0 = sHexDigits[(bt & M0XF0) >> M4];
        // 取字节中低 4 位的数字转换
        char c1 = sHexDigits[bt & M0XF];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
