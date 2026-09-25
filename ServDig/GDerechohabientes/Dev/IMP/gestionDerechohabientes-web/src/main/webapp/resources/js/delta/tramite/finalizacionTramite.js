
function impresionReporte(idSolicitud,idTramite,personas,idTipoTramite, idPersona, titulo, umf, umfusuario,rechazado) {
	
	this.mostrar = function() {
		
		var parametros = {
				'idTramite': idTramite,
				'idPersona': idPersona
		}
		
		if(rechazado) {
			switch(idTipoTramite) {
			case REGISTRO_PERSONA: ;
						break;
			case REGISTRO_DERECHOHABIENTE: ;
						break;
			case REGISTRO_ASEGURADO: ;
						break;
			case REGISTRO_PENSIONADO: ;
						break;
			case REGISTRO_CONCUBINA: ;
						break;
			case REGISTRO_CONYUGE: ;
						break;
			case REGISTRO_PERSONA_EN_UNION_CIVIL: ;
						break;
			case REGISTRO_HIJOS: ;
						break;
			case REGISTRO_PADRES: ;
						break;
			case CANCELA_REGISTRO: showCompRechazoSol(titulo,idTipoTramite);
						break;					
			case CORRECCION_DATOS_DERECHOHABIENTE: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case AUTORIZACION_CIRCUNSCRIPCION: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case SUSPENSION_CIRCUNSCRIPCION: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case CAMBIO_MEDICO: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case CAMBIO_UMF: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case ASIGNACION_MEDICO: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case BAJA_DEFUNCION: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case BAJA_CONCUBINATO: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud) ;
						break;
			case BAJA_DIVORCIO: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case BAJA_PERSONA_UNION_CIVIL: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case BAJA_DEPENDENCIA: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case PRORROGA_ESTUDIOS: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case PRORROGA_ENFERMEDAD: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case PRORROGA_INVALIDEZ: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case PRORROGA_PERMANENTE: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case PRORROGA_TEMPORAL: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case PRORROGA_ACUERDOS: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case PRORROGA_OBSTETRICOS: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			case PRORROGA_LAUDO: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
						break;
			;
			}
		} else {
			switch(idTipoTramite) {
				case REGISTRO_PERSONA: ;
									break;
				case REGISTRO_DERECHOHABIENTE: showCompSolRegistro(idSolicitud,titulo);
									break;
				case REGISTRO_DERECHOHABIENTES_CU: showCompValRegistroDep(idTramite,personas,idTipoTramite,titulo);
									break;				
				case REGISTRO_DERECHOHABIENTES_CC: showCompValRegistroDep(idTramite,personas,idTipoTramite,titulo);
									break;
				case REGISTRO_ASEGURADO: showCompValRegistro(idPersona,idTramite,titulo);
									break;
				case REGISTRO_PENSIONADO: showCompValRegistro(idPersona,idTramite,titulo);
									break;
				case REGISTRO_CONCUBINA: showCompValRegistro(idPersona,idTramite,titulo);
									break;
				case REGISTRO_CONYUGE: showCompValRegistro(idPersona,idTramite,titulo);
									break;
				case REGISTRO_PERSONA_EN_UNION_CIVIL: showCompValRegistro(idPersona,idTramite,titulo);
									break;
				case REGISTRO_HIJOS: showCompValRegistro(idPersona,idTramite,titulo);
									break;
				case REGISTRO_PADRES: showCompValRegistro(idPersona,idTramite,titulo);
									break;									
				case CANCELA_REGISTRO: showCompRechazoSol(titulo,idTipoTramite);
									break;					
				case CORRECCION_DATOS_DERECHOHABIENTE: showComprobanteCambioDatos(idPersona, idTramite, titulo);
									break;
				case SOLICITUD_REGISTRO: showCompSolRegistro(idSolicitud,titulo);
									break;
				case AUTORIZACION_CIRCUNSCRIPCION: showComprobanteAutorizacionCircunscripcion(idPersona,idTramite, titulo, umf, umfusuario);
									break;
				case SUSPENSION_CIRCUNSCRIPCION: showComprobanteSuspencion(idPersona,idTramite,umf, umfusuario);
									break;
				case CAMBIO_MEDICO: showComprobanteCambioMedico(idPersona,idTramite);
									break;
				case CAMBIO_UMF: showComprobanteCambioUmf(idPersona,idTramite, titulo, umf, umfusuario);
									break;
				case ASIGNACION_MEDICO: showComprobanteCambioConsultorio(idTramite, titulo);
									break;
				case BAJA_DEFUNCION: showComprobanteValidacionBaja(idTramite, titulo);
									break;
				case BAJA_CONCUBINATO: showComprobanteValidacionBaja(idTramite, titulo);
									break;
				case BAJA_DIVORCIO: showComprobanteValidacionBaja(idTramite, titulo);
									break;
				case BAJA_DEPENDENCIA: showComprobanteValidacionBaja(idTramite, titulo);
									break;
				case BAJA_PERSONA_UNION_CIVIL: showComprobanteRechazo(titulo,idTipoTramite,idTramite,idSolicitud);
									break;
				case BAJA_AUTORIDAD_NORMATIVA : showComprobanteValidacionBaja(idTramite, titulo);
									break;
				case PRORROGA_ESTUDIOS: showComprobantesProrroga(idTramite,idPersona);
									break;
				case PRORROGA_ENFERMEDAD: showComprobantesProrroga(idTramite,idPersona);
									break;
				case PRORROGA_INVALIDEZ: showComprobantesProrroga(idTramite,idPersona);
									break;
				case PRORROGA_PERMANENTE: showComprobantesProrroga(idTramite,idPersona);
									break;
				case PRORROGA_TEMPORAL: showComprobantesProrroga(idTramite,idPersona);
									break;
				case PRORROGA_ACUERDOS: showComprobantesProrroga(idTramite,idPersona);
									break;
				case PRORROGA_OBSTETRICOS: showComprobantesProrroga(idTramite,idPersona);
									break;
				case PRORROGA_LAUDO: showComprobantesProrroga(idTramite,idPersona);
									break;
			}
		}
	}
}

