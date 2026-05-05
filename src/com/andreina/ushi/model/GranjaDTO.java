package com.andreina.ushi.model;

public class GranjaDTO extends Granja {
	
    private Long usuarioId=null;
    private String usuarioNombre=null;
    
    	public GranjaDTO() {
    		
    	}

		public Long getUsuarioId() {
			return usuarioId;
		}

		public void setUsuarioId(Long usuarioId) {
			this.usuarioId = usuarioId;
		}

		public String getUsuarioNombre() {
			return usuarioNombre;
		}

		public void setUsuarioNombre(String usuarioNombre) {
			this.usuarioNombre = usuarioNombre;
		}
    	
}
