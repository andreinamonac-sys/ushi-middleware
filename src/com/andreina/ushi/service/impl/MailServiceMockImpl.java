package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.service.MailService;

public class MailServiceMockImpl implements MailService {

	public MailServiceMockImpl() {

	}

	@Override
	public void sendEmail(String para, String asunto, String contenido, String firma) throws Exception {
		System.out.println("Enviado email a"+para+": Asunto "+asunto);
		System.out.println(contenido);
		System.out.println("Enviado");
	}

	@Override
	public void sendEmail(List<String> para, String asunto, String contenido, String firma) throws Exception {
	    for (String destinatario : para) {
	        sendEmail(destinatario, asunto, contenido, firma);
	    }
	    System.out.println("Enviados");
	}

}