function showComprobanteCambioDatos(idPersona, idTramite, titulo){
	var direccion= context_path + "/documentos/cambioDatos?idPersona="+idPersona+"&idTramite="+idTramite+"&titulo="+titulo;
	var page= context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
	window.resizeTo(800,600);
}

function showComprobanteCambioUmf(idPersona,idTramite, titulo, umf, umfusuario){
	if(umfusuario == umf) {
		var direccion= context_path + "/documentos/cambioClinica?idPersona="+idPersona+"&idTramite="+idTramite+"&titulo="+titulo+"";
		var page= context_path + "/resources/js/delta/viewPdf.html";
		window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
	} else {
		var direccion= context_path + "/documentos/cambioClinicaO?idPersona="+idPersona+"&idTramite="+idTramite;
		var page= context_path + "/resources/js/delta/viewPdf.html";
		window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
	}
}

function showComprobanteAutorizacionCircunscripcion(idPersona,idTramite, titulo, umf, umfUsuario){
	var direccion= "";
	
	if(umf == umfUsuario)
		direccion = context_path + "/documentos/circunscripcionA?idPersona="+idPersona+"&idTramite="+idTramite+"&titulo="+titulo+"";
	else
		direccion = context_path + "/documentos/documentosAutorizacion?idPersona="+idPersona+"&idTramite="+idTramite+"&ind=1";
	var page= context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showComprobanteSuspencion(idPersona,idTramite,umf, umfUsuario){
	
	var direccion= "";
	if(umf != umfUsuario)
		direccion = context_path + "/documentos/circunscripcionS?idTramite="+idTramite+"&idPersona="+idPersona+"&titulo=Suspensi\u00F3n de Servicios en circunscripci\u00F3n for\u00E1nea";
	else
		direccion = context_path + "/documentos/documentosAutorizacion?idPersona="+idPersona+"&idTramite="+idTramite+"&ind=0";
	
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showComprobanteCambioMedico(idPersona,idTramite){
	var direccion= context_path + "/documentos/cartilla?idPersona="+idPersona+"&idTramite="+idTramite;
	var page= context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}
function showComprobanteCambioConsultorio(idPersona,idTramite,titulo){
	var direccion= context_path + "/documentos/cambioConsultorio?idPersona="+idPersona+"?idTramite="+idTramite+"&titulo="+titulo;
	var page= context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}
function showCompSolRegistro(idSolicitud,titulo){
	var direccion=context_path + "/documentos/comprobanteSolicitud?idSolicitud="+idSolicitud+"&titulo="+titulo;
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showCompValRegistro(idPersona,idTramite,titulo){
	var direccion=context_path + "/documentos/documentoRegistro?idPersona="+idPersona+"&idTramite="+idTramite+"&titulo="+titulo;
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showCompValRegistroDep(idTramite,personas,tipoTramite,titulo){
	var direccion=context_path + "/documentos/documentoRegistroDep?idTramite="+idTramite+"&personas="+personas+"&tipoTramite="+tipoTramite+"&titulo="+titulo;
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showCompRechazoSol(titulo,tipoTramite){
	var direccion=context_path + "/documentos/rechazoSolicitud?titulo="+titulo+"&tipoTramite="+tipoTramite;
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showComprobanteValidacionBaja(idTramite, titulo){
	var direccion=context_path + "/documentos/documentosBaja?idTramite="+idTramite+"&titulo="+titulo+"";
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showComprobanteRechazo(titulo,tipoTramite,idTramite,idSolicitud){
	var direccion=context_path + "/documentos/rechazoSolicitud?titulo="+titulo+"&tipoTramite="+tipoTramite+"&idTramite="+idTramite+"&idSolicitud="+idSolicitud;
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showComprobantesProrroga(idTramite,idPersona){
	var direccion=context_path +'/documentos/documentosProrroga?idTramite='+idTramite+'&idDerechohabiente='+idPersona;
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
}
