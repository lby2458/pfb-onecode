package com.pfb.onecode.gateway.controller.enums;

public enum ResultCode {
	/** 成功 */
	SUCCESS("000000", "操作成功");
	private final String code;
	private final String msg;

	private ResultCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String code() {
		return code;
	}

	public String msg() {
		return msg;
	}
}
