var nClassShow = 'showElement';
var nClassHidden = 'hiddenElement';
var solicitudCometConn = null;
var timeOut = null;
var objReporte;
var urlRissVentanilla = '/alta/riss/';
var urlVisorWeb = '/gestionSolicitud-visor-web/portlet/solicitudes/mostrarDocumentoResultante';

$(document).ready(function() {
	
});

$(function() {

	if ($('#huboError').length == 0) {
		var _idSolicitud = $('input#hdnIdSolicitud').val();
		var edoSolic = $('#edoSolicitud').val();

		if (edoSolic == null || edoSolic == 'EN_PROCESO') {
			CometCtrl.prototype.addSubscriber = function(subscription) {
				this.listInitialSubscription.push(subscription);
			};

			setTimeout(function() {
				solicitudCometConn = new CometCtrl();
				solicitudCometConn.addSubscriber(createSubscriber());
				solicitudCometConn.init(false);
			}, 1100);

			/* 
			 * Función que se ejecutará en automático después de 3 minutos,
			 * para refrescar la pantanlla y checar si la solicitud ya fue procesada.
			 */
			timeOut = setTimeout(function() {
				window.location = context_path + urlRissVentanilla + 'concluir?idSolicitud=' + _idSolicitud; 
			}, 30000);
			
		} else if (edoSolic != null && edoSolic == 'ATENDIDA') {
			fnSolicitudProcesada($('#hdnFolioSolicitud').val());
			
			$(window).on('beforeunload', function(){
				if ($('form#formComprobante').attr('alreadyDownloaded') == 'false') {
					$('div#notDownloadedWarning').show();
			      return 'No has descargado el comprobante, ¿Estas seguro de cambiar de página?';
				}
			});
			
		} else if (edoSolic != null && edoSolic == 'CANCELADA') {
			if ($('#motivoCancelacionSolic').length > 0
					&& $('#motivoCancelacionSolic').val() != null
					&& $('#motivoCancelacionSolic').val() != '') {
				fnSolicitudProcesadaError("La solicitud fue CANCELADA debido a: "
						+ $('#motivoCancelacionSolic').val()
						+ ". Favor de intentar nuevamente.");
			} else {
				fnSolicitudProcesadaError("La solicitud fue CANCELADA, favor de intentar nuevamente.");
			}
		}
		
		$('#descargarComp').click(function(e) {
			e.preventDefault();	
			
			var idS 	= $('input#idSolicitudHashed').val();
			var idT 	= $('input#idTramiteHashed').val();
			var idDocT 	= $('input#tipoDocumentoHashed').val();		
			
			$('form#formComprobante').attr('action', urlVisorWeb);		
			$('form#formComprobante input#idSolicitud').val(idS);
			$('form#formComprobante input#idTramite').val(idT);
			$('form#formComprobante input#tipoDocumento').val(idDocT);		
			$('form#formComprobante').attr('alreadyDownloaded', 'true');
			$('div#notDownloadedWarning').hide();
			$('form#formComprobante').submit();
			
		});	
		
	}
});

var fnSolicitudProcesada = function(msg) {
	
	$('span#pasoProceso').text('Solicitud Procesada');
	$('span#folioSolicitudSuccess').text(msg);
	$('div#procesandoDiv').addClass(nClassHidden);
	$('div#successDiv').removeClass(nClassHidden);
	$('div#successDiv').addClass(nClassShow);
	fnShowNvaSolicDiv();
	
};

var fnSolicitudProcesadaError = function(msg) {

	$('span#pasoProceso').text('Solicitud Procesada');
	$('span#errorMsg').text(msg);
	$('div#procesandoDiv').addClass(nClassHidden);
	$('div#errorDiv').removeClass(nClassHidden);
	$('div#errorDiv').addClass(nClassShow);
	fnShowNvaSolicDiv();
	
};

var fnShowNvaSolicDiv = function() {
	
	$('div#nvaSolicDiv').removeClass(nClassHidden);
	$('div#nvaSolicDiv').addClass(nClassShow);
	
};

function createSubscriber() {
	var folioSolicitud = $('input#hdnFolioSolicitud').val();

	return {
		channel : [ '/solicitud/modificacion/', folioSolicitud ].join(''),
		action : function(message) {
			var respuesta = message.data;

			if (respuesta.isExitoso || respuesta.isExitoso == 'true') {
				fnSolicitudProcesada(respuesta.folioSolicitud);
			} else {
				var mensajeError = respuesta.mensajeError;
				fnSolicitudProcesadaError(mensajeError);
			}

			if (timeOut != null) {
				clearTimeout(timeOut);
			}

			solicitudCometConn.disconnect(true);
		}
	};
}
