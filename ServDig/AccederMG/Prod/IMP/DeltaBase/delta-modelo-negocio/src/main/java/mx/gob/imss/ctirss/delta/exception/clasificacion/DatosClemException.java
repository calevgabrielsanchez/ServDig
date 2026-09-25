package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

@ApplicationException(rollback=true)
public class DatosClemException extends AbstractException {

	private static final long serialVersionUID = -4790352691129504847L;
	
	private static final String situacion = "field.clem.error";
		
	public static final int codigo= 200;
	
	public DatosClemException(){
		super(situacion , codigo);
	}
		
	public DatosClemException(int codigo, String causa){
		super( causa, codigo);
	}
	
	public DatosClemException( String causa){
		super( causa, codigo);
	}

}
