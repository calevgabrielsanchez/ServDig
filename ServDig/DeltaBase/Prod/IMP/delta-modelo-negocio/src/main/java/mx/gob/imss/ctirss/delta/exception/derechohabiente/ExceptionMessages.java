package mx.gob.imss.ctirss.delta.exception.derechohabiente;

public class ExceptionMessages {
	//Vigencia
	public static final String VIGENCIA_ERROR = "exception.vigencia";
	
	// Medios de contacto
	public static final String MEDIOS_CONTACTO_ERROR = "exception.mediosContacto.consulta";
	//Busqueda
	public static final String USUARIO_INVALIDO = "exception.login.UsuarioInvaido";
	public static final String PASSWORD_INVALIDO = "exception.login.PasswordInvalido";
	//Asignacion Nss
	public static final String NSS_NO_ENCONTRADO="exception.derechahabientes.nssNoExiste";
	//CabezaGrupoFamiliar
	public static final String CABEZAGF_NO_ENCONTRADO="exception.derechahabientes.cabeceraNoExiste";
	//Grupo Familiar
	public static final String INTEGRANTE_GRUPO_FAMILIAR="exception.integrante.grupoFamiliar";
	public static final String GRUPO_FAMILIAR_ACTUALIZAR_ERROR="exception.grupoFamiliar.actualizar";
	public static final String GRUPO_FAMILIAR_GUARDAR_ERROR="exception.grupoFamiliar.guardar";
	public static final String GRUPO_FAMILIAR_CONSULTA_ERROR="exception.grupoFamiliar.consultar";
	
	//Estado Derechohabiente
	public static final String ESTADO_DH_NO_ENCONTRADO="exception.derechahabientes.estado.dh.NoExiste";
	//agendar cita
	public static final String AGENDARCITA_SIN_CAPASIDAD="exception.derechahabientes.agendarCitaSinCapacidad";
	public static final String CODIGO_POSTAL_SIN_UMF="exception.derechahabientes.codigoPostalSinUmf";
	//Codigo postal sin UMF registrada
	public static final String CODIGO_POSTAL_SIN_UMF_ASEGURADO="exception.derechahabientes.codigoPostalSinUmfAsegurado";
	//Codigo postal sin UMF registrada
	public static final String CODIGO_POSTAL_SIN_UMF_PATRON="exception.derechahabientes.codigoPostalSinUmfPatron";
	
	public static final String DOMICLIO_SIN_CODIGO_POSTAL = "exception.domicilio.sinCodigoPostal";
	
	//Patron Sujeto Obligado
	public static final String SIN_PATRON_SUJETO="Error al recuperar el tipo de modalidad del derechohabiente";
	//Registro Patronal
	public static final String REGISTRO_PATRONAL_ERROR = "exception.patron.registro";
	//Patrones relacionados a un asegurado
	public static final String PATRONES_RELACIONADOS_ERROR = "consulta.patron.error";
	
	public static final String PERSONA_DOMICILIO = "error.persona.domicilio.guardar";
	public static final String PERSONA_DOMICILIO_CONSULTA = "error.persona.domicilio.consultar";
	
	//Servicios del asegurado
	public static final String SERVICIOS_ASEGURADO_ERROR = "exception.RNGD0146";
	public static final String SERVICIOS_RESTRICCIONES_ASEGURADO_ERROR = "exception.RNGD0084";

	//Modalidad descripcion
	public static final String MODALIDAD_ERROR = "exception.modadalidad.consultar";
	//Asegurado
	public static final String ASEGURADO_ERROR = "exception.asegurado";
	//Pensionado
	public static final String PENSIONADO_ERROR ="Error al recuperar un Derechohabiente Pensionado";
	//Solicitud
	public static final String FOLIO_SOLICITUD_ERROR = "Error al recuperar un folio de solicitud";
	//Solicitud
	public static final String FOLIO_SOLICITUD_ATENDIDA = "exception.solicitud.atendida";
	//Solicitud
	public static final String FOLIO_SOLICITUD_CANCELADA = "exception.solicitud.cancelada";
	//Sin informacion para mostrar
	public static final String SIN_INFORMACION = "exception.sinInformacion";
	//Folio no existe
	public static final String SIN_FOLIO_SOLICITUD = "exception.solicitud.solicitudNoExiste";
	//Derechohabiente
	public static final String DERECHOHABIENTE_ERROR = "exception.derechohabiente.errorDerechohabiente";
	public static final String DERECHOHABIENTE_ACTUALIZA_ERROR = "exception.derechohabiente.errorActualiza";
	public static final String DERECHOHABIENTE_GUARDAR_ERROR = "exception.derechohabiente.errorGuardar";
	
