package com.andreina.ushi.dao.criteria;

import java.util.Date;

public class EventoCriteria {
	private Long id;
	private String numRegistro;
    private Date fechaDesde;
    private Date fechaHasta;
    private String descripcion;
    private Long tipoEventoId;
    private Long animalId;
    private String valorDiagnostico;  
    private Long veterinarioId;
	
	EventoCriteria(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
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

	public Long getAnimalId() {
		return animalId;
	}

	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}

	public String getValorDiagnostico() {
		return valorDiagnostico;
	}

	public void setValorDiagnostico(String valorDiagnostico) {
		this.valorDiagnostico = valorDiagnostico;
	}

	public Long getVeterinarioId() {
		return veterinarioId;
	}

	public void setVeterinarioId(Long veterinarioId) {
		this.veterinarioId = veterinarioId;
	}
}
