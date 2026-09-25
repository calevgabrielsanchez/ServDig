var objCtrl = parent.identificarCambiosAutomaticosPersonaFisicaCtrl;

$(document).ready(function() {

	$('#indicadorMostrarPantalla').val(objCtrl.datosEntrada.indMostrarPantalla);
	$('#isUsuarioExterno').val(objCtrl.datosEntrada.indUsuarioExterno);
	$('#busquedaIdPersona').val(objCtrl.datosEntrada.idPersona);
	$('#busquedaCurp').val(objCtrl.datosEntrada.curp);
	$('#busquedaRfc').val(objCtrl.datosEntrada.rfc);
	$('#busquedaNombre').val(objCtrl.datosEntrada.nombrePersona);
	$('#busquedaPrimerApellido').val(objCtrl.datosEntrada.primerApellido);
	$('#busquedaSegundoApellido').val(objCtrl.datosEntrada.segundoApellido);
	$('#busquedaIndRENAPO').val(objCtrl.datosEntrada.indBusquedaRENAPO);
	$('#busquedaIndSAT').val(objCtrl.datosEntrada.indBusquedaSAT);
	
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
