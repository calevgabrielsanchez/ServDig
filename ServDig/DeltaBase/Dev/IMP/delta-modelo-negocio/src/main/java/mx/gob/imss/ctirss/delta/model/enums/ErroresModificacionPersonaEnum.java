package mx.gob.imss.ctirss.delta.model.enums;

/**
 * Enum que contiene las claves para los errores devueltos por los servicios del
 * ICA y de la Modificación Manual
 * 
 * @author Marco Sanchez
 * 
 */
public enum ErroresModificacionPersonaEnum {

	ERROR_CONSULTA_RENAPO("ERROR_CONSULTA_RENAPO"), ERROR_CONSULTA_SAT("ERROR_CONSULTA_SAT"),
	PERSONA_NO_ENCONTRADA("PERSONA_NO_ENCONTRADA"), CURP_NO_LOCALIZADO("CURP_NO_LOCALIZADO"),
	ERROR_VALIDAR_DATOS_ENTIDAD_EXTERNA("ERROR_VALIDAR_DATOS_ENTIDAD_EXTERNA"),
	RFC_NO_LOCALIZADO("RFC_NO_LOCALIZADO"), ERROR_COMPARACION_DATOS_RENAPO("ERROR_COMPARACION_DATOS_RENAPO"),
	COMPARACION_SIN_DIFERENCIAS("COMPARACION_SIN_DIFERENCIAS"), DATOS_INSUFICIENTES_ICA("DATOS_INSUFICIENTES_ICA"),
	DIFERENCIAS_RENAPO_SAT("DIFERENCIAS_RENAPO_SAT"), PERSONA_FISICA_NO_ENCONTRADA("PERSONA_FISICA_NO_ENCONTRADA"),
	DATOS_INSUFICIENTES_MDM("DATOS_INSUFICIENTES_MDM");

	private String codigo;

	private ErroresModificacionPersonaEnum(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

}
