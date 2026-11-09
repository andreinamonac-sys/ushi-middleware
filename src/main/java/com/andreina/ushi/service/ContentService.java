package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.exception.ContentException;
import com.andreina.ushi.exception.DataException;
import com.andreina.ushi.model.ContentData;
import com.andreina.ushi.model.ContentMetadata;

/**
 * Servicio común de infraestructura para gestion de contenidos (CMS).
 */
public interface ContentService {
	
	// public ContentData get(String contentId) throws CMSException;
	
	/**
	 * Recupera un contenido en base a sus metadatos.
	 * @param metadata
	 * @return
	 * @throws ContentException
	 */
	public ContentData get(ContentMetadata metadata) throws ContentException; 
		
	 /**
	  * Recupera todos los contenidos asociados a una entidad.
	  * @param entityId
	  * @return
	  * @throws ContentException
	  */	 
	public List<ContentData> get(String entityType, String entityId) throws ContentException; 	
		
	/**
	 * Guarda un contenido en el gestor. 
	 * @param metadata Metadatos del contenido.
	 * @param data Raw data.
	 * @throws ContentException
	 */	
	public void save(ContentData data) throws ContentException;
	
	
	public void remove(ContentMetadata metadata) throws DataException;
			
	
	public void remove(String id) throws DataException;	
			
}
