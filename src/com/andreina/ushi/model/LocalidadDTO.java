package com.andreina.ushi.model;

public class LocalidadDTO extends Localidad {

	private Long provinciaId=null;
	private String provinciaNombre=null;
	
	private Long granjaId=null;
	private String granjaNombre=null;
	
	public LocalidadDTO() {
		super();
	}

	public Long getProvinciaId() {
		return provinciaId;
	}

	public void setProvinciaId(Long provinciaId) {
		this.provinciaId = provinciaId;
	}

	public String getProvinciaNombre() {
		return provinciaNombre;
	}

	public void setProvinciaNombre(String provinciaNombre) {
		this.provinciaNombre = provinciaNombre;
	}

	public Long getGranjaId() {
		return granjaId;
	}

	public void setGranjaId(Long granjaId) {
		this.granjaId = granjaId;
	}

	public String getGranjaNombre() {
		return granjaNombre;
	}

	public void setGranjaNombre(String granjaNombre) {
		this.granjaNombre = granjaNombre;
	}
	
	
}
