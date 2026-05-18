package com.andreina.ushi.service;

import com.andreina.ushi.model.UsuarioLoginDTO;

public interface AuthenticationService {
	
	    UsuarioLoginDTO login(String email, String password) throws Exception;

	    UsuarioLoginDTO findByEmail(String email) throws Exception;
	}

