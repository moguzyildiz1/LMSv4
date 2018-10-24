package com.gcit.lms.util;

public class ResultServe<T> {

	private T data;
	private String message;

	public ResultServe() {

	}

	public ResultServe(T t) {
		this.data = t;
	}

	public ResultServe(T t, String msg) {
		this.data = t;
		this.message = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
