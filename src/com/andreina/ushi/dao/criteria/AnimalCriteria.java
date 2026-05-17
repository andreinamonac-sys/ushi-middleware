package com.andreina.ushi.dao.criteria;

import java.util.Date;

import com.andreina.ushi.model.AbstractValueObject;

public class AnimalCriteria extends AbstractValueObject {

    private Long id;
    private String numRegistro;
    private String sexoDescripcion;
    private Date fechaNacimiento;
    private Date fechaAlta;
    private Date fechaBaja;
    private String granjaNif;
    private String numRegistroMadreInterna;

    public AnimalCriteria() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}

	public String getSexoDescripcion() {
		return sexoDescripcion;
	}

	public void setSexoDescripcion(String sexoDescripcion) {
		this.sexoDescripcion = sexoDescripcion;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getGranjaNif() {
		return granjaNif;
	}

	public void setGranjaNif(String granjaNif) {
		this.granjaNif = granjaNif;
	}

	public String getNumRegistroMadreInterna() {
		return numRegistroMadreInterna;
	}

	public void setNumRegistroMadreInterna(String numRegistroMadreInterna) {
		this.numRegistroMadreInterna = numRegistroMadreInterna;
	}

    
}

