package com.andreina.ushi.service.impl;

import java.util.List;

import com.andreina.ushi.service.MailService;

public class MailServiceMockImpl implements MailService{

	public  MailServiceMockImpl() {
		
	}
	
	@Override
	public void sendEmail(String para, String asunto, String contenido) {
	System.out.println("Enviando email a " +para+ ": Asunto " +asunto);
	System.out.println(contenido);
	System.out.println("Enviado.");
	}
	
	 
	
	
	
	@Override
	public void sendEmail(List<String> para, String asunto, String contenido) {
		
	}
}