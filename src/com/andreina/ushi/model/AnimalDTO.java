package com.andreina.ushi.model;

public class AnimalDTO extends Animal {

	private Long madreInternaId=null;
	
	private Long granjaId=null;
	
	private Long sexoId=null;
	private String sexoDescripcion=null;
	
	public AnimalDTO() {
		
	}

	public Long getMadreInternaId() {
		return madreInternaId;
	}

	public void setMadreInternaId(Long madreInternaId) {
		this.madreInternaId = madreInternaId;
	}

	public Long getGranjaId() {
		return granjaId;
	}

	public void setGranjaId(Long granjaId) {
		this.granjaId = granjaId;
	}

	public Long getSexoId() {
		return sexoId;
	}

	public void setSexoId(Long sexoId) {
		this.sexoId = sexoId;
	}

	public String getSexoDescripcion() {
		return sexoDescripcion;
	}

	public void setSexoDescripcion(String sexoDescripcion) {
		this.sexoDescripcion = sexoDescripcion;
	}

}
	
	

