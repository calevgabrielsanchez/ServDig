package mx.gob.imss.ctirss.delta.exception.derechohabiente;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class TramiteDerechoabienteTSPIException extends AbstractException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8793698219414457267L;
	
	private static final Integer codigo = Integer.valueOf(101);
	private static final String mensaje = new String("Ocurrio un error no identificado");
	
	public static final String  ERROR_VIGENCIA 	= "El asegurado no cuenta con una situacon de vigencia valida para realizar el registro  de beneficiarios";
	public static final String ERROR_DATOS_INCOMPLETOS  = "Los datos requeridos para el tramite de registro estan incompletos";                                                   
	public static final String ERROR_DATOS_DOMICILIO_INCOMPLETO = "Los datos del domicilio requeridos para el tramite de registro estan incompletos"; 
	public static final String ERROR_PERSONA_REGISTRADO_GRUPO_FAMILAIR = "La persona ya se encuentra registrada en el grupo familiar del Asegurado";
	
	public static final Integer RNGD0026= 26;
	
	
	public TramiteDerechoabienteTSPIException() {
		super(mensaje, codigo);
	}

	public TramiteDerechoabienteTSPIException(String mensaje) {
		super(mensaje);
	}
	
	public TramiteDerechoabienteTSPIException(String mensaje, Integer codigo) {
		super(mensaje,codigo);
	}
	
	public static void throwException(String message) throws TramiteDerechoabienteTSPIException{
		throw new TramiteDerechoabienteTSPIException(message);
	}
	
	public static void throwException(String mensaje, Integer codigo) throws TramiteDerechoabienteTSPIException {
		throw new TramiteDerechoabienteTSPIException(mensaje, codigo);
	}
	

}
