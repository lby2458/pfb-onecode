package com.pfb.onecode.gateway.controller.outer;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pfb.onecode.gateway.common.CommonConstant;
import com.pfb.onecode.gateway.controller.base.BaseController;
import com.pfb.onecode.gateway.controller.base.OuterResponseBase;
import com.pfb.onecode.gateway.controller.ipay.IPayTrans;
import com.pfb.onecode.gateway.utils.PropertiesLoader;
import com.pfb.onecode.gateway.utils.SignUtil;

@Controller
public class OuterGateWay extends BaseController{
	
	@Resource
	Map<String, IPayTrans> ipay;
	@Resource
	PropertiesLoader propertiesLoader;
	
	@RequestMapping({"gateway","appGateWay"})
	@ResponseBody
	public OuterResponseBase process(HttpServletRequest req, HttpServletResponse Response){
		String uri = req.getRequestURI();
		OuterResponseBase resp = new OuterResponseBase();
		log.info("请求地址:{}",uri);
		Map<String, Object> map = parseRequestParams(req);
		if (map==null) {
			resp.setReturn_msg("请求类型错误");
			return resp;
		}
		String serviceType = String.valueOf(map.get("service_type"));
		if (!StringUtils.isEmpty(serviceType)) {
			IPayTrans payTrans = ipay.get(serviceType);
			if (payTrans == null) {
				log.info("上送的服务类型(service_type={})不存在", serviceType);
				resp.setReturn_code("");
				resp.setReturn_msg("service_type is not exist!");
				return resp;
			}
			// 验参
			String mapToString = map.toString();
			boolean verifyParameter = payTrans.verifyParameter(mapToString, resp, log);
			if (!verifyParameter) {
				resp.setReturn_code("");
				resp.setReturn_msg("");
				return resp;
			}
			// 验签
			if (!uri.contains("appGateWay")) {
				String key = propertiesLoader.getProperty(CommonConstant.CONFIG_MAC_KEY);
				boolean checkSign = SignUtil.checkSign(map, key, log);
				if (!checkSign) {
					resp.setReturn_code("");
					resp.setReturn_msg("");
					return resp;
				}
			}
			// 路由
			resp = payTrans.execute(map.toString(), log);
		}else{
			resp.setReturn_code("");
			resp.setReturn_msg("");
			return resp;
		}
		return resp;
	}
}
