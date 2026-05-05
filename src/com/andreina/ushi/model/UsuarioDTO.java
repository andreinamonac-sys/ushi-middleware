package com.andreina.ushi.model;

public class UsuarioDTO extends Usuario {
   
	private Long rolId=null;
	private String nombreRol=null;
	
	public UsuarioDTO() {
		
	}

	public Long getRolId() {
		return rolId;
	}

	public void setRolId(Long rolId) {
		this.rolId = rolId;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	

	
}
