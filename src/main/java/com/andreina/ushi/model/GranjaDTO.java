package com.andreina.ushi.model;

public class GranjaDTO extends Granja {
	
    private String usuarioNombre=null;
    private String localidadNombre = null;
    
    	public GranjaDTO() {
    		
    	}

		public String getUsuarioNombre() {
			return usuarioNombre;
		}

		public void setUsuarioNombre(String usuarioNombre) {
			this.usuarioNombre = usuarioNombre;
		}

		public String getLocalidadNombre() {
			return localidadNombre;
		}

		public void setLocalidadNombre(String localidadNombre) {
			this.localidadNombre = localidadNombre;
		}
		
		
    	
}
