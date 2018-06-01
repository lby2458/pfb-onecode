package com.pfb.onecode.gateway.controller.base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


@SuppressWarnings("unchecked")
public class BaseController {
	
	public Logger log = LoggerFactory.getLogger(BaseController.class);
	public static final String CONTENT_TYPE_JOSN = "application/json";
	public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
	public static final String CONTENT_TYPE_PLAIN = "text/plain";

	public Map<String, Object> parseRequestParams(HttpServletRequest request){
		Map<String, Object> paramMap = null;
		String contentType = request.getContentType();
		log.info("请求的Content-type:{}", contentType);
		if (contentType==null) {
			return paramMap;
		}
		if(contentType.startsWith(CONTENT_TYPE_JOSN) || contentType.startsWith(CONTENT_TYPE_PLAIN)){
			try {
				String reqStr = parseRequest2JsonStr(request);
				if (reqStr.isEmpty()) {
					return paramMap;
				}
				 paramMap = JSON.parseObject(reqStr,Map.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(contentType.startsWith(CONTENT_TYPE_FORM)){
			paramMap = new HashMap<>();
			Map<String, String[]> parameterMap = request.getParameterMap();
			for (Map.Entry<String, String[]> map : parameterMap.entrySet()) {
				String paramKey = map.getKey();
				String paramValue = map.getValue()[0];
				paramMap.put(paramKey, paramValue);
			}
		}
		return paramMap;
	}
	
	 /**
     * 解析请求参数为JSON字符串
     *
     * @param request 请求
     * @return
     * @throws Exception
     */
    public String parseRequest2JsonStr(HttpServletRequest request) throws Exception {
        // 读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        String reqStr = sb.toString();
        return reqStr;
    }

}
