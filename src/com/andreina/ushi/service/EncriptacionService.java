package com.andreina.ushi.service;

public interface EncriptacionService {
	
	public String encrypt(String textoPlano) throws Exception;
	// public String decrypt(String textoEncriptado);
	public boolean verify(String textoPlano, String textoEncriptado) throws Exception;
	

}
