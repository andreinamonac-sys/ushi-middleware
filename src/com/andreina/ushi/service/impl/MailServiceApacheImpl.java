package com.andreina.ushi.service.impl;

import java.util.List;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.andreina.ushi.service.MailService;

public class MailServiceApacheImpl implements MailService {
	public MailServiceApacheImpl() {
	}
	public void sendEmail(String para, String asunto, String contenido) {
		try {
			
			HtmlEmail email = new HtmlEmail();
			
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setStartTLSEnabled(true);
			email.setSSLOnConnect(false);
			//email.setAuthenticator(
					//new DefaultAuthenticator("ushi", "password"));
			email.setFrom("ushi.manage9@gmail.com");
			email.setAuthentication("ushi.manage9@gmail.com", "vcuq kvpg tvkx gfaw");
			email.setSubject(asunto);
			email.setMsg(contenido);
			email.addTo(para);
			
			email.setHtmlMsg("<html><body><h1><b><i>" + contenido + "</i></b></h1></body></html>");
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void sendEmail(List<String> destinatarios, String asunto, String contenido) {
		for(String destinatario:destinatarios) {
			sendEmail(destinatario, asunto, contenido);
		}
		
	}
}
