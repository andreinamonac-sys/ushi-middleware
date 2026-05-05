package com.andreina.ushi.model;

public class Animal extends AbstractValueObject {
	
	private Long id=null;
	private String numRegistro=null;
	private Long padreExternoId=null;
	private Long madreExternaId=null;
	// TODO: fechaNacimiento
	// TODO: fechaAlta opcional
	// TODO: fechaBaja
	
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
	
	
}
