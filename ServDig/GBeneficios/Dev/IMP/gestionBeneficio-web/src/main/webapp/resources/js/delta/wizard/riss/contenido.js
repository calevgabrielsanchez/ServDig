var urlBeneficio 		= '/${mvn.web.app.root}/wizard/tramite/solicitar/riss';
var idFormaBeneficio	= 'soForm';

var dialogoConfirmarCancelar;
var dialogoConfirmarCommon;

$(document).ready(function() {	
	
	$('#finalizarTramite').click(function() {

		if(parent.WizardSolicitarRifCtrl.config.idOrigen == 2){
			//Si es INTERNET.
			var componenteFirma = {
				idTipoSolicitud : codigoTipoSolicitud,
				descripcionTipoSolicitud : descripcionTipoSolicitud,
				idTipoTramite : arrayCodigoTipoTramite,
				folioSolicitud : $('#folioSolicitud').val(),
				curp : parent.FirmanteCtrl.curp,
				rfc : parent.FirmanteCtrl.rfc,
				validarRFC : true,
				registroPatronal : parent.AtributosPersonaCtrl.personaPortal.registroPatronal,
				nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
				fechaElectronica : datosEntradaFirma.fechaElectronica,
				cad_original : $('#contenidoFirmar').val(),
				tipo_operacion : 'firmaCMS',
				firma_archivo : false,
				min_archivos : 0,
				max_archivos : 0,
				afectado: [parent.AtributosPersonaCtrl.personaPortal],
				tipoAcuse: '1',
				acuse: 'CDCT'
			};
			
			parent.iniciarFirmaDigital(componenteFirma);
		}else{
			finalizarTramite();
		}
		
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
	
	dialogoConfirmarCommon = $( "#dialog-confirm-common" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false
	 });
	
	
	if(parent.WizardSolicitarRifCtrl.config.idOrigen == 2){
		/* 
		 * Al cerrar el di�logo de la firma digital,
		 * se ejecuta la funci�n para finalizar el tr�mite
		 */
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			dialogoConfirmarCommon.dialog("option", "buttons", [ {
				text : 'ACEPTAR',
				click : function() {
					$(this).dialog('close');
				}
			}]);
			
			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				mostrarDialogo("La validaci\u00f3n de la firma no pudo ser realizada");
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
					mostrarDialogo("La validaci\u00f3n de la firma no pudo ser realizada");
				}
			}
		});
	}
	

});

function firmarTramite(firmaResponse) {
	var url = urlBeneficio+'/procesarDatosFirma';
	
	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		mostrarDialogo(data.mensaje);
	});
}

function finalizarTramite() {
	
	$.blockUI();
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $('#folioSolicitud').val();	
	var url = urlBeneficio+'/finalizar/solicitud/'+ idSolicitudPendiente;
	
	$.postJSON(url, null, function(resultado) {
		
		$.unblockUI();
		
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
		cerrarWizard();
		
	}).error(function(resultado){
		fnProcesarErrores(resultado, 'form#'+idFormaBeneficio);
	});

}

function cancelarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlBeneficio+'/cancelar/solicitud/'+ idSolicitudPendiente;
	
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, null, function(data) {
		mostrarDialogo(data.mensaje);
	}).error(function(data){
		mostrarDialogo(data.mensaje);
	});
}

function cerrarWizard() {	
	parent.WizardSolicitarRifCtrl.cerrar();
}

function mostrarDialogo (mensaje) {
	$('#mensajeDialogo').text(mensaje);
	dialogoConfirmarCommon.dialog('open');
}