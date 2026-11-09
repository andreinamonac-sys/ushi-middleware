package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.exception.MailException;

public interface MailService {
	
	/**
	 * Envía un correo electrónico a un destinatario.
	 * @param para Dirección de correo del destinatario
	 * @param asunto Asunto del correo
	 * @param contenido Contenido del correo
	 */
	public void sendEmail(String para, String asunto, String contenido, String firma) throws MailException;
	

	/**
	 * Envía un correo electrónico a múltiples destinatarios.
	 * @param para Lista de direcciones de correo de los destinatarios
	 * @param asunto Asunto del correo
	 * @param contenido Contenido del correo
	 */
	public void sendEmail(List<String> para, String asunto, String contenido, String firma) throws MailException;
}
