var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {
	
	// Se carga el módulo de medios fiscales
	if($('#isRetomar').val() == 'true'){
		ejecutarRetomarAdmonMediosFiscales();
	} else {
		ejecutarAdmonMediosFiscales();
	}
	
	$('#finalizarTramite').click(function() {

		var componenteFirma = {
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			idTipoTramite : arrayCodigoTipoTramite,
			folioSolicitud : $('#folioSolicitud').val(),
			curp : datosEntradaFirma.curp,
			rfc : datosEntradaFirma.rfc,
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
		autoOpen: false
	 });
	
	/* 
	 * Al cerrar el diálogo de la firma digital,
	 * se ejecuta la función para finalizar el trámite
	 */
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		dialogoConfirmar.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
			}
		}]);
		
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
			dialogoConfirmar.dialog('open');
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
				$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
				dialogoConfirmar.dialog('open');
			}
		}
	});

});

function firmarTramite(firmaResponse) {
	var url = '/gestionIndividuo-consulta-web/wizard/tramite/modificar/medios/fiscales/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	var resultado = null;
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = '/gestionIndividuo-consulta-web/wizard/tramite/modificar/medios/fiscales/finalizar/solicitud/'
				+ idSolicitudPendiente;
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	// Llamado a función común para el procesamiento de los cambios
	resultado = fnProcesarModifManual(url);
	
	/*
	 * Si resultado no trae el mensaje nulo, significa que fue exitoso y, por lo
	 * tanto, se debe ejecutar la función del comet para esperar la atención de
	 * la solicitud
	 */
	if (resultado.mensaje == null || typeof resultado.mensaje === 'undefined') {
		parent.ProcesandoSolicitudCtrl.abrir(idSolicitudPendiente);
		cerrarWizard();
	} else {
		$('#mensajeDialogo').text(resultado.mensaje);
		dialogoConfirmar.dialog('open');
	}
}

function guardarTramite() {
	var resultado = null;
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = '/gestionIndividuo-consulta-web/wizard/tramite/modificar/medios/fiscales/guardar/solicitud/'
				+ idSolicitudPendiente;
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	// Llamado a función común para el procesamiento de los cambios
	resultado = fnProcesarModifManual(url);

	$('#mensajeDialogo').text(resultado.mensaje);
	dialogoConfirmar.dialog('open');
}

function cancelarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = '/gestionIndividuo-consulta-web/wizard/tramite/modificar/medios/fiscales/cancelar/solicitud/'
				+ idSolicitudPendiente;
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function cerrarWizard() {	
	parent.WizardModificacionMediosFiscalesCtrl.cerrar();
}

function ejecutarAdmonMediosFiscales(){
	var cveFisica = $('#cveFisicaAdmon').val();
	var url = '/gestionMediosContacto-web/medios/fiscales/administrar/init/' + cveFisica;
	
	$.post(url, function(data) {
		$("#admonMediosContactoFiscales").html(data);
	}).error(function(data) {
		
	});	
}

function ejecutarRetomarAdmonMediosFiscales(){
	var idSolicitud = $('#idSolicitud').val();
	var idPersona = $('#idPersona').val();
	var url = '/gestionMediosContacto-web/medios/fiscales/administrar/init/retomar/' + idSolicitud + "/" + idPersona;
	
	$.post(url, function(data) {
		$("#admonMediosContactoFiscales").html(data);
	}).error(function(data) {
		
	});	
}