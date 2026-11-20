package com.andreina.ushi.exception;

public class ContentException extends UshiException {
	
	public ContentException(String message) {
		super(message);
	}

	 public  ContentException(Throwable cause) {
	        super(cause);
	    }
	
	public ContentException(String message, Throwable cause) {
		super(message, cause);
	}
}
