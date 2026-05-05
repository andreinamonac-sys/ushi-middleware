package com.andreina.ushi.model;

public class DosisDTO extends Dosis {

	private Long tratamientoId = null;

	public DosisDTO() {
		
	}

	public Long getTratamientoId() {
		return tratamientoId;
	}

	public void setTratamientoId(Long tratamientoId) {
		this.tratamientoId = tratamientoId;
	}
}
