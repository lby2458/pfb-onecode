package com.pfb.onecode.gateway.controller.ipay;

import org.slf4j.Logger;

import com.pfb.onecode.gateway.controller.base.OuterResponseBase;


public interface IPayTrans {
	 OuterResponseBase execute(String reqStr, Logger log);
	 
	 boolean verifyParameter(String reqStr, OuterResponseBase response, Logger log);
	 
}
