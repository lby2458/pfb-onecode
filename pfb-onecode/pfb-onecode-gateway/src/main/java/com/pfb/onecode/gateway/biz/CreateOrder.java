package com.pfb.onecode.gateway.biz;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.pfb.onecode.gateway.controller.base.OuterResponseBase;
import com.pfb.onecode.gateway.controller.ipay.IPayTrans;

@Component(value="CREATE_ORDER")
public class CreateOrder implements IPayTrans{

	@Override
	public OuterResponseBase execute(String reqStr, Logger log) {
		System.out.println("请求参数"+reqStr);
		OuterResponseBase resp = new OuterResponseBase();
		return resp;
	}

	@Override
	public boolean verifyParameter(String reqStr, OuterResponseBase response, Logger log) {
		return true;
	}

}
