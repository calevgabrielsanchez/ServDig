URL_REGISTRO = context_path + "/tramite/detalle/registro"; 
URL_REGISTRO_DER = context_path + "/tramite/detalle/registro";
URL_CORRECCION = context_path + "/tramite/detalle/correccion";
URL_AUTORIZACION_CIRCUNSCRIPCION = context_path + "/tramite/detalle/circunscripcion/autorizacion";
URL_SUSPENCION_CIRCUNSCRIPCION = context_path + "/tramite/detalle/circunscripcion/suspension";
URL_CAMBIO_MEDICO = context_path + "/tramite/detalle/cambioTurno";
URL_CAMBIO_UMF = context_path + "/tramite/detalle/cambioUmf";
URL_ASIGNACION_MEDICO = context_path + "/tramite/detalle/asignacionMedico";
URL_BAJA = context_path + "/tramite/detalle/bajas";
URL_PRORROGA = context_path + "/tramite/detalle/prorroga";

URL_VALIDAR_REGISTRO = context_path + "/tramite/registro/retomar";
URL_VALIDAR_CORRECCION = context_path + "/derechohabiente/correccion/datosPersonales/validar";
URL_VALIDAR_CAMBIO_MEDICO = context_path + "/derechohabiente/correccion/cambioMedico/validar";
URL_VALIDAR_CAMBIO_UMF = context_path + "/derechohabiente/correccion/cambioUmf/validar";
URL_VALIDAR_BAJA = context_path + "/derechohabiente/baja/validar";
URL_VALIDAR_ASIGNACION_MEDICO = context_path + "/derechohabiente/correccion/asignarMedico/validar";
URL_VALIDAR_CIRCUNSCRIPCION = context_path + "/derechohabiente/correccion/circunscripcion/autorizacion/validacion";
URL_VALIDAR_CIRCUNSCRIPCION_SUSPENSION = context_path + "/derechohabiente/correccion/circunscripcion/suspension/validacion";


function detalleTramite(idTramite,idHtml,tipoTramite,idPersona) {
	
	this.mostrar = function() {
		var url;
		
		var parametros = {
				'idTramite': idTramite,
				'idPersona': idPersona
		}
		
		switch(tipoTramite) {
			case REGISTRO_PERSONA: url = URL_REGISTRO;
								break;
			case REGISTRO_DERECHOHABIENTE: url = URL_REGISTRO_DER;
								break;
			case REGISTRO_ASEGURADO: url = URL_REGISTRO_DER;
								break;
			case REGISTRO_PENSIONADO: url = URL_REGISTRO_DER;
								break;
			case REGISTRO_CONCUBINA: url = URL_REGISTRO_DER;
								break;
			case REGISTRO_CONYUGE: url = URL_REGISTRO_DER;
								break;
			case REGISTRO_HIJOS: url = URL_REGISTRO_DER;
								break;
			case REGISTRO_PADRES: url = URL_REGISTRO_DER;
								break;
			case REGISTRO_PERSONA_UNION_CIVIL: url = URL_REGISTRO_DER;
								break;
			case CORRECCION_DATOS_DERECHOHABIENTE: url = URL_CORRECCION;
								break;
			case AUTORIZACION_CIRCUNSCRIPCION: url = URL_AUTORIZACION_CIRCUNSCRIPCION;
								break;
			case SUSPENSION_CIRCUNSCRIPCION: url = URL_SUSPENCION_CIRCUNSCRIPCION;
								break;
			case CAMBIO_MEDICO: url = URL_CAMBIO_MEDICO;
								break;
			case CAMBIO_UMF: url = URL_CAMBIO_UMF;
								break;
			case ASIGNACION_MEDICO: url = URL_ASIGNACION_MEDICO;
								break;
			case BAJA_DEFUNCION: url = URL_BAJA;
								break;
			case BAJA_CONCUBINATO: url = URL_BAJA;
								break;
			case BAJA_DIVORCIO: url = URL_BAJA;
								break;
			case BAJA_DEPENDENCIA: url = URL_BAJA;
								break;
			case BAJA_UNION_CIVIL: url = URL_BAJA;
								break;
			case PRORROGA_ESTUDIOS: url = URL_PRORROGA;
								break;
			case PRORROGA_ENFERMEDAD: url = URL_PRORROGA;
								break;
			case PRORROGA_INVALIDEZ: url = URL_PRORROGA;
								break;
			case PRORROGA_PERMANENTE: url = URL_PRORROGA;
								break;
			case PRORROGA_TEMPORAL: url = URL_PRORROGA;
								break;
			case PRORROGA_ACUERDOS: url = URL_PRORROGA;
								break;
			case PRORROGA_OBSTETRICOS: url = URL_PRORROGA;
								break;
			case PRORROGA_LAUDO: url = URL_PRORROGA;
								break;
			
		}
		
		$("#"+idHtml).load(url,parametros);
	}
}

function ocultarBotonValidar(){
	if($("#umfSolicitud").val() !=  $("#umfUsuario").val())
		$("#validar").hide();
	
}

