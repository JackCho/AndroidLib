package com.jit.lib.model;

import com.google.gson.annotations.SerializedName;

public class ResultEntity<T> {

	@SerializedName(value = "ResultCode")
	private int resultCode;
	@SerializedName(value = "Message")
	private String message;
	@SerializedName(value = "IsSuccess")
	private boolean isSuccess;
	@SerializedName(value = "Data")
	private T data;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
