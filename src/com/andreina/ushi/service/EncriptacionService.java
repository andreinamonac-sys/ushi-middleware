package com.andreina.ushi.service;

public interface EncriptacionService {
	
	public String encrypt(String textoPlano);
	// public String decrypt(String textoEncriptado);
	public boolean verify(String textoPlano, String textoEncriptado);
	

}
