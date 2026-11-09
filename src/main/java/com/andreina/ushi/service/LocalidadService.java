package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.LocalidadDTO;

public interface LocalidadService {

    List<LocalidadDTO> findAll() throws ServiceException;
}
