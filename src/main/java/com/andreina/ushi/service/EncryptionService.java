package com.andreina.ushi.service;

import com.andreina.ushi.exception.ServiceException;

public interface EncryptionService {
	
	public String encrypt(String textoPlano) throws ServiceException;
	// public String decrypt(String textoEncriptado);
	public boolean verify(String textoPlano, String textoEncriptado) throws ServiceException;
	

}
