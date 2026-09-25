$(document).ready(function(){
	
	//$("#enviarReporte").on("click",reenviarCorreo);
	//quitamos el block ui ya que la encuesta funciona con ajax y se queda bloqueada la pantalla
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI); 

	$('#imprimirReporte').on("click",imprimirComprobante);
	
	$('#descargarReporte').on("click",downloadComprobante);
	
	$('#imprimirTarjeta').on("click",imprimirTarjeta);
	
	$('#descargarTarjeta').on("click",downloadTarjeta);
	
	$(".reenvioMAIL").on("click",reenviarCorreo);
	
	$('.salir').on("click",finalizaTramite);
	//lanzamos la encuesta una vez que se carga la pagina
	startEncuestaHC(500,$("#homoclaveTramite").val());
});

var imprimirComprobante= function() {
	imprimirReporte("/asignacionNSS/getComprobante");
}

var imprimirTarjeta= function() {
	imprimirReporte("/asignacionNSS/getTarjetaNSS");
}

var downloadComprobante= function() {
	imprimirReporte("/asignacionNSS/downloadComprobante");
}

var downloadTarjeta= function() {
	imprimirReporte("/asignacionNSS/downloadTarjetaNSS");
}

var imprimirReporte = function(url) {
	var urlReporte = context_path + ""+url;
	$('#formVerReporte').attr("action",urlReporte);
	$('#formVerReporte').submit();
	$.unblockUI();
}

var finalizaTramite = function() {
	$.blockUI();
	location.href = context_path + "/asignacionNSS/";

};

var reenviarCorreo = function() {
	
	var url = context_path + "/asignacionNSS/reenvioMail";
	
	$.postJSON(url, {} , function(data){
		var botones = {
			"Aceptar": function() {
				$( this ).dialog( "close" );
		 	}
		 }
		mostrarAviso(data.mensaje, botones, "Mensaje de sistema");
	});
}

var mostrarAviso = function(mensaje, botones, titulo) {
	
	$divMensajes = $( "#mensajes" );
	$divMensajes.dialog({
		resizable: false,
		height:'auto',
		modal: true,
		title: titulo,
		autoOpen: false,
	    closeOnEscape: false,
		buttons: botones
	 });
	
	$divMensajes.html(mensaje);
	$divMensajes.dialog('open');
}
