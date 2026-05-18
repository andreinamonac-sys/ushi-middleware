package com.andreina.ushi.model;

public class UsuarioLoginDTO extends AbstractValueObject {

	private Long id=null;
	private String email=null;
	private String password=null;
	private Long rolId=null;
	private String rolNombre=null;
	private Boolean activo=null;
	
	   
	    public UsuarioLoginDTO() {
	    }


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public Long getRolId() {
			return rolId;
		}


		public void setRolId(Long rolId) {
			this.rolId = rolId;
		}


		public String getRolNombre() {
			return rolNombre;
		}


		public void setRolNombre(String rolNombre) {
			this.rolNombre = rolNombre;
		}


		public Boolean getActivo() {
			return activo;
		}


		public void setActivo(Boolean activo) {
			this.activo = activo;
		}


}
