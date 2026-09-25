package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

@ApplicationException(rollback=true)
public class ClemCaracterException extends AbstractException {

	private static final long serialVersionUID = -4790352691129504847L;
	
	private static final String situacion = "label.error.caracter.clem";
		
	public static final int codigo= 300;
	
	public ClemCaracterException(){
		super(situacion , codigo);
	}
		
	public ClemCaracterException(int codigo, String causa){
		super( causa, codigo);
	}
	
	public ClemCaracterException( String causa){
		super( causa, codigo);
	}

}
