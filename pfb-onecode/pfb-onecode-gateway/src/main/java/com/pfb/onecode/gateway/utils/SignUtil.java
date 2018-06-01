package com.pfb.onecode.gateway.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;


public class SignUtil {
	public static boolean checkSign(Map<String, Object> map, String key, Logger log) {
		String sign = map.get("sign").toString();
		if (sign.isEmpty()) {
			return false;
		}
		log.info("请求签名：{}", sign);
		map.remove("sign");
		String signs = getSign(map, key, log);
		System.out.println("实际签名："+signs);
		log.info("实际签名：{}", signs);
		if (!sign.equals(signs)) {
			return false;
		}
		return true;
	}

	public static String getSign(Map<String, Object> map, String sign, Logger log) {
		ArrayList<String> arrlist = new ArrayList<>();

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null &&  StringUtils.isNotBlank(String.valueOf(entry.getValue()))) {
				arrlist.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		String[] arr2 = new String[arrlist.size()];
		String[] array = arrlist.toArray(arr2);
		Arrays.sort(array, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sbs = new StringBuilder();
		for (int i = 0; i < arrlist.size(); i++) {
			sbs.append(array[i]);
		}
		String result = sbs.toString();
		result = result.substring(0, result.length() - 1);
		result += sign;
		String md5EnCode = MD5Util.md5EnCode(result).toUpperCase();
		return md5EnCode;
	}

}
