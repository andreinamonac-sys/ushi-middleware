package com.andreina.ushi.model;

import java.util.Date;

public class Tag extends AbstractValueObject {

	private Long id = null;
	private String numero = null;
	private Date ultimaActualizacion = null;
	private String versionSoftware = null;
	private String tipo = null;
	private String incidencias = null;

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

	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}

	public String getVersionSoftware() {
		return versionSoftware;
	}

	public void setVersionSoftware(String versionSoftware) {
		this.versionSoftware = versionSoftware;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(String incidencias) {
		this.incidencias = incidencias;
	}
}
