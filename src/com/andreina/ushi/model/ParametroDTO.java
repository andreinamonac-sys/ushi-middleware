package com.andreina.ushi.model;

public class ParametroDTO extends Parametro{
	
	private Long tagId=null;
	private Long animalId=null;
	
	private Long tipoParametroId=null;
	private String tipoParametroNombre=null;
	
	public ParametroDTO() {
		
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getAnimalId() {
		return animalId;
	}

	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}

	public Long getTipoParametroId() {
		return tipoParametroId;
	}

	public void setTipoParametroId(Long tipoParametroId) {
		this.tipoParametroId = tipoParametroId;
	}

	public String getTipoParametroNombre() {
		return tipoParametroNombre;
	}

	public void setTipoParametroNombre(String tipoParametroNombre) {
		this.tipoParametroNombre = tipoParametroNombre;
	}
	
	
	
}
