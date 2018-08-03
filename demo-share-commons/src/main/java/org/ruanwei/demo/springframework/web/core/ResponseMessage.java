package org.ruanwei.demo.springframework.web.core;

public class ResponseMessage {
	private int code;
	private String message;

	public ResponseMessage(String message, int code) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
