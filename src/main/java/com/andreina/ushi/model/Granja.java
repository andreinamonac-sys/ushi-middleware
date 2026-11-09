package com.andreina.ushi.model;

public class Granja extends AbstractValueObject {
	
	private Long id=null;
	private String codigoRega =null;
	private String nif=null;
	private String calle=null;
	private Integer numero=null;
	private Long cp=null;
	private Long usuarioId=null;
	
	public Granja() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoRega() {
		return codigoRega;
	}

	public void setCodigoRega(String codigoRega) {
		this.codigoRega = codigoRega;
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Long getCp() {
		return cp;
	}

	public void setCp(Long cp) {
		this.cp = cp;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	
}
