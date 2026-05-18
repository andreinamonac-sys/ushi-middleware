package com.andreina.ushi.dao;

import com.andreina.ushi.service.ServiceException;

public class DataException extends ServiceException {
	
	public DataException(){
		super();
	}
	
	public DataException(String message){
		super(message);
	}
	
	public DataException(String message, Throwable t){
		super(message, t);
	}

}
