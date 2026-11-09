package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Provincia;

public interface ProvinciaService {

    List<Provincia> findAll() throws ServiceException;
}
