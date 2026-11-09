package com.andreina.ushi.model;

public class DosisDTO extends Dosis {

	private String nombreTratamiento = null;

	public DosisDTO() {
		
	}

	public String getNombreTratamiento() {
		return nombreTratamiento;
	}

	public void setNombreTratamiento(String nombreTratamiento) {
		this.nombreTratamiento = nombreTratamiento;
	}

	
}
