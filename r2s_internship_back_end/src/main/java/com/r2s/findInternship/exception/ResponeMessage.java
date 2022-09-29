package com.r2s.findInternship.exception;

import lombok.Data;

@Data
public class ResponeMessage {
	private int httpCode;
	private String message;
	private String path;
	public ResponeMessage()
	{
		
	}
	public ResponeMessage(int code, String message)
	{
		this.httpCode =  code;
		this.message =  message;
	}
	public ResponeMessage(int code, String message, String path)
	{
		this.httpCode =  code;
		this.message =  message;
		this.path = path;
	}
}
