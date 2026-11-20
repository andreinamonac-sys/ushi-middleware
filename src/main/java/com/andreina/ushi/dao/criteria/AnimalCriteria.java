package com.andreina.ushi.dao.criteria;

import java.util.Date;

import com.andreina.ushi.model.AbstractValueObject;

public class AnimalCriteria extends AbstractValueObject {
	
	
	private Long id = null;
	private String crotal = null;
	private String tagAsociado = null;
	private Long sexoId = null;
	private Date fechaNacimientoDesde = null;
	private Date fechaNacimientoHasta = null;
	private Date fechaAltaDesde = null;
	private Date fechaAltaHasta = null;
	private Date fechaBajaDesde = null;
	private Date fechaBajaHasta = null;
	private Long granjaId = null;
	private Long padreExternoId = null;
	private Long madreExternaId = null;
	private String crotalMadreInterna = null;

    public AnimalCriteria() {
    	
    }

    
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCrotal() {
		return crotal;
	}

	public void setCrotal(String crotal) {
		this.crotal = crotal;
	}

	public String getTagAsociado() {
		return tagAsociado;
	}

	public void setTagAsociado(String tagAsociado) {
		this.tagAsociado = tagAsociado;
	}

	public Long getSexoId() {
		return sexoId;
	}

	public void setSexoId(Long sexoId) {
		this.sexoId = sexoId;
	}

	public Date getFechaNacimientoDesde() {
		return fechaNacimientoDesde;
	}

	public void setFechaNacimientoDesde(Date fechaNacimientoDesde) {
		this.fechaNacimientoDesde = fechaNacimientoDesde;
	}

	public Date getFechaNacimientoHasta() {
		return fechaNacimientoHasta;
	}

	public void setFechaNacimientoHasta(Date fechaNacimientoHasta) {
		this.fechaNacimientoHasta = fechaNacimientoHasta;
	}

	public Date getFechaAltaDesde() {
		return fechaAltaDesde;
	}

	public void setFechaAltaDesde(Date fechaAltaDesde) {
		this.fechaAltaDesde = fechaAltaDesde;
	}

	public Date getFechaAltaHasta() {
		return fechaAltaHasta;
	}

	public void setFechaAltaHasta(Date fechaAltaHasta) {
		this.fechaAltaHasta = fechaAltaHasta;
	}

	public Date getFechaBajaDesde() {
		return fechaBajaDesde;
	}

	public void setFechaBajaDesde(Date fechaBajaDesde) {
		this.fechaBajaDesde = fechaBajaDesde;
	}

	public Date getFechaBajaHasta() {
		return fechaBajaHasta;
	}

	public void setFechaBajaHasta(Date fechaBajaHasta) {
		this.fechaBajaHasta = fechaBajaHasta;
	}

	public Long getGranjaId() {
		return granjaId;
	}

	public void setGranjaId(Long granjaId) {
		this.granjaId = granjaId;
	}

	public Long getPadreExternoId() {
		return padreExternoId;
	}

	public void setPadreExternoId(Long padreExternoId) {
		this.padreExternoId = padreExternoId;
	}

	public Long getMadreExternaId() {
		return madreExternaId;
	}

	public void setMadreExternaId(Long madreExternaId) {
		this.madreExternaId = madreExternaId;
	}

	public String getCrotalMadreInterna() {
		return crotalMadreInterna;
	}

	public void setCrotalMadreInterna(String crotalMadreInterna) {
		this.crotalMadreInterna = crotalMadreInterna;
	}

	
    
}

