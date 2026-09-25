package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.utils;

/**
 * 
 * @author CGARCIA
 *
 */
public class Constants {
	// Excepciones de session
	public static final String SESSION_ERROR = "exception.session.invalida";
	
	//nombres de session
	public static final String ASIGNACION_NSS_SESSION_NAME="AsignacionNSS";
	public static final String PATRON_SUJETO = "PatronSujetoObligado";
	public static final String CABEZA_GRUPO_FAM_SESSION = "cabezaGrupoFamiliar";
	// Vigencia
	public static final String CON_VIGENCIA = "0";
	public static final String SIN_VIGENCIA = "1";
	// Usuario Interno o Externo
	public static final String INTERNO = "0";
	public static final String EXTERNO = "1";
	public static final String REGRESOS = "2";
	
	// Descripcion tipo de solicitud
	public static final String DESCRIPCION_RECHAZO = "RECHAZO DE REGISTRO";
	public static final Long TIPO_RECHAZO_REGISTRO = 1000L;
	//Valida cabeza grupo familiar registrado
	public static final String CON_ASEGURADO_EXTERNO = "0";
	public static final String CON_ASEGURADO_INTERNO = "1";
	public static final String SIN_ASEGURADO_EXTERNO = "2";
	public static final String SIN_ASEGURADO_INTERNO = "3";
	public static final String SIN_PENSIONADO_EXTERNO = "4";
	public static final String SIN_PENSIONADO_INTERNO = "5";
	public static final String SIN_ASEG_PEN_CON_SOL = "6";	
	public static final String CON_ASEGURADO_PARENTESCO = "7";
	
	//Detalle de encabezado para todas las pantallas
	public static final String CON_DETALLE_SITUACION = "0";
	public static final String SIN_DETALLE_SITUACION = "1";
	
	//Catalogo documentos 
	public static final int TIPO_DOC_ACTA_NACIMIENTO = 1 ;
	public static final int TIPO_DOC_ACTA_MATRIMONIO = 2 ;
	public static final int TIPO_DOC_ACTA_RECONOCIMIENTO = 3 ;
	public static final int TIPO_DOC_ACTA_ADOPCION = 4;
	public static final int TIPO_DOC_ACTA_MATRIMONIO_DICTAMEN_DIS=5;
	public static final int TIPO_DOC_ACTA_CONSTITUTIVA=6;
	
	public static final int TIPO_DOC_ESTADO_CUENTA_BANCARIO=7;
	public static final int TIPO_DOC_CONTRATO_ARRENDAMIENTO=8;
	public static final int TIPO_DOC_RECIBO_TELEVISION=9;
	public static final int TIPO_DOC_PAGO_TENENCIA_VEHICULAR=10;
	public static final int TIPO_DOC_RECIBO_GAS=11;
	public static final int TIPO_DOC_RECIBO_LUZ=12;
	public static final int TIPO_DOC_RECIBO_TELEFONO=13;
	public static final int TIPO_DOC_ESCRITURA_PROPIEDAD_INMOBILIARIA=14;
	public static final int TIPO_DOC_RECIBO_AGUA=15;
	public static final int TIPO_DOC_PAGO_PREDIAL=16;

	
	public static final int TIPO_DOC_ACUERDO = 17 ;
	public static final int TIPO_DOC_VIGENCIA_TEMPORAL = 18 ;
	public static final int TIPO_DOC_CONSTANCIA_ESTUDIOS = 19 ;
	public static final int TIPO_DOC_OBSTETRICO = 20 ;
	public static final int TIPO_CERTIFICADO_SIT_CRITICA = 21 ;
	public static final int TIPO_DOC_LAUDO = 22;
	public static final int TIPO_DOC_DICTAMEN_INCAPACITADO= 23 ;
	public static final int TIPO_DOC_CERTIFICADO_NACIMIENTO = 24 ;
	public static final int TIPO_DOC_CURP = 34 ;
	public static final int TIPO_DOC_CEDULA_PROFESIONAL = 35 ;
	public static final int TIPO_DOC_CREDENCIAL_ELECTOR = 36 ;
	public static final int TIPO_DOC_CARTILLA_MILITAR = 37 ;
	public static final int TIPO_DOC_PASAPORTE = 38 ;
	public static final int TIPO_DOC_ACTA_DIVORCIO = 40 ;
	public static final int TIPO_DOC_ACTA_DEFUNCION = 41;
	public static final int TIPO_DOC_PENSION = 42 ;
	public static final int TIPO_DOC_ADIMSS = 46 ;
	public static final int TIPO_DOC_ACTA_PACTO_CIVIL = 65;
	public static final int TIPO_DOC_ACTA_UNION_CIVIL = 210;
	public static final int TIPO_DOC_ACTA_TERMINO_UNION_CIVIL = 211;
	
