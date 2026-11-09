package com.andreina.ushi.model;

public class Dosis extends AbstractValueObject {

	private Long id = null;
	private Long tratamientoId = null;
	private Integer plazoSiguiente = null;
	private Integer numDosis = null;

	public Dosis() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTratamientoId() {
		return tratamientoId;
	}
	
	public void setTratamientoId(Long tratamientoId) {
		this.tratamientoId = tratamientoId;
	}

	public Integer getPlazoSiguiente() {
		return plazoSiguiente;
	}

	public void setPlazoSiguiente(Integer plazoSiguiente) {
		this.plazoSiguiente = plazoSiguiente;
	}

	public Integer getNumDosis() {
		return numDosis;
	}

	public void setNumDosis(Integer numDosis) {
		this.numDosis = numDosis;
	}
}
