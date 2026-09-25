/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: GCESujetoObligadoException.java
 *  @Paquete: mx.gob.imss.ctirss.delta.exception.clasificacion
 *  @Fecha: 09/01/2013
 */
package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

@ApplicationException(rollback = false)
public class GCESujetoObligadoException extends AbstractException {

	/** Serial version */
	private static final long serialVersionUID = -4513888144585758163L;

	private static final String situacion = "Error a nivel de negocio, verifique bit-cora para mas detalles.";
	private static final Integer codigo = new Integer(600);
	private int code;

	public GCESujetoObligadoException() {
		super(situacion, codigo);
	}

	public GCESujetoObligadoException(String message) {
		super(message);
	}

	public GCESujetoObligadoException(String message, int code) {
		super(message, code);
	}

	public Integer getCode() {
		return this.code;
	}

}
