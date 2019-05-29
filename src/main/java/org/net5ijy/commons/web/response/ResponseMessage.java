package org.net5ijy.commons.web.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ResponseMessage implements Serializable {

	public static final long serialVersionUID = -8990791130742368169L;

	public static final String MESSAGE_SUCCESS = "操作成功";

	public static final String MESSAGE_ERROR = "操作失败";

	public static final int MESSAGE_SUCCESS_CODE = 0;

	public static final int MESSAGE_ERROR_CODE = 99;

	private int code;

	private String message;

	private Map<String, Object> data = new HashMap<String, Object>();

	public ResponseMessage() {
		super();
	}

	public ResponseMessage(int code, String message) {
		this();
		this.code = code;
		this.message = message;
	}

	public static ResponseMessage success() {
		return new ResponseMessage(MESSAGE_SUCCESS_CODE, MESSAGE_SUCCESS);
	}

	public static ResponseMessage error() {
		return new ResponseMessage(MESSAGE_ERROR_CODE, MESSAGE_ERROR);
	}

	public ResponseMessage code(int code) {
		this.code = code;
		return this;
	}

	public ResponseMessage message(String message) {
		this.message = message;
		return this;
	}

	public ResponseMessage addAttribute(String name, Object val) {
		this.data.put(name, val);
		return this;
	}
}
