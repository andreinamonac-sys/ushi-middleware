package com.andreina.ushi.model;

public class Granja extends AbstractValueObject {
	private Long id=null;
	private String nif=null;
	private String calle=null;
	private Long cp=null;
	
	public Granja() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public Long getCp() {
		return cp;
	}

	public void setCp(Long cp) {
		this.cp = cp;
	}
	
}
