package mx.gob.imss.ctirss.delta.exception.movil;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class TramiteMovilException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1770545017233878520L;
	private static final Integer codigo = Integer.valueOf(101);
	private static final String mensaje = new String("Ocurrio un error no identificado");
	
	public static final Integer DATOS_ENTRADA_INVALIDOS = Integer.valueOf(102);
	public static final Integer CURP_CORREO_INVALIDO = Integer.valueOf(103);
	public static final Integer ERROR_CONSULTA_RENAPO = Integer.valueOf(104);
	public static final Integer DATOS_INCONSISTENTES_RENAPO = Integer.valueOf(105);
	public static final Integer PERSONA_INCONSISTENTE = Integer.valueOf(106);
	public static final Integer NO_CUMPLE_REQUISITOS_TRAMITE = Integer.valueOf(107);
	public static final Integer ERROR_DE_SISTEMA = Integer.valueOf(108);
	public static final Integer NSS_NO_ENCONTRADO = Integer.valueOf(109);
	public static final Integer NSS_PERSONA_DIFERENTE = Integer.valueOf(110);
	public static final Integer ASEGURADO_REGISTRADO = Integer.valueOf(111);
	public static final Integer ASEGURADO_NO_REGISTRADO = Integer.valueOf(112);
	public static final Integer ERROR_ALMACEN_VIGENCIA = Integer.valueOf(113);
	
	public TramiteMovilException() {
		super(mensaje, codigo);
	}

	public TramiteMovilException(String mensaje) {
		super(mensaje);
	}
	
	public TramiteMovilException(String mensaje, Integer codigo) {
		super(mensaje,codigo);
	}
	
	public static void throwException(String message) throws TramiteMovilException{
		throw new TramiteMovilException(message);
	}
	
	public static void throwException(String mensaje, Integer codigo) throws TramiteMovilException {
		throw new TramiteMovilException(mensaje, codigo);
	}
}
