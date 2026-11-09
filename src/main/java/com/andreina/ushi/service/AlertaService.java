package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.AlertaDTO;

public interface AlertaService {

    List<AlertaDTO> findUltimas(int limit) throws ServiceException;

    boolean marcarLeida(Long alertaId) throws ServiceException;
}
