package com.andreina.ushi.service;

public class MailException extends ServiceException {
	
	public MailException(){
		super();
	}
	
	public MailException(String message){
		super(message);
	}
	
	public MailException(String message, Throwable t){
		super(message, t);
	}

}
