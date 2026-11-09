package com.andreina.ushi.service.impl;

import org.mindrot.jbcrypt.BCrypt;

import com.andreina.ushi.service.EncryptionService;

public class EncryptionServiceBCryptImpl implements EncryptionService {
	public EncryptionServiceBCryptImpl() {
		
	}

	@Override
	public String encrypt(String textoPlano) {
		if (textoPlano == null) {
			return null;
		}
		return BCrypt.hashpw(textoPlano, BCrypt.gensalt());
	}

	@Override
	public boolean verify(String textoPlano, String textoEncriptado) {
		if (textoPlano == null || textoEncriptado == null) {
			return false;
		}
		if (!textoEncriptado.startsWith("$2a$") && !textoEncriptado.startsWith("$2b$")
				&& !textoEncriptado.startsWith("$2y$")) {
			return textoPlano.equals(textoEncriptado);
		}
		return BCrypt.checkpw(textoPlano, textoEncriptado);
	}
	
}
