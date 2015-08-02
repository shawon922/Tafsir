package com.jolpai.tafsir.communication;

import org.json.JSONObject;

import java.io.Serializable;

// TODO: Auto-generated Javadoc

/**
 * The Class WebResponseInfo.
 */
public class Response implements Serializable {

	String status;
	String errorMsg;
	String type;
	JSONObject data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

}
