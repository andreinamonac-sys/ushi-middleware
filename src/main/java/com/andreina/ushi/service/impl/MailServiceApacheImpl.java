package com.andreina.ushi.service.impl;

import java.util.List;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import com.andreina.ushi.exception.MailException;
import com.andreina.ushi.service.MailService;

public class MailServiceApacheImpl implements MailService {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;

    private static final String FROM_EMAIL = "ushi.manage9@gmail.com";
    private static final String FROM_NAME = "Ushi";

    @Override
    public void sendEmail(String para, String asunto, String contenido, String firma) throws MailException {
        try {
            Email email = new SimpleEmail();

            email.setHostName(SMTP_HOST);
            email.setSmtpPort(SMTP_PORT);
            email.setStartTLSEnabled(true);
            email.setSSLOnConnect(false);

            email.setFrom(FROM_EMAIL, FROM_NAME);

            String password = System.getenv("GMAIL_APP_PASSWORD");
            email.setAuthentication(FROM_EMAIL, password);

            email.setSubject(asunto);

            String mensaje = contenido;
            if (firma != null && !firma.isBlank()) {
                mensaje += "\n\n" + firma;
            }

            email.setMsg(mensaje);
            email.addTo(para);
            email.send();

        } catch (Exception e) {
            throw new MailException("Error enviando email a " + para, e);
        }
    }

    @Override
    public void sendEmail(List<String> destinatarios, String asunto, String contenido, String firma)
            throws MailException {

        if (destinatarios == null || destinatarios.isEmpty()) {
            throw new MailException("La lista de destinatarios está vacía.");
        }

        for (String destinatario : destinatarios) {
            sendEmail(destinatario, asunto, contenido, firma);
        }
    }
}