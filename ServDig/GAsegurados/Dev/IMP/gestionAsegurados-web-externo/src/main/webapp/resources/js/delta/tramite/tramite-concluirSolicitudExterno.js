/*
 * JS para el soporte de la funcionalidad del paso 4 de concluir y esperar la 
 * solicitud procesada
 */
var objReporte;

$(document).ready(function(){
	
//	var _strFolio = $('input#folio').val();
//	var _idSolicitud = $('input#id').val();
//	
//	$.getScript("/gestionSolicitud-web/static/resources/js/delta/solicitud/SolicitudTimer.js").done(function(script, textStatus) {
//		SolicitudTimer.init('timer');
//		SolicitudTimer.isEnProceso(_strFolio);
//		SolicitudTimer.setOnSolicitudProcesada(fnSolicitudProcesada);
//	}).fail(function(jqxhr, settings, exception) {
//	  alert('Error al cargar el script /gestionSolicitud-web/static/resources/js/delta/solicitud/SolicitudTimer.js');
//	});
	
	fnSolicitudProcesada(); // como no hubo procesamiento de la solicitud, luego luego mostramos el div con el boton de imprimir el reporte
	
    $('form#formComprobante').submit( function(){
    	fnSetDialogoReporte();
    	objReporte.dialog('open');
    	$.unblockUI();
    	return false;
    });
	
});


var nClassShow = 'showElement';
var nClassHidden ='hiddenElement';

/**
 * Funcion para el manejo de la funcionalidad
 * de cuando la solicitud ya fue procesada
 */
var fnSolicitudProcesada = function(){
	
	fnSetDialogoReporte();
	
	$('div#comprobante').removeClass(nClassHidden);
	$('div#comprobante').addClass(nClassShow);
	objReporte.dialog('open');
}

var fnSetDialogoReporte = function(){
	
	var _strFolio = $('input#folio').val();
	var _idSolicitud = $('input#id').val();
	
	/*
	 * Configuracion para mostrar el comprobante de tramite
	 */
	
	/*
	 * Inicializamos el dialogo que contiene la pantalla 
	 * de la consulta de personas morales.
	 */
	var horizontalPadding = 15;
    var verticalPadding = 15;
    var urlFrame  = context_path + '/reporte/comprobante/externo/' +_idSolicitud ;
	
	var d = $('#reporteFrame').html(
			'<iframe id="site" src="' + urlFrame
		 			+ '" width="100%" height="100%" frameborder="0"/>');
	    /*
	     * Configuracion del dialogo
	     */
	    objReporte = d.dialog({
	    	
	        title: 'Comprobante de tr\u00E1mite',
	        autoOpen: false,
	        width: 500,
	        height: 500,
	        modal: true,
	        resizable: false,
	        autoResize: true,
	        overlay: {
	            opacity: 0.5,
	            background: "black"
	        }
	    }).width(500).height(500);
	
}
