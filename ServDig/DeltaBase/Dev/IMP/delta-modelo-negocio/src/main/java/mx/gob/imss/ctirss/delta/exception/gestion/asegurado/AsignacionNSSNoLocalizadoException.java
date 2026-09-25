package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class AsignacionNSSNoLocalizadoException extends AbstractException implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6313464643050500864L;
	
	private static final String situacion = "NSS no localizado";
	private static final String error = "NSS no localizado";
	private static final Integer codigo = new Integer(10008000);

    public AsignacionNSSNoLocalizadoException() {
    	super(situacion, codigo);
    }

    public AsignacionNSSNoLocalizadoException(String causa) {
    	super(error + causa, codigo);
    }
    
    public AsignacionNSSNoLocalizadoException(String causa, Integer codigoError) {
    	super(error + causa, codigoError);
    }

}
