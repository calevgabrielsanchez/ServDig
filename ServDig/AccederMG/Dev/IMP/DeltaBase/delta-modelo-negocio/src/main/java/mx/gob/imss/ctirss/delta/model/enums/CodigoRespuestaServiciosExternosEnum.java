/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.enums;

/**
 * @author NOVUTECK1
 *
 */
public enum CodigoRespuestaServiciosExternosEnum {
	OPERACION_EXITOSA("000"),
	NO_SE_ENCONTRARON_REGISTROS("101"),
	DATOS_ENTRADA_INVALIDOS("102"),
	CURP_CORREO_INVALIDO  ("103"),
	ERROR_CONSULTA_RENAPO ("104"),
	DATOS_INCONSISTENTES_RENAPO ("105"),
	PERSONA_INCONSISTENTE ("106"),
	NO_CUMPLE_REQUISITOS_TRAMITE ("107"),
	ERROR_DE_SISTEMA ("108"),
	NSS_NO_ENCONTRADO("109"),
	NSS_PERSONA_DIFERENTE("110"),
	ASEGURADO_REGISTRADO("111"),
	ASEGURADO_NO_REGISTRADO("112"),
	ERROR_ALMACEN_VIGENCIA("113");
	
	private String codigo;
	private CodigoRespuestaServiciosExternosEnum(String codigo){
		this.codigo=codigo;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
