package com.andreina.ushi.model;

public class Dosis extends AbstractValueObject {

	private Long id = null;
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
