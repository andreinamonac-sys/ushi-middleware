package com.andreina.ushi.model;

public class GranjaDTO extends Granja {
	
    private String usuarioNombre=null;
    
    	public GranjaDTO() {
    		
    	}

		public String getUsuarioNombre() {
			return usuarioNombre;
		}

		public void setUsuarioNombre(String usuarioNombre) {
			this.usuarioNombre = usuarioNombre;
		}
    	
}
