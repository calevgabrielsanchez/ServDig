/**
 * 
 */
package mx.gob.imss.ctirss.idse.utility;

/**
 * @author NOVUTECK1
 *
 */
public enum CodigoError {
	Requerimiento_Inexistente(10,"La información del requerimiento es requerida"), 
	PKCS7_Inexistente(20,"La información del certificado es requerida"),
	Serial_Inexistente(30,"El número de serie del certificado es requerido"),
	Correo_Inexistente(40,"La información del correo electronico es requerida"),
	CURP_Inexistente(50,"La información de la CURP es requerida"),
	Estatus_Inexistente(60,"La información del Estatus Fiel es requerida"),
	FechaValida_Inexistente(70,"La Fecha Valida es requerida"),
	FechaValidaInicio_Inexistente(80,"La Fecha Valida Inicio es requerida"),
	IdRol_Inexistente(90,"El Id Rol es requerido"),
	NombreCompleto_Inexistente(100,"El Nombre Completo es requerido"),
	NombreUsuario_Inexistente(110, "El Nombre de Usuario es requerido"),
	RFCAsociado_Inexistente(115, "El RFC Asociado es requerido"),
	Telefono_Inexistente(120,"El Telefono es requerido"),
	RegistroPatronal_Inexistente(125, "El Registro Patronal es requerido"),
	EstatusRP_Inexistente(130,"El estatus del registro patronal es requerido"),
	FechaActivacion_Inexistente(135,"La fecha de Activación es requerida"),
	FechaRecepcion_Inexistente(140,"La fecha de recepción es requerida"),
	RazonSocial_Inexistente(145,"La razón social es requerida"),
	Registropatronal_Inexistente(150,"El registro patronal es requerido"),
	TipoPersona_Inexistente(155,"El Tipo de Persona es requerido"),
	UsuarioSubdelegacion_Inexistente(160,"El Usuario Subdelegacional es requerido"),
	RequerimientoIDSE_Inexistente(165,"La información del requerimiento IDSE es requerida"),
	Actividad_Inexistente(170,"La actividad del requerimiento es requerida"),
	Clasert_Inexistente(175,"La clase RT del requerimientos es requerida"),
	ClaveDelegacionInexistente(180,"La Clave delegacional es requerida"),
	ClaveSubdelegacion_Inexistente(185,"La Clave Subdelegacional es requerida"),
	Domicilio_Inexistente(190,"El domicilio es requerido "),
	Email_Inexistente(195,"El correo electrónico es requerido"),
	Fraccion_Inexisten(200,"La fracción es requerida"),
	Localidad_Inexistente(210,"La localidad es requerida"),
	Municipio_Inexistente(215,"El municipio es requerido"),
	RegPatron_Inexistente(220,"El RegPatron es requerido"),
	RegPatronDig_Inexistente(225,"El RegPatronDig es requerido"),
	RepLegal_Inexistente(230,"El representante Legal es Requeridio"),
	RFC_Inexistente(235, "El RFC es requerido"),
	Sector_Inexistente(240,"El sector es requerido")
	
	
	
	
	
	
	;

	
	private int codigo;
	private String descripcion;
	CodigoError(int pCodigo, String pDescripcion){
		codigo=pCodigo;
		descripcion=pDescripcion;
	}
	
	public int getCodigo(){
		return codigo;
	}

	public String getDescripcion(){
		return descripcion;
	}
	
}
