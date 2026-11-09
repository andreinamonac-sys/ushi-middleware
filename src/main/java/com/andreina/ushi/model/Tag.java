package com.andreina.ushi.model;

import java.util.Date;

public class Tag extends AbstractValueObject {

	private Long id = null;
	private String numero = null;
	private String estado = null;
	private Double bateria = null;
	private Date ultimaSenal = null;
	private String incidencia = null;
	private String tipo = null;
	private String versionSoftware = null;

	public Tag() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Double getBateria() {
		return bateria;
	}

	public void setBateria(Double bateria) {
		this.bateria = bateria;
	}

	public Date getUltimaSenal() {
		return ultimaSenal;
	}

	public void setUltimaSenal(Date ultimaSenal) {
		this.ultimaSenal = ultimaSenal;
	}

	public String getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getVersionSoftware() {
		return versionSoftware;
	}

	public void setVersionSoftware(String versionSoftware) {
		this.versionSoftware = versionSoftware;
	}

}
