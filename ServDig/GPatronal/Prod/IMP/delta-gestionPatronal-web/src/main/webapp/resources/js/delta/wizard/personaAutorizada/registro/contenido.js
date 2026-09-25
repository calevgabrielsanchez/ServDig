var urlWizardRegistroPA				= context_path + '/wizard/tramite/personaAutorizada/';
var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	/*
	 * se iniciliza el componente del acordeon con la opcion 'autoHeight: false'
	 * para que cada DIV colapsable tenga la altura de acuerdo a su contenido
	 */
	$('#acordeon').accordion({
		autoHeight : false,
		collapsible : true
	});

	$('#finalizarTramite').click(function() {
		var patrones = getPatronesSeleccionados();
		
		if(patrones.length == 0) {
			MostrarMensajeSeleccionarPatrones();
		} else {
			if(parent.WizardRegistroPersonaAutorizadaCtrl.config.idOrigen == 2){
				firmarRepresentante();
			}else{
				finalizarTramite();
			}
		}
	});
	$('#seleccionarPatrones').click(function() {
		buscarCURP();
	});

	$('#guardarTramite').click(function() {
		guardarTramite();
	});

	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});

	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false,
		buttons: {
			"ACEPTAR": function() {
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false
	 });
	 
});

function buscarCURP() {
		$("#formPersona").submit();
}

function firmarRepresentante() {
	
	var rfcRepresentante = $('#hdnRfcPersonaSesion').val();	
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
		}else {
			if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
				var firmaResponse = {
					cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
					recibo                       : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
					reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
					urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
					serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
					strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
					strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
				};

				firmarTramite(firmaResponse);
			} else {
				mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
			}
		}
	});

	var componenteFirma = {
		idTipoSolicitud : codigoTipoSolicitud,
		descripcionTipoSolicitud : descripcionTipoSolicitud,
		folioSolicitud : $('#folioSolicitud').val(),
		idTipoTramite : arrayCodigoTipoTramite,
		curp : datosEntradaFirma.curp,
		rfc : rfcRepresentante,
		validarRFC : true,
		registroPatronal : datosEntradaFirma.registroPatronal,
		nombreCompleto : datosEntradaFirma.nombreCompleto,
		fechaElectronica : datosEntradaFirma.fechaElectronica,
		cad_original : $('#contenidoFirmar').val(),
		tipo_operacion : 'firmaCMS',
		firma_archivo : false,
		min_archivos : 0,
		max_archivos : 0
	};

	parent.iniciarFirmaDigital(componenteFirma);
}

function firmarTramite(firmaResponse) {
	var url = urlWizardRegistroPA + 'procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	
		var url = urlWizardRegistroPA + 'registro/solicitud/finalizar';
		var patrones = getPatronesSeleccionados();
		var folioSolicitudPendiente = $("#folioSolicitud").val();
		var idSolicitudPendiente = $("#idSolicitud").val();
		
		var patronesSO = {
			'sujetosObligados' : patrones
		};
				
		dialogoConfirmar.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
				cerrarWizard();
			}
		}]);
		
		$.postJSON(url, patronesSO , function(data) {
			parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
			cerrarWizard();
		}).error(function(data){
			$('#mensajeDialogo').text(data.mensaje);
			dialogoConfirmar.dialog('open');
		});
}

function guardarTramite() {	
	var patrones = getPatronesSeleccionados();	
	if(patrones.length == 0) {
		MostrarMensajeSeleccionarPatrones();
	} else {
		var url = urlWizardRegistroPA + 'registro/solicitud/guardar';
		var patronesSO = {
			'sujetosObligados' : patrones
		};
				
		$.postJSON(url, patronesSO , function(data) {
			mostrarMensaje(data.mensaje);
		}).error(function(data){
			mostrarMensaje(data.mensaje);
		});
	}
}

function getPatronesSeleccionados() {
	var patrones = new Array();
	$("input[@name='patronSO']:checked").each(function() {
		var idPatron = $(this).val();
		 if($.trim(idPatron).length > 0)
			 patrones.push({"cveIdSujetoObligado": idPatron});
	 });
	
	return patrones;
}

function MostrarMensajeSeleccionarPatrones() {
	$('#mensajeDialogo').text("Debe seleccionar al menos un RP");

	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	dialogoConfirmar.dialog('open');
}

function mostrarMensaje(mensaje) {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmar.dialog('open');
}

function cancelarTramite() {
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlWizardRegistroPA + 'registro/solicitud/cancelar';
	
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardRegistroPersonaAutorizadaCtrl.cerrar();
}