	//views
	
	public static final String FORDWARD_ACTA_COMUN ="actaComun";
	public static final String FORDWARD_COMPBANTE_DOMICILIO ="compDom";
	public static final String FORDWARD_CEDULA_PROFESIONAL="cedulaProfesional";
	public static final String FORDWARD_ACTA_NACIMIENTO="actaNacimiento";
	public static final String FORDWARD_CARTILLA_MILITAR="cartillaMilitar";
	public static final String FORDWARD_CERTIFICADO_NACIMIENTO="certificadoNacimiento";
	public static final String FORDWARD_CONSTANCIA_ESTUDIOS="constanciaEstudios";
	public static final String FORDWARD_CREDENCIAL_ELECTOR="credencialElector";
	public static final String FORDWARD_CURP="curp";
	public static final String FORDWARD_PASAPORTE="pasaporte";
	public static final String FORDWARD_CERTIFICADO_SIT_CRITICA="certificadoSitCritica";
	public static final String FORDWARD_DICTAMEN_INCAPACITADO="dictamenIncapacitado";
	public static final String FORDWARD_OBSTETRICO="obstetrico";
	public static final String FORDWARD_VIGENCIA_TEMPORAL="vigenciaTemporal";
	public static final String FORDWARD_ACUERDO="acuerdo";
	public static final String FORDWARD_ADIMSS = "adimss";
	public static final String FORDWARD_ACTA_PACTO_CIVIL = "actaPactoCivil";
	public static final String FORDWARD_ACTA_UNION_CIVIL = "actaUnionCivil";
	public static final String FORDWARD_ACTA_TERMINO_UNION_CIVIL = "actaTerminoUnionCivil";

	
	//Nuevos
	//public static final int TIPO_DOC_COMPROBANTE_DOMICILIO= 
	public static final int TIPO_DOC_SOLICITUD_PENSION=42;
	

	
	//Titulos en Pantallas comunes Captura Documentos
	public static final String TITLE_ACTA_MATRIMONIO = "title.capturaDocumentos.matrimonio";
	public static final String TITLE_ACTA_MATRIMONIO_DIC_DIS = "title.capturaDocumentos.matrimonioDicDis";
	public static final String TITLE_ACTA_DIVORCIO = "title.capturaDocumentos.divorcio";
	public static final String TITLE_ACTA_RECONOCIMIENTO = "title.capturaDocumentos.reconocimiento";
	public static final String TITLE_ACTA_DEFUNCION = "title.capturaDocumentos.defuncion";
	public static final String TITLE_ACTA_ADOPCION = "title.capturaDocumentos.adopcion";
	
	public static final String TITLE_ACUERDO = "title.capturaDocumentos.acuerdo";
	public static final String TITLE_LAUDO = "title.capturaDocumentos.laudo";
	
	public static final int TOTAL_PUNTOS_CUESTIONARIO_CONVIVENCIA = 81 ;
	
	//Arreglo combos Calidad parentesco
	public static final int NORMAL = 0;
	public static final int RECIEN_NACIDO = 1;
	public static final int HIJOS_SIN_DERECHOS = 2;
	public static final int HIJO_MAYOR_A_25 = 0;
	public static final int LAUDO_ACUERDO = 3;
	public static final int AMPARO = 4;		
	
	// Parentescos Maximos permitidos
	public static final int HIJOS = 86;
	public static final int PADRES = 2;
	public static final int CONYUGUE = 1; 
	public static final int CONCUBINA = 1;
	
	//Welcome
	public static final String SOLICITUDES_PEN_AUT_FORWARD="muestraSolicitudesPenAut";
	public static final String SOLICITUDES_ATENDIDAS="muestraSolicitudesAtendidas";
	public static final String GRUPO_FAMILIAR_FORDWARD="grupoFamiliar";
	public static final String GRUPO_FAMILIAR_FORDWARD2="grupoFamiliar2";
	public static final String BUSQUEDA_PRINCIPAL_FORDWARD = "busquedaPrincipal";
	public static final String MENU_TRAMITADOR_FORDWARD = "menuTramitador";
	public static final String WELCOME_FORWARD = "welcome";
	
	//Registro Derechohabientes
	public static final String REGISTRO_DERECHOHABIENTE = "registro";
	
	public static final int PRIMER_REGISTRO_LISTA = 0;
	
