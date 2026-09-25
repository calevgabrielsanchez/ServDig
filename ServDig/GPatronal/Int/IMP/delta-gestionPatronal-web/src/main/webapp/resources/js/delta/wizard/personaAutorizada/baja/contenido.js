var urlWizardBajaPA				= context_path + '/wizard/tramite/personaAutorizada/baja/';
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
		var personasAutorizadas = getPersonasAutorizadasSeleccionadas();
		if(personasAutorizadas.length == 0) {
			mostrarMensajeSeleccionarPersona();
		} else {
			if(parent.WizardBajaPersonaAutorizadaCtrl.config.idOrigen == 2){
				firmarRepresentante();
			}else{
				finalizarTramite();
			}
		}
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
	var url = urlWizardBajaPA + 'procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	
	var personasAutorizadas = getPersonasAutorizadasSeleccionadas();
	var url = urlWizardBajaPA + 'solicitud/finalizar';
	var folioSolicitudPendiente = $("#folioSolicitud").val();
	var idSolicitudPendiente = $("#idSolicitud").val();
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
		
	var tramitePA = {
		'personasAutorizadas' : personasAutorizadas
	};
		
	$.postJSON(url, tramitePA , function(data) {
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
		cerrarWizard();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
	
}

function guardarTramite() {
	
	var personasAutorizadas = getPersonasAutorizadasSeleccionadas();
	
	if(personasAutorizadas.length == 0) {
		mostrarMensajeSeleccionarPersona();
	} else {
		var url = urlWizardBajaPA + 'solicitud/guardar';
		var tramitePA = {
			'personasAutorizadas' : personasAutorizadas
		};
		
		$.postJSON(url, tramitePA , function(data) {
			mostrarMensaje(data.mensaje);
		}).error(function(data){
			mostrarMensaje(data.mensaje);
		});
	}
}

function getPersonasAutorizadasSeleccionadas() {
	var personasAutorizadas = new Array();
	$("input[@name='personaA']:checked").each(function() {
		var idPersonaAutorizada = $(this).val();
		 if($.trim(idPersonaAutorizada).length > 0)
			 personasAutorizadas .push({"cvePersonaAutorizada": idPersonaAutorizada});
	 });
	
	return personasAutorizadas;
}

function mostrarMensajeSeleccionarPersona() {
	$('#mensajeDialogo').text("Debe seleccionar al menos una persona autorizada");

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
	var url = urlWizardBajaPA + 'solicitud/cancelar';
	
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard(){	
	parent.WizardBajaPersonaAutorizadaCtrl.cerrar();
}