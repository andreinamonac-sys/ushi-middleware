package com.andreina.ushi.dao.criteria;

import com.andreina.ushi.model.AbstractValueObject;

public class AnimalCriteria extends AbstractValueObject {
	
	public final String ORDER_BY_ID= "a.id";
	public final String ORDER_BY_NUMREGISTRO= "a.num_registro";
	
	private Long id=null;
	private String numRegistro=null;
	private Long padreExternoId=null;
	private Long madreExternaId=null;
	
	private Long madreInternaId=null;
	private Long granjaId=null;
	private Long sexoId=null;
	
	
	public AnimalCriteria() {
		
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

	public Long getPadreExternoId() {
		return padreExternoId;
	}

	public void setPadreExternoId(Long padreExternoId) {
		this.padreExternoId = padreExternoId;
	}

	public Long getMadreExternaId() {
		return madreExternaId;
	}

	public void setMadreExternaId(Long madreExternaId) {
		this.madreExternaId = madreExternaId;
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

	
	
}

