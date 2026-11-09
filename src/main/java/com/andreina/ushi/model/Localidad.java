package com.andreina.ushi.model;

public class Localidad extends AbstractValueObject {

		private Long id=null;
		private String nombre=null;
		private Long provinciaId=null;
		private Long granjaId=null;
		
		public Localidad() {
	
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

		public Long getProvinciaId() {
			return provinciaId;
		}

		public void setProvinciaId(Long provinciaId) {
			this.provinciaId = provinciaId;
		}

		public Long getGranjaId() {
			return granjaId;
		}

		public void setGranjaId(Long granjaId) {
			this.granjaId = granjaId;
		}
		
		
		
}
