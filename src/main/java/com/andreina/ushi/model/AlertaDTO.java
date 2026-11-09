package com.andreina.ushi.model;


public class AlertaDTO extends Alerta{
	 
	private Boolean leida=null;
	private String crotal=null;
	private String numeroTag = null;
	
	public AlertaDTO() {
		
	}

	public Boolean getLeida() {
		return leida;
	}

	public void setLeida(Boolean leida) {
		this.leida = leida;
	}

	public String getCrotal() {
		return crotal;
	}

	public void setCrotal(String crotal) {
		this.crotal = crotal;
	}

	public String getNumeroTag() {
		return numeroTag;
	}

	public void setNumeroTag(String numeroTag) {
		this.numeroTag = numeroTag;
	}

	
}