function validarTramite(idTramite,solicitud,persona,tipoTramite) {
	fnAbrirMensajeEsperePorFavor();
	switch(tipoTramite) {
		case REGISTRO_DERECHOHABIENTE: 
			location.href = URL_VALIDAR_REGISTRO+"/"+solicitud;
			break;
		case REGISTRO_ASEGURADO: 
			location.href = URL_VALIDAR_REGISTRO+"/"+solicitud;
			break;
		case REGISTRO_PENSIONADO: 
			location.href = URL_VALIDAR_REGISTRO+"/"+solicitud;
			break;
		case REGISTRO_CONCUBINA: 
			location.href = URL_VALIDAR_REGISTRO+"/"+solicitud;
			break;
		case REGISTRO_CONYUGE: 
			location.href = URL_VALIDAR_REGISTRO+"/"+solicitud;
			break;
		case REGISTRO_PERSONA_UNION_CIVIL: 
			location.href = URL_VALIDAR_REGISTRO+"/"+solicitud;
			break;
		case REGISTRO_HIJOS: 
			location.href = URL_VALIDAR_REGISTRO+"/"+solicitud;
			break;
		case REGISTRO_PADRES: 
			location.href = URL_VALIDAR_REGISTRO+"/"+solicitud;
			break;
		case CORRECCION_DATOS_DERECHOHABIENTE:
			location.href = URL_VALIDAR_CORRECCION+"/"+solicitud;
			break;
		case CAMBIO_MEDICO:
			location.href = URL_VALIDAR_CAMBIO_MEDICO+"/"+solicitud;
			break;
		case CAMBIO_UMF:
			location.href = URL_VALIDAR_CAMBIO_UMF+"/"+solicitud;
			break;
		case BAJA_DEFUNCION:
			location.href = URL_VALIDAR_BAJA+"/"+solicitud+"/"+persona;
			break;
		case BAJA_CONCUBINATO:
			location.href = URL_VALIDAR_BAJA+"/"+solicitud+"/"+persona;
			break;
		case BAJA_DIVORCIO:
			location.href = URL_VALIDAR_BAJA+"/"+solicitud+"/"+persona;
			break;
		case BAJA_DEPENDENCIA:
			location.href = URL_VALIDAR_BAJA+"/"+solicitud+"/"+persona;
			break;
		case BAJA_UNION_CIVIL:
			location.href = URL_VALIDAR_BAJA+"/"+solicitud+"/"+persona;
			break;
		case BAJA_AUTORIDAD_NORMATIVA:
			location.href = URL_VALIDAR_BAJA+"/"+solicitud+"/"+persona;
			break;
		case ASIGNACION_MEDICO:
			location.href = URL_VALIDAR_ASIGNACION_MEDICO+"/"+solicitud;
			break;
		case SUSPENSION_CIRCUNSCRIPCION:
			location.href = URL_VALIDAR_CIRCUNSCRIPCION_SUSPENSION+"/"+solicitud;
			break;
		case AUTORIZACION_CIRCUNSCRIPCION:
			location.href = URL_VALIDAR_CIRCUNSCRIPCION+"/"+solicitud;
			break;
		
	}
}

function getCookie(name) {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length == 2) return parts.pop().split(";").shift();
}

function confirmarCancelacion(solicitudId) {
    
    var decision = $('<div></div>').text('¿Desea continuar con la cancelación del trámite?');
    decision.dialog({
        autoOpen: false,
        resizable: false,
        width: 300,
        title: 'Advertencia',
        modal: true,
        buttons: {
            "Sí": function() {
                var dialogoAdvertencia = $(this); // Guarda la referencia al diálogo de advertencia
                // Lógica para enviar solicitud de cancelación
                $.ajax({
                    url: context_path +'/solicitud/cancelarTramiteVentanilla',
                    type: 'POST',
                    data: { solicitudId: solicitudId },
                    complete: function() {
                        // Verificar el estado de la cancelación mediante cookies
                        var cancelacion = getCookie("cancelacion");
                        var cancelacionError = getCookie("cancelacionError");
                        var mensaje = cancelacion === "true" && cancelacionError === "false" ? 'Trámite cancelado exitosamente.' : 'Error al cancelar el trámite.';
                        
                        // Crea un nuevo diálogo para mostrar el resultado
                        $('<div></div>').appendTo('body')
                            .html('<div><h6>' + mensaje + '</h6></div>')
                            .dialog({
                                modal: true,
                                title: 'Resultado',
                                zIndex: 10000,
                                autoOpen: true,
                                width: 'auto',
                                resizable: false,
                                buttons: {
                                    Aceptar: function () {
                                        // Cierra el diálogo de resultado
                                        $(this).dialog("close"); 
                                        // Llama a cerrarDialogosAbiertos para asegurar que todos los diálogos se cierren
                                        cerrarDialogosAbiertos();
                                        // Redibujamos la tabla de Solicitudes Pendientes para actualizar el contenido
                                        solicitudesRegistradasVentanilla.fnDraw();
                                    }
                                },
                                close: function (event, ui) {
                                    $(this).remove(); 
                                }
                            });
                    }
                });
            },
            "No": function() {
                $(this).dialog('close');
            }
        }
    }).dialog('open');
}

function cerrarDialogosAbiertos() {
    $(".ui-dialog-content").each(function() {
        var dialogo = $(this);
        if (dialogo.dialog("isOpen")) {
            dialogo.dialog("close");
        }
    });
}