	public static final String DERECHOHABIENTE_SIN_VIGENCIA = "exception.derechohabiente.vigencia";
	
	//Registro derechohabientes
	public static final String REGISTRO_DERECHOHABIENTE_ERROR = "exception.registroDerechohabiente.consulta";
	//Persona Interesada Solicitud
	public static final String PERSONA_INT_SOL_ERROR = "Error al recuperar la persona interesada de la solicitud";
	//Localizar persona fisica
	public static final String LOCALIZAR_PERSONA_ERROR = "exception.localizar.personaFisica";
	//Alta persona fisica
	public static final String ALTA_PERSONA_FISICA = "exception.alta.personaFisica";
	//Actualiza persona fisica
	public static final String ACTUALIZA_PERSONA_FISICA = "exception.actualiza.personaFisica";
	//Domicilio
	public static final String DOMICILIO_REGISTRO = "exception.registro.domicilio";
	public static final String DOMICILIO_CONSULTA = "Error al recuperar un domicilio geografico";
	public static final String PERSONA_SIN_DOMICILIO = "exception.persona.sinDomicilio";
	
	//Requisitos minimos
	public static final String EDAD_HIJOS_ERROR = "Error al calcular la edad de un descendiente";
	//No sepudo guardar el tramite
	public static final String ERROR_GUARDADO_SOLCITUD = "exception.tramite.errorGuardar";
	public static final String ERROR_CONSULTA_TRAMITE = "exception.tramite.errorConsulta";
	public static final String ERROR_CONSULTA_SOLICITUD = "exception.solicitud.errorConsulta";
	public static final String ERROR_ACTUALIZA_SOLICITUD = "exception.solicitud.errorActualizar";
	
	//Parentesco no adecado para el tipo de baja
	public static final String PARENTESCO_INCORRECTO_BAJA = "exception.tramite.baja.parentescoIncorrecto";
	//Tramites Rechazados
	public static final String ERROR_TRAMITES_RECHAZADOS = "exception.tramite.rechazado";
	//Persona ya no es candidato a ser dado de baja por defuncion
	public static final String ERROR_CANDIDATO_DEFUNCION = "excepcion.baja.defuncion.candidato";
	//Persona ya no es candidato a ser dado de baja por termino de concubinato
	public static final String ERROR_CANDIDATO_CONCUBINATO = "excepcion.baja.concubinato.candidato";
	
