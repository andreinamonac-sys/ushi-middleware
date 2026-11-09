package com.andreina.ushi.service.impl;

import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.exception.ContentException;
import com.andreina.ushi.exception.DataException;
import com.andreina.ushi.model.ContentData;
import com.andreina.ushi.model.ContentMetadata;
import com.andreina.ushi.service.ContentService;
import com.andreina.ushi.utils.FileUtils;


	

public class ContentServiceFileImpl implements ContentService {
	
	private static Logger logger = LogManager.getLogger(ContentServiceFileImpl.class);
	
	private static final String CMS_BASE_FOLDER = "C:/Users/galio/temporal/cms";
	
	
	public ContentServiceFileImpl() {
		
	}

	/**
	 * Genera la ruta del contenido en base a sus metadatos.
	 * @param metadata
	 * @return
	 */
	protected String getPath(ContentMetadata metadata) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(CMS_BASE_FOLDER)
		.append("/").append(metadata.getEntityType())
		.append("/").append(metadata.getEntityId())
		.append("/").append(metadata.getResourceType())
		.append('.')
		.append(metadata.getFormat());		
		return buffer.toString();
	}
	
	@Override
	public ContentData get(ContentMetadata metadata) throws ContentException {

        String contentPath = getPath(metadata);

        try {
            byte[] raw = FileUtils.readFully(contentPath);

            ContentData data = new ContentData();
            data.setMetadata(metadata);
            data.setRaw(raw);

            return data;

        } catch (IOException ioe) {
			logger.error("Leyendo "+contentPath+": "+ioe.getMessage(), ioe);			
			throw new ContentException(contentPath, ioe);
        }
	}

	@Override
	public List<ContentData> get(String entityType, String entityId) throws ContentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ContentData data) throws ContentException {
		ContentMetadata metadata = data.getMetadata();
		// En qué fichero en disco se va a guarda data.
		// Convencion de nombrado.
		String contentPath = getPath(metadata);
		try {
			FileUtils.write(data.getRaw(), contentPath, false);
		} catch (IOException ioe) {
			logger.error("Guardando "+contentPath+": "+ioe.getMessage(), ioe);			
			throw new ContentException(contentPath, ioe);
		}
	}

	@Override
	public void remove(ContentMetadata metadata) throws DataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) throws DataException {
		// TODO Auto-generated method stub
		
	}

}
