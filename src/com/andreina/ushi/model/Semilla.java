package com.andreina.ushi.model;

public class Semilla extends AbstractValueObject {

	private Long id = null;
	private Integer itp = null;
	private Integer meritoNeto = null;

	public Semilla() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getItp() {
		return itp;
	}

	public void setItp(Integer itp) {
		this.itp = itp;
	}

	public Integer getMeritoNeto() {
		return meritoNeto;
	}

	public void setMeritoNeto(Integer meritoNeto) {
		this.meritoNeto = meritoNeto;
	}
}
