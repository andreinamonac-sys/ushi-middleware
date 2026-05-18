package com.andreina.ushi.model;

public class UshiException extends Exception {
	
	public UshiException(){
		super();
	}
	
	public UshiException(String message){
		super(message);
	}
	
	public UshiException(String message, Throwable t){
		super(message, t);
	}
	
}
