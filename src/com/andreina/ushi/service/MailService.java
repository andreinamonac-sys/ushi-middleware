package com.andreina.ushi.service;

import java.sql.Connection;
import java.util.List;

public interface MailService {
	
	/**
	 * Envía un correo electrónico a un destinatario.
	 * @param para Dirección de correo del destinatario
	 * @param asunto Asunto del correo
	 * @param contenido Contenido del correo
	 */
	public void sendEmail(String para, String asunto, String contenido, String firma) throws Exception;
	

	/**
	 * Envía un correo electrónico a múltiples destinatarios.
	 * @param para Lista de direcciones de correo de los destinatarios
	 * @param asunto Asunto del correo
	 * @param contenido Contenido del correo
	 */
	public void sendEmail(List<String> para, String asunto, String contenido, String firma) throws Exception;
}
