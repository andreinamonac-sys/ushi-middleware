package com.andreina.ushi.service;

import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.UsuarioLoginDTO;

public interface AuthenticationService {
	
	    UsuarioLoginDTO login(String email, String password) throws ServiceException;

	    UsuarioLoginDTO findByEmail(String email) throws ServiceException;
	}

