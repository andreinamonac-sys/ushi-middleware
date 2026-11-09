package com.andreina.ushi.model;

public class LocalidadDTO extends Localidad {

	
	private String provinciaNombre=null;
	private String granjaNombre=null;
	
	public LocalidadDTO() {
		super();
	}

		public String getProvinciaNombre() {
		return provinciaNombre;
	}

	public void setProvinciaNombre(String provinciaNombre) {
		this.provinciaNombre = provinciaNombre;
	}

	public String getGranjaNombre() {
		return granjaNombre;
	}

	public void setGranjaNombre(String granjaNombre) {
		this.granjaNombre = granjaNombre;
	}
	
	
}
