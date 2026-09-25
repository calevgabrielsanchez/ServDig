$(document).ready(function(){
	
	$("#enviarReporte").on("click",reenviarCorreo);
	
	$('#imprimirReporte').on("click",imprimirReporte);
	
	$('#descargarReporte').on("click",download);
	
	$('#imprimirConstancia').on("click",imprimirReporte);
	
	$('.salir').on("click",finalizaTramite);
	//lanzamos la encuesta una vez que se carga la pagina
	startEncuestaHC(500,$("#homoclaveTramite").val());
});

var imprimirReporte = function() {
	getReporte(false);
}

var download = function() {
	getReporte(true);
}

var getReporte = function(descargar) {
	var $formulario = $('#formVerReporte'),
	url = context_path + "/vigencia" + (descargar ? "/downloadReport" : "/viewReport")
	$formulario.attr("action", url);
	$formulario.submit();
	$.unblockUI();
}

var finalizaTramite = function() {
	$('#formSalir').submit();
	$.unblockUI();
}

var reenviarCorreo = function() {
	
	var url = context_path + "/vigencia/reenvioMail";
	
	$.postJSON(url, {} , function(data){
		mostrarAviso(data.mensaje);
	});
}

var mostrarAviso = function(mensaje) {
	
	$divMensajes = $( "#mensajes" );
	$divMensajes.dialog({
		resizable: false,
		height:'auto',
		modal: true,
		title: 'Mensaje de sistema',
		autoOpen: false,
	    closeOnEscape: false,
		buttons: {
			"Aceptar": function() {
				$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	$divMensajes.html(mensaje);
	$divMensajes.dialog('open');
}