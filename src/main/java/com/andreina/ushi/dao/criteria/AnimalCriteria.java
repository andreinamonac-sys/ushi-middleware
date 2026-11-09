package com.andreina.ushi.dao.criteria;

import java.util.Date;

import com.andreina.ushi.model.AbstractValueObject;

public class AnimalCriteria extends AbstractValueObject {

    private Long id = null;
    private String crotal= null;
    private String sexoDescripcion= null;
    private Long sexoId= null;
    private Date fechaNacimiento= null;
    private Date fechaNacimientoDesde= null;
    private Date fechaNacimientoHasta= null;
    private Date fechaAlta= null;
    private Date fechaAltaDesde= null;
    private Date fechaAltaHasta= null;
    private Date fechaBaja= null;
    private Date fechaBajaDesde= null;
    private Date fechaBajaHasta= null;
    private Long granjaId= null;
    private String granjaNif= null;
    private Long padreExternoId= null;
    private Long madreExternaId= null;
    private Long madreInternaId= null;
    private String crotalMadreInterna= null;

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

	public String getSexoDescripcion() {
		return sexoDescripcion;
	}

	public void setSexoDescripcion(String sexoDescripcion) {
		this.sexoDescripcion = sexoDescripcion;
	}

	public Long getSexoId() {
		return sexoId;
	}

	public void setSexoId(Long sexoId) {
		this.sexoId = sexoId;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
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

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
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

	public String getGranjaNif() {
		return granjaNif;
	}

	public void setGranjaNif(String granjaNif) {
		this.granjaNif = granjaNif;
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

	public Long getMadreInternaId() {
		return madreInternaId;
	}

	public void setMadreInternaId(Long madreInternaId) {
		this.madreInternaId = madreInternaId;
	}

	public String getCrotalMadreInterna() {
		return crotalMadreInterna;
	}

	public void setCrotalMadreInterna(String crotalMadreInterna) {
		this.crotalMadreInterna = crotalMadreInterna;
	}

	
    
}

