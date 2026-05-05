package com.andreina.ushi.service.impl;

import org.mindrot.jbcrypt.BCrypt;

import com.andreina.ushi.service.EncriptacionService;

public class EncriptacionServiceBCryptImpl implements EncriptacionService {
	public EncriptacionServiceBCryptImpl() {
		
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
		return BCrypt.checkpw(textoPlano, textoEncriptado);
	}
	
}
