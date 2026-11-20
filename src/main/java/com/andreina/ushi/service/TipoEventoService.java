package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.TipoEvento;

public interface TipoEventoService {
	
	public List<TipoEvento> findAll() throws ServiceException;

}
