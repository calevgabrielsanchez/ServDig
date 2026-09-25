/*
 * JS para el soporte de la funcionalidad del paso 4 de concluir y esperar la 
 * solicitud procesada
 */
var objReporte;
var nClassShow = 'showElement';
var nClassHidden = 'hiddenElement';
var solicitudCometConn = null;
var timeOut = null;

$(document).ready(function() {
	
	if ($('#huboError').length == 0) {
		var _idSolicitud = $('input#hdnIdSolicitud').val();
		var _hsdp = $('input#hDP').val();
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
		     * Funci�n que se ejecutar� en autom�tico despu�s de 3 minutos,
		     * para refrescar la pantanlla y checar si la solicitud ya fue procesada.
		     */
		    timeOut = setTimeout(function() {
		    	window.location = context_path + '/tramite/concluir?iS=' + _idSolicitud + '&hDP=' + _hsdp; 
			}, 1000);
		} else if (edoSolic != null && edoSolic == 'ATENDIDA') {
			fnSolicitudProcesada($('#hdnFolioSolicitud').val());
			
			$(window).on('beforeunload', function(){
				if ($('form#formComprobante').attr('alreadyDownloaded') == 'false') {
					$('div#notDownloadedWarning').show();
			      return 'No has descargado el comprobante, �estas seguro de cambiar de p�gina?';
				}
			});
		} else if (edoSolic != null && edoSolic == 'CANCELADA') {
			if ($('#motivoCancelacionSolic').length > 0
					&& $('#motivoCancelacionSolic').val() != null
					&& $('#motivoCancelacionSolic').val() != '') {
				fnSolicitudProcesadaError("La solicitud fue CANCELADA debido a: "
						+ $('#motivoCancelacionSolic').val() + ". Favor de intentar nuevamente.");
			} else {
				fnSolicitudProcesadaError("La solicitud fue CANCELADA, favor de intentar nuevamente.");
			}
		}
		
		$('#descargarComp').click(function(e) {
			e.preventDefault();
		
			var _idSolicitud = $('input#hdnIdSolicitud').val();
			var url = context_path + '/reporte/comprobante/interno';
		
			$('form#formComprobante').attr('action', url);
			$('input#si', 'form#formComprobante').val(_idSolicitud);
			$('form#formComprobante').attr('alreadyDownloaded', 'true');
			$('div#notDownloadedWarning').hide();
			$('form#formComprobante').submit();
			
		});
		
		$('#imprimirNSS').click(function(e) {
			e.preventDefault();
		
			var _idSolicitud = $('input#hdnIdSolicitud').val();
			var url = context_path + '/reporte/imprimeNSS';
		
			$('form#formImprimirNSS').attr('action', url);
			$('input#si', 'form#formImprimirNSS').val(_idSolicitud);
			$('form#formImprimirNSS').submit();
			
		});
	}
	
	$('.btnFinTramite').click(function(){
		$('form#terminarTramiteForm').submit();
	});
});

/**
 * Funcion para el manejo de la funcionalidad
 * de cuando la solicitud ya fue procesada
 */
var fnSolicitudProcesada = function(msg) {
	
	$('span#pasoProceso').text('Paso 4: Solicitud procesada');
	
	$('span#folioSolicitudSuccess').text(msg);

	$('div#procesandoDiv').addClass(nClassHidden);

	$('div#successDiv').removeClass(nClassHidden);
	$('div#successDiv').addClass(nClassShow);

	cleanSession();
};

var fnSolicitudProcesadaError = function(msg) {

	$('span#pasoProceso').text('Paso 4: Solicitud procesada');

	$('span#errorMsg').text(msg);

	$('div#procesandoDiv').addClass(nClassHidden);

	$('div#errorDiv').removeClass(nClassHidden);
	$('div#errorDiv').addClass(nClassShow);
	
	cleanSession();

};

function createSubscriber() {
	var folioSolicitud = $('#hdnFolioSolicitud').val();
	
    return {
        channel: ['/solicitud/modificacion/', folioSolicitud].join(''), 
        action: function(message) {
        	var respuesta = message.data;

        	if (respuesta.isExitoso || respuesta.isExitoso == 'true') {
        		// Se muestra el comprobante de la asignacion de NSS
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

function cleanSession() {
	$.ajax({
		url : "/gestionAsegurados-web-externo/tramite/limpiar-sesion",
		type: "POST",
		dataType : "json",
		data : {
			hDP: $('#hDP').val()
		}
	});
}