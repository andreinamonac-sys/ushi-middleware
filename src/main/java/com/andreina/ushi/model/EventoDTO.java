package com.andreina.ushi.model;

public class EventoDTO extends Evento{
	
	private String nombreTipoEvento=null;
	private String crotal=null;
	private String nombreVeterinario=null;
	private String apellidoVeterinario=null;
	private String nombreTratamieto = null;
	private Long numDosis=null;
	
	public EventoDTO(){
		
	}

	public String getNombreTipoEvento() {
		return nombreTipoEvento;
	}

	public void setNombreTipoEvento(String nombreTipoEvento) {
		this.nombreTipoEvento = nombreTipoEvento;
	}

	public String getCrotal() {
		return crotal;
	}

	public void setCrotal(String crotal) {
		this.crotal = crotal;
	}

	public String getNombreVeterinario() {
		return nombreVeterinario;
	}

	public void setNombreVeterinario(String nombreVeterinario) {
		this.nombreVeterinario = nombreVeterinario;
	}

	public String getApellidoVeterinario() {
		return apellidoVeterinario;
	}

	public void setApellidoVeterinario(String apellidoVeterinario) {
		this.apellidoVeterinario = apellidoVeterinario;
	}

	public String getNombreTratamieto() {
		return nombreTratamieto;
	}

	public void setNombreTratamieto(String nombreTratamieto) {
		this.nombreTratamieto = nombreTratamieto;
	}

	public Long getNumDosis() {
		return numDosis;
	}

	public void setNumDosis(Long numDosis) {
		this.numDosis = numDosis;
	}

	
		
}
