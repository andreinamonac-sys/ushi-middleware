package com.andreina.ushi.model;

public class AnimalDTO extends Animal {

	
	private String sexoDescripcion=null;
	private String granjaNif=null;
	private String crotalMadreInterna=null;
	
	public AnimalDTO() {
		
	}

	public String getSexoDescripcion() {
		return sexoDescripcion;
	}

	public void setSexoDescripcion(String sexoDescripcion) {
		this.sexoDescripcion = sexoDescripcion;
	}

	public String getGranjaNif() {
		return granjaNif;
	}

	public void setGranjaNif(String granjaNif) {
		this.granjaNif = granjaNif;
	}

	public String getCrotalMadreInterna() {
		return crotalMadreInterna;
	}

	public void setCrotalMadreInterna(String numRegistroMadreInterna) {
		this.crotalMadreInterna = numRegistroMadreInterna;
	}

	
}
	
	