	//Baja de derechohabiente
	public static final String LISTA_BAJA_DEFUNCION_FORDWARD = "listaBajaDefuncion";
	public static final String LISTA_BAJA_DIVORCIO_FORDWARD = "listaBajaDivorcio";
	public static final String LISTA_BAJA_CONCUBINATO_FORDWARD = "listaBajaConcubinato";
	public static final String LISTA_BAJA_CONVIVENCIA_FORDWARD = "listaBajaConvivencia";
	public static final String INICIO_BAJA_CONCUBINATO = "inicioBajaConcubinato";
	public static final String INICIO_BAJA_DIVORCIO = "inicioBajaDivorcio";
	public static final String CITA_BAJA_DERECHOHABIENTE_FORDWARD = "citaSolicitud";
	public static final String VALIDAR_BAJA_DERECHOHABIENTE_FORDWARD = "validarBaja";
	public static final String AUTORIZAR_BAJA_DERECHOHABIENTE_FORDWARD = "autorizarBaja";
	public static final String TRAMITE_VALIDACION_BAJA = "tramitarValidarBaja";
	
	//Correccion de derechohabiente
	public static final String LISTA_CORRECCION_FORWARD = "candidatosCorreccionDerechohabiente";
	public static final String CAMBIO_CLINICA_HOME = "cambioClinicaHome";
	public static final String CAMBIO_CLINICA_MULTIPLE = "cambioClinicaMultiple";
	public static final String CAMBIO_DATOS_FORWARD = "datosCorreccionDerechohabiente";
	public static final String CAMBIO_MEDICO_FORWARD = "correccionMedicoEnTurno";
	public static final String CAMBIO_UMF_FORWARD = "correccionUMF";
	public static final String AUTORIZACION_CIRCUNSCRIPCION = "correccionCircunscripcion";
	public static final String SUSPENSION_CIRCUNSCRIPCION = "suspencionCircunscripcion";
	public static final String CITA_SOLICITUD_GENERICA = "citaSolicitudGeneral";
	public static final String DATOS_DERECHOHABIENTE_CORRECCION = "datosCorreccionDerechohabiente";
	public static final String FINALIZACION_TRAMITE_CORRECCION = "finalizacionCorreccion";
	public static final String AUTORIZACION_CORRECCION = "autorizacionCorreccion";
	public static final String ASIGNACION_MEDICO_FORWARD = "asignarMedico";
	
	//detalle tramite
	public static final String DETALLE_CIRCUNSCRIPCION = "detalleCircunscripcion";
	public static final String DETALLE_CORRECCION_DATOS = "detalleCorreccion";
	public static final String DETALLE_REGISTRO = "detalleRegistro";
	public static final String DETALLE_CAMBIO_MEDICO = "detalleCambioMedico";
	public static final String DETALLE_PRORROGAS = "detalleProrroga";
	public static final String DETALLE_ASIGNACION_MEDICO = "detalleAsignacionMedico";
	public static final String DETALLE_CAMBIO_UMF = "detalleCambioUmf";
	public static final String DETALLE_TRAMITE_DERECHOHABIENTE = "detalleTramiteDerechohabiente";
	
	//Reportes y documentos
	public static final String URL_CATILLA_NACIONAL_SALUD =   "cartillaNacionalSalud";
	public static final String URL_SAV007 =   "documentoSAV007";
	public static final String URL_SAV017A =   "documentoSAV017A";
	public static final String URL_SAV017S =   "documentoSAV017S";
	public static final String URL_SAV005 =   "documentoSAV005";
	public static final int HIJOS_X_ASEGURADO =   26;
	//Mensajes Error Web Service
	public static final String MSG19 = "4";
	public static final String MSG20 = "6";
	public static final String MSG21 = "7";
	public static final String MSG22 = "8";
	public static final String MSG23 = "9";
	public static final String MSG24 = "10";
	public static final String IPSERVER = "254.254.254.254";
	public static final String FTOFECHA = " 00:00:00.0";
	public static final String SINDATOS = "NO ASIGNADA";
	public static final String SINUMF = "UMF NO LOCALIZADA";
	public static final String SINDELEGACION = "DELEGACION NO LOCALIZADA";
	
	public static final String MSG00 = "0";
	public static final String MSG04 = "4"; //No se encontró Info NECE
	public static final String MSG06 = "6"; //Longitud NSS NECE
	public static final String MSG07 = "7"; //No se proporcionó CPID entrada
	public static final String MSG09 = "9"; //No es numerico el nss
	
	// Modalidades para registro derechohabientes
	public static final String MOD_DH = "'10', '13', '14', '30', '35', '36', '38', '42', '43', '44'";
	public static final String MOD_RN = "'10', '13', '14', '30', '33', '35', '36', '38', '42', '43', '44'";
	public static final String MOD_ASEG = "'10', '13', '14', '30', '32', '33', '35', '36', '38', '42', '43', '44'";
	
	public static final String DOBLE_PROCESO="1";
	
