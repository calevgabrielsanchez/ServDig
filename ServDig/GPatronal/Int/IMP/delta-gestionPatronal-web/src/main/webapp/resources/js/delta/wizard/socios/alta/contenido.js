var urlWizardSocios	= context_path + '/wizard/tramite/socios/';
var idFormaSocios	= 'soForm';
var dialogoConfirmarCancelar;
var dialogoConfirmarCommon;

$(document).ready(function() {
	
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	//Siguiente - Seleccion de RL
	$('#mostrarRLVentanilla').click(function() {
		procesarSolicitudVentanilla();
	});
	
	$('#finalizarTramite').click(function() {

		if(parent.WizardAltaSociosCtrl.config.idOrigen == 2){
			//INTERNET
			var afectadoCopy = $.extend({},parent.AtributosPersonaCtrl.personaPortal);
			//Se escapan las comillas dobles en caso de que las contenga la cadena para que se pueda abrir el componente de firma
			afectadoCopy.nombreRazonSocial = afectadoCopy.nombreRazonSocial.replace(/\"/g,"\\\""); 
			
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
					afectado: [afectadoCopy],
					tipoAcuse: '1',
					acuse: 'CDCT'
				};
				
				parent.iniciarFirmaDigital(componenteFirma);			
		}
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
	
	if(parent.WizardAltaSociosCtrl.config.idOrigen == 2){
		/* 
		 * Al cerrar el di�logo de la firma digital,
		 * se ejecuta la funci�n para finalizar el tr�mite
		 */
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			
			inicializarDialogo();
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
	var url = urlWizardSocios+'procesarDatosFirma';
	
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
	var url = urlWizardSocios+'finalizar/solicitud/'+idSolicitudPendiente;
	
	$.postJSON(url, null, function(resultado) {			
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
		cerrarWizard();		
	}).error(function(resultado){
		fnProcesarErrores(resultado, 'form#'+idFormaSocios);
	});	
}

function cancelarTramite() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlWizardSocios+'cancelar/solicitud/'+ idSolicitudPendiente;
	
	inicializarDialogoCancelacion();
	
	$.postJSON(url, null, function(data) {
		mostrarDialogo(data.mensaje);		
	}).error(function(data){
		mostrarDialogo(data.mensaje);
	});
}

function cerrarWizard() {	
	parent.WizardAltaSociosCtrl.cerrar();
}

function inicializarDialogoCancelacion() {
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
}

function inicializarDialogo() {
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
}

function mostrarDialogo(mensaje) {	
	$('#mensajeDialogo').text(mensaje);
	dialogoConfirmarCommon.dialog('open');
}

function mostrarDialogoH(mensaje) {	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmarCommon.dialog('open');
}

//Funciones para mostrar seleccion de Representantes Legales
function procesarSolicitudVentanilla() {
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $('#folioSolicitud').val();
	var url = urlWizardSocios+'finalizar/solicitud/'+idSolicitudPendiente;
	var form = $('form#'+idFormaSocios).toObject();
	
	prepararRequest(url, form, true, mostrarRepresentantesLegales);
	
}

function mostrarRepresentantesLegales(response){
	if (response.mensaje != undefined && response.mensaje != null) {
		var idSolicitudPendiente = $('#idSolicitud').val();
		setTimeout(function(){
			var urlAction = parent.representantesLegalesCtrl.config.urlRLVentanilla + idSolicitudPendiente+"/"+idTipoTramite;
			$.blockUI();
			$('form#'+idFormaSocios).attr('action', urlAction);
			$('form#'+idFormaSocios).submit();
		}, 90);		
	}else{
		var mensaje = "Ocurrio un error al procesar la solicitud.";
		if (response.error == "") {			
		} else {
			mensaje = response.error;
		}
		mostrarDialogoH(mensaje);
	}	
}