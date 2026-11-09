package com.andreina.ushi.dao.criteria;

public class GranjaCriteria {
	private Long id=null;
	private String codigoRega = null;
	private String nif=null;
	private String calle=null;
	private Integer numero=null;
	private Integer cp=null;
	private String localidad=null;
	private String provincia=null;
	private String encargado=null;
	
	public GranjaCriteria(){
		
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

	public Integer getCp() {
		return cp;
	}

	public void setCp(Integer cp) {
		this.cp = cp;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getEncargado() {
		return encargado;
	}

	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}
	
}
