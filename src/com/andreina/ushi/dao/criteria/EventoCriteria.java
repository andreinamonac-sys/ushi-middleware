package com.andreina.ushi.dao.criteria;

import java.util.Date;

public class EventoCriteria {
	private Long id=null;
	private Date fechaDesde=null;
	private Date fechaHasta=null;
	private String descripcion=null;
	
	private Long tipoEventoId=null;
	private String nombreTipoEvento=null;
	
	private Long animalId=null;
	private Long semillaId=null;
	private Long dosisId=null;
	private Long valorDiagnostico=null;
	private Long criaId=null;
	private Long veterinarioId=null;
	
	EventoCriteria(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getTipoEventoId() {
		return tipoEventoId;
	}

	public void setTipoEventoId(Long tipoEventoId) {
		this.tipoEventoId = tipoEventoId;
	}

	public String getNombreTipoEvento() {
		return nombreTipoEvento;
	}

	public void setNombreTipoEvento(String nombreTipoEvento) {
		this.nombreTipoEvento = nombreTipoEvento;
	}

	public Long getAnimalId() {
		return animalId;
	}

	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}

	public Long getSemillaId() {
		return semillaId;
	}

	public void setSemillaId(Long semillaId) {
		this.semillaId = semillaId;
	}

	public Long getDosisId() {
		return dosisId;
	}

	public void setDosisId(Long dosisId) {
		this.dosisId = dosisId;
	}

	public Long getValorDiagnostico() {
		return valorDiagnostico;
	}

	public void setValorDiagnostico(Long valorDiagnostico) {
		this.valorDiagnostico = valorDiagnostico;
	}

	public Long getCriaId() {
		return criaId;
	}

	public void setCriaId(Long criaId) {
		this.criaId = criaId;
	}

	public Long getVeterinarioId() {
		return veterinarioId;
	}

	public void setVeterinarioId(Long veterinarioId) {
		this.veterinarioId = veterinarioId;
	}

	
	
	

		
}
