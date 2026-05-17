package com.andreina.ushi.service;

import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.service.impl.MailServiceApacheImpl;
import com.andreina.ushi.service.impl.MailServiceMockImpl;

public class MailServiceTest {
	
	private MailService mailService=null;
	
	public MailServiceTest() {
		//mailService=new MailServiceMockImpl();
		mailService=new MailServiceApacheImpl();
	}
	
	public void testEnviarEmail()throws Exception {
		
		mailService.sendEmail("joseantoniolp.teacher@gmail.com", "Volamos", "La OO es alucinante ",null);
	}

	public void testEnviarEmailMultiple()throws Exception {
		
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add("aland1213aa@gmail.com");
		destinatarios.add("aymanesshaibi06@gmail.com");
		destinatarios.add("ushi.manage9@gmail.com");
		
		mailService.sendEmail(destinatarios, "Volamos", "La OO es alucinante ",null);
	}
	
	public static void main(String args[])	 throws Exception {
		MailServiceTest test =new MailServiceTest();
		test.testEnviarEmail();
		test.testEnviarEmailMultiple();
	}
}
