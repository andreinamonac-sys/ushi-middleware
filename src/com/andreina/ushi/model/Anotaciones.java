package com.andreina.ushi.model;

public @interface Anotaciones {
// para la concatenación de select
	/*LISTA DE CONDICIONES
	 * List<String> condiciones= new ArrayList<String>()
	 */
	
	
	/*Capa Lógica de negocio: Service
	 * 
	 */
	
	/*
	 * No puedo hacer un get time sobre un null. Va a pasar con los getStarDate.
	 * No se puede poner [criteria.getStartDate().getTime()]
	 * Ponemos [[criteria.getStartDate().getTime()==null ? null : new java.sq....]
	 */
	
	/*La sintaxis de las querys en java es la misma que la del workbench.
	 */
	/*
	 * jasypt para encriptar la contraseña en el servicio de usuario
	 */
	/*Búsqueda en findById y findByEmail:
	 * Para no tener que repetir la misma query y lo mismo en el resultset
	 *  método que me cargue el resultset y retorne el objeto loadNext(Resultset rs)
	 *  
	 *  El DTO tiene que venir con lo mismo independientemente de la consulta
	 */
}
