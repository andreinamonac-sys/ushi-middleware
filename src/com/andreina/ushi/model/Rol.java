package com.andreina.ushi.model;

public class Rol extends AbstractValueObject {
	private Long id=null;
	private String nombre=null;
	
	public Rol() {
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
