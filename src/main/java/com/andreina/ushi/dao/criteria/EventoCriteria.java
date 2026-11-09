package com.andreina.ushi.dao.criteria;

import java.util.Date;

public class EventoCriteria {
	private Long id = null;
	private String numRegistro= null;
    private Date fechaDesde= null;
    private Date fechaHasta= null;
	private String descripcion= null;
	private Long tipoEventoId= null;
	private String tipoEventoNombre= null;
	private Long animalId= null;
	private Long granjaId= null;
	private String valorDiagnostico= null; 
	private Long veterinarioId= null;
	private String veterinarioNombre= null;
	
	public EventoCriteria(){
		
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

	public String getTipoEventoNombre() {
		return tipoEventoNombre;
	}

	public void setTipoEventoNombre(String tipoEventoNombre) {
		this.tipoEventoNombre = tipoEventoNombre;
	}

	public Long getAnimalId() {
		return animalId;
	}

	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}

	public Long getGranjaId() {
		return granjaId;
	}

	public void setGranjaId(Long granjaId) {
		this.granjaId = granjaId;
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

	public String getVeterinarioNombre() {
		return veterinarioNombre;
	}

	public void setVeterinarioNombre(String veterinarioNombre) {
		this.veterinarioNombre = veterinarioNombre;
	}
}
