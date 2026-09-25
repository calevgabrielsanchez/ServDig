var objCtrl = parent.identificarCambiosAutomaticosPersonaMoralCtrl;

$(document).ready(function() {

	$('#indicadorMostrarPantalla').val(objCtrl.datosEntrada.indMostrarPantalla);
	$('#busquedaIdPersona').val(objCtrl.datosEntrada.idPersona);
	$('#busquedaRfc').val(objCtrl.datosEntrada.rfc);
	
	//Se valida si se desea mostrar pantalla
	var aux = $('#indicadorMostrarPantalla').val().toLowerCase() == 'true';
	
	if(aux == true){
		fnICAPantalla();
	}else{
		fnICASinPantalla();
	}
	
	$('#btnAceptarDiv').click(function() {
		objCtrl.cerrar();
	});
});

// Ir al servicio de ICA
var fnICAPantalla = function() {
	$('#forma').submit();
};

//Ir al servicio de ICA sin pantalla de respuesta
var fnICASinPantalla = function() {
	
	var url = 'consultar-comparar-JSON';
	var oForm = $('#forma').toObject();
	
	$.postJSON(url, oForm, function(data) {
		objCtrl.setDatosSalida(data.icaDatosRespuesta);
		objCtrl.cerrar();
	}).error(function(data) {
		var objError = jQuery.parseJSON(data.responseText);
		objCtrl.setDatosSalida(objError.icaDatosRespuesta);
		$('#loadingDiv').hide();
		fnProcesarErrores(data, "form#forma");
		$('#btnAceptarDiv').show();
	});	
};
