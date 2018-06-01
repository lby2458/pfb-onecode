package com.pfb.onecode.gateway.main;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.pfb.onecode.gateway.utils.PropertiesLoader;

public class TestMain {
	public static void main(String[] args) {
		
		HashMap<String, String > map = new HashMap<>();
		map.put("service_type", "CREATE_ORDER");
		map.put("name", " 张三");
		map.put("sign", "123");
		JSON json = (JSON) JSON.toJSON(map);
		System.out.println(json);
	}
}
