/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: ParametrosInvalidosException.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.exception
 *  @Fecha: 28/09/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.exception;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class ParametrosInvalidosException extends AbstractException {

	private static final long serialVersionUID = 1L;
	private static final String situacion = "Los par\u00E1metros de entrada son inv\u00E1lidos";
	private static final Integer codigo = new Integer(200);

	public ParametrosInvalidosException() {
		super(situacion, codigo);
	}

	public ParametrosInvalidosException(final String message) {
		super(situacion, codigo);
	}
	
}
