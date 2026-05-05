package com.andreina.ushi.model;

import java.util.Date;

public class Parametro extends AbstractValueObject {
	
	private Long id=null;
	private Date fechaHora=null;
	private Double valorParametro=null;
	
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

	public Double getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(Double valorParametro) {
		this.valorParametro = valorParametro;
	}
	
	
}
