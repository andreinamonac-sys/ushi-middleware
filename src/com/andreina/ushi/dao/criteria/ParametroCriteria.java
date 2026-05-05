package com.andreina.ushi.dao.criteria;

import java.util.Date;

public class ParametroCriteria {

	private Long id = null;
	private Date fechaDesde = null;
	private Date fechaHasta = null;
	private Long tagId = null;
	private Long animalId = null;
	private Long tipoParametroId = null;
	private Double valorMin = null;
	private Double valorMax = null;

	public ParametroCriteria() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
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

	public Double getValorMin() {
		return valorMin;
	}

	public void setValorMin(Double valorMin) {
		this.valorMin = valorMin;
	}

	public Double getValorMax() {
		return valorMax;
	}

	public void setValorMax(Double valorMax) {
		this.valorMax = valorMax;
	}
}
