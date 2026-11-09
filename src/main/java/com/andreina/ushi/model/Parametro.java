package com.andreina.ushi.model;

import java.util.Date;

public class Parametro extends AbstractValueObject {
	
	private Long id =null;
	private Date fechaHora =null;
	private Long tagId =null;
	private Long animalId =null;
	private Long tipoParametroId =null;
	private Double valorParametro =null;
	private String estado = null; 
	
	public Parametro() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
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

	public Double getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(Double valorParametro) {
		this.valorParametro = valorParametro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	
	
}
