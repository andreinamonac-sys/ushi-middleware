package com.ushi;

import com.andreina.ushi.exception.ContentException;
import com.andreina.ushi.model.ContentData;
import com.andreina.ushi.model.ContentMetadata;
import com.andreina.ushi.service.ContentService;
import com.andreina.ushi.service.impl.ContentServiceFileImpl;

public class ContentServiceTest {

	public static void main(String args[]) {
		ContentService cs = new ContentServiceFileImpl();
		ContentData c = new ContentData();
		c.setRaw("hola".getBytes());
		
		ContentMetadata cm = new ContentMetadata();
		cm.setEntityType("usuario");
		cm.setEntityId("22");
		cm.setResourceType("contrato");
		cm.setFormat("txt");
		c.setMetadata(cm);
		try {
			cs.save(c);
		} catch (ContentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
