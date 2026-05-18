package com.andreina.ushi.service;

import com.andreina.ushi.model.UshiException;

public class ServiceException extends UshiException {
	
	public ServiceException(){
		super();
	}
	
	public ServiceException(String message){
		super(message);
	}
	
	public ServiceException(String message, Throwable t){
		super(message, t);
	}

}
