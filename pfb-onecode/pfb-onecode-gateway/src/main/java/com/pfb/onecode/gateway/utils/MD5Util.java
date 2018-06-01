package com.pfb.onecode.gateway.utils;

import java.security.MessageDigest;

public class MD5Util {
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};
	
	public static String md5EnCode(String input){
		StringBuffer sb = new StringBuffer();
		try {
			// 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 输入的字符串转换成字节数组
			byte[] bytes = input.getBytes();
			// 通过使用 update 方法处理数据,使指定的 byte数组更新摘要
			md.update(bytes);
			// 获得密文完成哈希计算,产生128 位的长整数
			byte[] digest = md.digest(bytes);
			// 
			// 转换为16字节
			for (byte b : digest) {
				sb.append(yteToHexString(b));
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sb.toString();
	}
	
	/**
	 * byte 转换16进制
	 * @param b
	 * @return:Object
	 * @author:libingyang
	 * @date:2018年5月29日 下午6:59:48
	 */
	public static Object yteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1]+hexDigits[d2];
	}
	
}
