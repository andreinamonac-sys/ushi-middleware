package com.andreina.ushi.model;

public class Tratamiento extends AbstractValueObject {

	private Long id = null;
	private String nombre = null;

	public Tratamiento() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
