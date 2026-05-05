package com.andreina.ushi.model;

public class TipoEvento extends AbstractValueObject {

	private Long id = null;
	private String nombre = null;

	public TipoEvento() {
		
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
