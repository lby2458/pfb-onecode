/**
 * Project Name:bibabel
 * Package Name:com.sunfund.bibabel.common.util
 * Date:2017/12/13 17:50
 * SunFund.com Inc.
 * Copyright (c) 2017, wang.yi@sunfund.com All Rights Reserved.
 */
package pfb.onecode.api.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:MD5Util
 * Date:     2017/12/13 17:50
 * @author jdy
 * @version V1.0
 * @since JDK 1.8
 */
public class MD5Util {
    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(strObj.getBytes());
            byte[] res = md.digest();
//             md.digest(); //该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(res);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] args) throws IOException {
        String ss = "123456";
        String aa = GetMD5Code(ss);
        System.out.println(aa);





    }
}
