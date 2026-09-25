/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:AbstractException.java
 *  @Paquete:mx.gob.imss.delta.framework.base.exception
 *  @Fecha:07/02/2012
 */
package mx.gob.imss.ctirss.delta.framework.base.exception;

/**
 * @author Lucio Duran Silva
 *
 */
public class AbstractException extends Exception {
	
	
	
	private static final long serialVersionUID = 1L;
	/*Situacion que genera la excepcion*/
	private String situacion;
	/*Codigo de error asignado a esta excepcion*/
	private Integer codigo;
	
	public AbstractException(){
		super();
	}
	
	public AbstractException(String message){
		super(message);
	}
	
	public AbstractException(Throwable e){
		super();
		String message =		e.getMessage();
		this.situacion = message;
	}
	

	/**
	 * 
	 * @param situacion : Mensaje de la situacion del error o de la excepcion
	 * @param codigo : Identificador del error
	 */
	public AbstractException(String situacion, Integer codigo){
		super(situacion);
		this.codigo = codigo;
		this.situacion = situacion;
		
		//TODO: LUDS implementar la funcionalidad del mapa de codigo de exceptions
		//AbstractException.addExceptionToMap( this.codigo , this.getClass());
		
	}

	/**
	 * @return the situacion
	 */
	public String getSituacion() {
		return situacion;
	}

	/**
	 * @param situacion the situacion to set
	 */
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	/**
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	

}
