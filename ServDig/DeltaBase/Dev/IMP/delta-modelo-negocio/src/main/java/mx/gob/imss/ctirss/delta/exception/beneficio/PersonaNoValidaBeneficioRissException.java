package mx.gob.imss.ctirss.delta.exception.beneficio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class PersonaNoValidaBeneficioRissException 
	extends AbstractException{

	private static final long serialVersionUID = -1L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Persona no v\u00e1lida para solicitar el beneficio RISS.");
	private int code;
	
	public PersonaNoValidaBeneficioRissException(){
		super(situacion , codigo);
	}
	
	public PersonaNoValidaBeneficioRissException(String message){
		super(message);
	}
	
	public PersonaNoValidaBeneficioRissException(String message, int code){
		super(message, code);
	}

	public Integer getCode() {
		return this.code;
	}
	
}
