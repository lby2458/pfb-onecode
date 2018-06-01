package com.pfb.onecode.gateway.controller.base;

import com.pfb.onecode.gateway.controller.absbase.AbstractBase;
import com.pfb.onecode.gateway.controller.enums.ResultCode;

import lombok.Data;


public class OuterResponseBase extends AbstractBase{
	private static final long serialVersionUID = 1L;
	
	/** 相应码 */
	private String return_code = ResultCode.SUCCESS.code();
	/** 相应信息 */
	private String return_msg = ResultCode.SUCCESS.msg();
	
	private String  page ;
	/** 签名 */
	private String sign;
	/** 随机字符串 */
	private String nonce_str;
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