	//Rquisitos minimos para registro de derechohabientes
	public static final int APROBADO=1;
	public static final int NO_APROBADO=2;
	public static final String SOL_LAUDO = "Requisitos aprobados por solicitud de Laudo";
	public static final String SOL_ACUERDO = "Requisitos aprobados por solicitud de Acuerdo HCCD/HCT";
	public static final String SOL_AMPARO = "Requisitos aprobados por solicitud de Amparo";
	public static final String DH_REGISTRADO = "La persona que intenta registrar como derechohabiente ya forma parte del grupo familiar. Por favor corrobore los datos y vuelva a intentarlo.";
	public static final String MOTIVO_APROBADO = "Los requisitos para el registro de derechohabientes son correctos";
	public static final String MOTIVO_MAYOR_25 = "El registro de los hijos mayores a 16 años se registran con estado en BAJA";
	public static final String MOTIVO_SIN_DERECHO = "El registro de los hijos sin derecho a servicio se registran con estado en BAJA";
	public static final String CIRCUNSCRIPCION = "La dirección proporcionada no se encuentra en la misma circunscripción de la delegación <DelegaciónIMSS>. Por favor proporcione una dirección válida";
	public static final String CONCUBINA_REGISTRADA = "Existe Concubina(rio) registrado. Es necesario realizar la baja de este integrante para registrar Esposo(a).";
	public static final String TRAMITE_RECHAZO = "El derechohabiente cuenta una solicitud rechazada para este trámite";
	
	public static final String MAYOR_16 = "No está permitido registrar Hijos mayores 16 años";	
	public static final String MAYOR_25 = "No está permitido registrar Hijos mayores 25 años";
	public static final String SEXO_PAREJA = "No está permitido registrar Cónyuge o Concubina(rio) del mismo sexo";
	public static final String SEXO_PADRES = "No está permitido registrar Padres del mismo sexo";
	public static final String HIJA_REGISTRADA = "Esta persona se encuentra registrada en otro grupo familiar con el parentesco de Cónyugue o Concubina(rio) y este movimiento no esta permitido.";
	public static final String RECIEN_NACIDOS = "La edad del Recién nacido excede la edad permitida";
	public static final String ASEGURADO_PENSIONADO_UNICO = "Este grupo familiar ya cuenta con un Asegurado o Pensionado registrado";
	public static final String NUMERO_MAXIMO_HIJOS = "Este grupo familiar ya cuenta con el número máximo de Hijos permitidos";
	public static final String NUMERO_MAXIMO_PADRES = "Este grupo familiar ya cuenta con el número máximo de Padres permitidos";
	public static final String NUMERO_MAXIMO_CONCUBINARIO = "Este grupo familiar ya cuenta con el número máximo de Concubina(rios) permitidos";
	public static final String NUMERO_MAXIMO_CONYUGE = "Este grupo familiar ya cuenta con el número máximo de Conyuges permitidos";
	public static final String NO_MODALIDAD = "No cuenta con la Modalidad requerida para realizar el registro del derechohabiente";
	public static final String CAB_GRP_NO_ENCONTRADO = "No se encuentró información del aseugrado/pensionado";
	public static final String EDAD_PADRES = "Verificar fecha de nacimiento";
	public static final String UMF_NO_CORRESPONDE = "La unidad médica familiar no corresponde con el domicilio del derechohabiente";
	public static final String SOL_PEND_APR = "Existe una solicitud pendiente de aprobar para este tipo de solicitud o trámite";
	public static final String PARENTESCO_REGISTRADO = "Esta persona se encuentra registrada con el mismo parentesco en otro grupo familiar";
	public static final String CONCUBINA_DUPLICADA = "Esta persona se encuentra registrada como concubina en otro grupo familiar";
	public static final String CONYUGE_DUPLICADA = "Esta persona se encuentra registrada como conyuge en otro grupo familiar";
	public static final String TRAMITE_CONCLUIDOS_APARTE_REGISTRO = "No se permite la corrección de parentesco para derechohabientes que cuentan con tramites concluidos";
	//Delegaciones con circunscripcion y sin validaciones
	public static final String DEL_QUINCE = "15";
	public static final String DEL_DIECISEIS = "16";
	public static final String DEL_TREINTAYNUEVE = "39";
	public static final String DEL_CUARENTA = "40";
	
	//Procesos de registro de derechohabientes
	public static final int CAPTURA = 0;
	public static final int EDICION = 2;
	public static final int CORRECCION = 3;
	
	//Servicios
	public static final int VALOR_PRESTACIONES_EN_ESPECIE  = 50;
	public static final int VALOR_REGISTRO_DE_BENEFICIARIOS  = 30;
	public static final int VALOR_EXPEDICION_DE_INCAPACIDADES  = 10;
	public static final int VALOR_SERVICIO_DE_GUARDERIAS = 10;
	
	//Tramites dependientes
	public static final String CAMBIO_UMF = "CU";
	public static final String CAMBIO_CIRCUNSCRIPCION = "CC";
	
	}
