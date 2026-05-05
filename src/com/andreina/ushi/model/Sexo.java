package com.andreina.ushi.model;

public class Sexo extends AbstractValueObject {

		private Long id=null;
		private String descripcion=null;
		
		
		public Sexo() {
			
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		
		
}
