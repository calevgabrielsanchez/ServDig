/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DomicilioNoValidoException.java
 *  @Paquete:mx.gob.imss.ctirss.delta.exception.domicilio
 *  @Fecha:27/04/2012
 */
package mx.gob.imss.ctirss.delta.exception.serie;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author SRG
 *
 */
@ApplicationException(rollback=false)
public class SeriesNoLocalizadasException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Series no localizadas en BDU");
	
	/**
	 * Constructor por omision
	 */
	public SeriesNoLocalizadasException(){
		super(situacion , codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de erro
	 * @param situacion
	 */
	public SeriesNoLocalizadasException(String situacion){
		super(situacion , codigo);
	}

}