	//Persona ya no es candidato a ser dado de baja por divorcio
	public static final String ERROR_CANDIDATO_DIVORCIO = "excepcion.baja.divorcio.candidato";
	//Persona ya no es candidato a ser dado de baja por termino de dependencia
	public static final String ERROR_CANDIDATO_DEPENDENCIA = "excepcion.baja.dependencia.candidato";
	// Datos
	public static final String ERROR_DATOS = "exception.datos.incorrectos";
	//Parcers
	public static final String ERROR_PARSER_PERSONA_DOM = "exception.parser.persona.domicilio";
	public static final String ERROR_PARSER_DERECHOHABIENTE = "Error al realizar el parser DERECHOHABIENTE";
	public static final String ERROR_PARSER_FISICA = "Error al realizar el parser FISICA";
	public static final String ERROR_PARSER_ASEGURADO = "Error al realizar el parser ASEGURADO";
	public static final String ERROR_PARSER_ASEGURADO_PENSION = "Error al realizar el parser ASEGURADO PENSION";
	public static final String ERROR_PARSER_ASENTAMIENTO = "Error al realizar el parser ASENTAMIENTO";
	public static final String ERROR_PARSER_ASIGNACION_NSS = "Error al realizar el parser ASIGNACION_NSS";
	public static final String ERROR_PARSER_CABEZA_GF = "Error al realizar el parser CABEZA_GRUPO_FAMILIAR";
	public static final String ERROR_PARSER_CALIDAD_PARENTESCO = "Error al realizar el parser CALIDAD_PARENTESCO";
	public static final String ERROR_PARSER_CARACTER = "Error al realizar el parser CARACTER";
	public static final String ERROR_PARSER_CATEGORIA_PREGUNTA = "Error al realizar el parser CATEGORIA_PREGUNTA";
	public static final String ERROR_PARSER_CLAVE_PRESUPUESTAL = "Error al realizar el parser CLAVE_PRESUPUESTAL";
	public static final String ERROR_PARSER_CODIGO_POSTAL = "Error al realizar el parser CODIGO_POSTAL";
	public static final String ERROR_PARSER_CONSULTORIO = "Error al realizar el parser CONSULTORIO";
	public static final String ERROR_PARSER_CORRECCION_DATOS_DERECHOHAB = "Error al realizar el parser CORRECCION_DATOS_DERECHOHAB";
	public static final String ERROR_PARSER_DELEGACION_IMSS = "Error al realizar el parser DELEGACION_IMSS";
	public static final String ERROR_PARSER_DIAS_FESTIVOS = "Error al realizar el parser DIAS_FESTIVOS";
	public static final String ERROR_PARSER_DOCUMENTACION_TRAMITE = "Error al realizar el parser DOCUMENTACION_TRAMITE";
	public static final String ERROR_PARSER_DOMICILIO = "Error al realizar el parser DOMICILIO";
	public static final String ERROR_PARSER_ENTIDAD_FEDERATIVA = "Error al realizar el parser ENTIDAD_FEDERATIVA";
	public static final String ERROR_PARSER_ESTADO_CIVIL = "Error al realizar el parser ESTADO_CIVIL";
	public static final String ERROR_PARSER_ESTADO_DERECHOHABIENTE = "Error al realizar el parser ESTADO_DERECHOHABIENTE";
	public static final String ERROR_PARSER_ESTADO_PERSONA = "Error al realizar el parser ESTADO_PERSONA";
	public static final String ERROR_PARSER_ESTADO_SOLICITUD = "Error al realizar el parser ESTADO_SOLICITUD";
	public static final String ERROR_PARSER_ESTADO_TRAMITE = "Error al realizar el parser ESTADO_TRAMITE";
	public static final String ERROR_PARSER_ESTADO_PRORROGA = "Error al realizar el parser ESTADO_PRORROGA";	
	public static final String ERROR_PARSER_EXTENSION_VIGENCIA = "Error al realizar el parser EXTENSION_VIGENCIA";
	public static final String ERROR_PARSER_GRUPO_FAMILIAR = "Error al realizar el parser GRUPO_FAMILIAR";
	public static final String ERROR_PARSER_MEDICO_EN_TURNO = "Error al realizar el parser MEDICO_EN_TURNO";
	public static final String ERROR_PARSER_MEDICO_ESPECIALIDAD = "Error al realizar el parser MEDICO_ESPECIALIDAD";
	public static final String ERROR_PARSER_MEDICO_FAMILIAR = "Error al realizar el parser MEDICO_FAMILIAR";
	public static final String ERROR_PARSER_MODALIDAD = "Error al realizar el parser MODALIDAD";
	public static final String ERROR_PARSER_MOD_SERV_PRES_DERECHOHAB = "Error al realizar el parser MOD_SERV_PRES_DERECHOHAB";
	public static final String ERROR_PARSER_MOVIMIENTO_ASEGURADO = "Error al realizar el parser MOVIMIENTO_ASEGURADO";
	public static final String ERROR_PARSER_MOVTO_ASEG_ABIERTO = "Error al realizar el parser MOVTO_ASEG_ABIERTO";
	public static final String ERROR_PARSER_MUNICIPIO = "Error al realizar el parser MUNICIPIO";
	public static final String ERROR_PARSER_NIVEL_ATENCION = "Error al realizar el parser NIVEL_ATENCION";
	public static final String ERROR_PARSER_PARENTESCO = "Error al realizar el parser PARENTESCO";
	public static final String ERROR_PARSER_PATRON_SUJETO_OBLIGADO = "Error al realizar el parser PATRON_SUJETO_OBLIGADO";
	public static final String ERROR_PARSER_PERFIL_USUARIO = "Error al realizar el parser PERFIL_USUARIO";
	public static final String ERROR_PARSER_PERSONA_INTERESADA_SOL = "Error al realizar el parser PERSONA_INTERESADA_SOL";
	public static final String ERROR_PARSER_PERSONA = "Error al realizar el parser PERSONA";
	public static final String ERROR_PARSER_PREGUNTA = "Error al realizar el parser PREGUNTA";
	public static final String ERROR_PARSER_PRORROGA = "Error al realizar el parser PRÓRROGA";
	public static final String ERROR_PARSER_RAZON_REGISTRO = "Error al realizar el parser RAZON_REGISTRO";
	public static final String ERROR_PARSER_RAZON_RESULTADO = "Error al realizar el parser RAZON_RESULTADO";
	public static final String ERROR_PARSER_REGISTRO_DERECHOHABIENTE = "Error al realizar el parser REGISTRO_DERECHOHABIENTE";
	public static final String ERROR_PARSER_RESPUESTA_CUESTIONARIO = "Error al realizar el parser RESPEUSTA_CUESTIONARIO";
	public static final String ERROR_PARSER_RESPUESTA = "Error al realizar el parser RESPEUSTA";
	public static final String ERROR_PARSER_RESPUESTA_SI_NO = "Error al realizar el parser RESPUESTA_SI_NO";
	public static final String ERROR_PARSER_SERVICIO_PREST_DERECHOHAB = "Error al realizar el parser SERVICIO_PREST_DERECHOHAB";
	public static final String ERROR_PARSER_SERVICIOS = "Error al realizar el parser SERVICIOS";
	public static final String ERROR_PARSER_SEXO = "Error al realizar el parser SEXO";
	public static final String ERROR_PARSER_SOLICITUD = "Error al realizar el parser SOLICITUD";
	public static final String ERROR_PARSER_SUBDELEGACION_IMSS = "Error al realizar el parser SUBDELEGACION_IMSS";
	public static final String ERROR_PARSER_SUBESTADO_DERECHOHABIENTE = "Error al realizar el parser SUBESTADO_DERECHOHABIENTE";
	public static final String ERROR_PARSER_TIPO_CUESTIONARIO = "Error al realizar el parser TIPO_CUESTIONARIO";
	public static final String ERROR_PARSER_TIPO_EXTENSION_VIGENCIA = "Error al realizar el parser TIPO_EXTENSION_VIGENCIA";
	public static final String ERROR_PARSER_TIPO_MOVIMIENTO = "Error al realizar el parser TIPO_MOVIMIENTO";
	public static final String ERROR_PARSER_TIPO_PENSION = "Error al realizar el parser TIPO_PENSION";
	public static final String ERROR_PARSER_TIPO_PER_INTERESADA_SOL = "Error al realizar el parser TIPO_PER_INTERESADA_SOL";
	public static final String ERROR_PARSER_TIPO_PREGUNTA = "Error al realizar el parser TIPO_PREGUNTA";
	public static final String ERROR_PARSER_TIPO_SOLICITUD = "Error al realizar el parser TIPO_SOLICITUD";
	public static final String ERROR_PARSER_TIPO_TRAMITE = "Error al realizar el parser TIPO_TRAMITE";
	public static final String ERROR_PARSER_TIPO_UMF = "Error al realizar el parser TIPO_UMF";
	public static final String ERROR_PARSER_TRAMITE = "Error al realizar el parser TRAMITE";
	public static final String ERROR_PARSER_TRAMITE_SIMPLE = "Error al realizar el parser TRAMITE_SIMPLE";
	public static final String ERROR_PARSER_TURNO = "Error al realizar el parser TURNO";
	public static final String ERROR_PARSER_UMF_CODIGO_POSTAL = "Error al realizar el parser UMF_CODIGO_POSTAL";
	public static final String ERROR_PARSER_UMF_TURNO = "Error al realizar el parser UMF_TURNO";
	public static final String ERROR_PARSER_UNIDAD_MEDICA_FAMILIAR = "Error al realizar el parser UNIDAD_MEDICA_FAMILIAR";
	public static final String ERROR_PARSER_USUARIO_FUNCIONARIO = "Error al realizar el parser USUARIO_FUNCIONARIO";
	public static final String ERROR_PARSER_USUARIO_ORDINARIO = "Error al realizar el parser USUARIO_ORDINARIO";
	public static final String ERROR_PARSER_USUARIO = "Error al realizar el parser USUARIO";
	public static final String ERROR_PARSER_CIRCUNSCRIPCION_FORANEA = "Error al realizar el parser CIRCUNSCRIPCION_FORANEA";
	public static final String ERROR_PARSER_CORRECCION_DATO_DERECHOHABIENTE = "Error al realizar el parser CORRECCION_DATO_DERECHOHABIENTE";
	public static final String ERROR_PARSER_DERECHOHABIENTE_SERVICE = "Error al realizar el parser DERECHOHABIENTE_SERVICE";
	public static final String ERROR_PARSER_DETALLE_NIVEL_EDUCATIVO = "Error al realizar el parser DETALLE_NIVEL_EDUCATIVO";
	public static final String ERROR_PARSER_DOCTO_REQ_TRAMITE = "Error al realizar el parser DOCTO_REQ_TRAMITE";
	public static final String ERROR_PARSER_FISICA_SERVICE = "Error al realizar el parser FISICA_SERVICE";
	public static final String ERROR_PARSER_GRUPO_FAMILIAR_SERVICE = "Error al realizar el parser GRUPO_FAMILIAR_SERVICE";
	public static final String ERROR_PARSER_NIVEL_EDUCATIVO_SERVICE = "Error al realizar el parser NIVEL_EDUCATIVO_SERVICE";
	public static final String ERROR_PARSER_PERSONA_DOMICILIO_SERVICE = "Error al realizar el parser PERSONA_DOMICILIO_SERVICE";
	public static final String ERROR_PARSER_TIPO_NIVEL_EDUCATIVO_SERVICE = "Error al realizar el parser TIPO_NIVEL_EDUCATIVO_SERVICE";
	public static final String ERROR_PARSER_DICTAMEN_INTEGRANTE_INCAPACITADO = "Error al realizar el parser DICTAMEN_INTEGRANTE_INCAPACITADO";
	public static final String ERROR_PARSER_DOCUMENTO_POR_TIPO = "Error al realizar el parser DOCUMENTO_POR_TIPO";
	public static final String ERROR_PARSER_TIPO_DOCUMENTO_PROBATORIO = "Error al realizar el parser TIPO_DOCUMENTO_PROBATORIO";
	
	
	public static final String ERROR_BASE_DATOS = "excepion.bd";
	
	//Servicios
	public static final String ERROR_CONSULTA_SERVICIOS = "Error al recuperar servicios de un asegurado";
	
	//Asegurado
	public static final String ERROR_CONSULTA_ASEGURADO = "Error al recuperar un asegurado";

	//Solicitud
	public static final String ERROR_EXISTE_SOLICITUD = "Error al consultar solicitudes";
	
	public static final String ERROR_ASEGURADO_EN_BAJA = "exception.asegurado.enbaja";
	
	//Firma elecgtrónica
	public static enum FIRMA_ELECTRONICA{
		ERROR(-100,"Error al consultar la firma electrónica");
		
		FIRMA_ELECTRONICA(Integer codigo, String mensaje){
			this.mensaje = mensaje;
			this.codigo = codigo;
		}
		
		private String mensaje;
		private Integer codigo;
		
		public String getMensaje(){
			return this.mensaje;
		}
		
		public Integer getCodigo(){
			return this.codigo;
		}
		
		@Override
		public String toString(){
			return this.mensaje;
		}
		
		
	}
	
	
	
}