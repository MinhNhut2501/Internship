package com.r2s.findInternship.exception;

import java.util.Map;

import lombok.Getter;

@Getter
public class ServiceUnavailableErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Map<String, String> errors;
	public ServiceUnavailableErrorException(String msg)
	{
		super(msg);
	}
	public ServiceUnavailableErrorException(Map<String, String> params)
	{
		this.errors = params;
	}
	
}
