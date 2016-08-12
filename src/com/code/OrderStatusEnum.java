package com.code;

public enum OrderStatusEnum {
	OPENED("opened"),
	CANCELED("canceled"),
	CLOSED("closed")
	;
	
	private String code;

	private OrderStatusEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
