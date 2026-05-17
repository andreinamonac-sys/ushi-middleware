package com.andreina.ushi.model;

import java.util.Date;

public class Animal extends AbstractValueObject {
	
	private Long id=null;
	private String numRegistro=null;
	private Long padreExternoId=null;
	private Long madreExternaId=null;
	private Date fechaNacimiento=null;
	private Date fechaAlta=null;
	private Date fechaBaja=null;
	
	public Animal() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	
	
}
