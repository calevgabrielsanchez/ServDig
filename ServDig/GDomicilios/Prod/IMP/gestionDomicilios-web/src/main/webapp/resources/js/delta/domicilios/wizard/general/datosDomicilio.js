/**
 * 
 */

$(document).ready(
	function () {
		
		$("#formRegistro").deshabilitarContenido(false);
		
		$("#ubicarDomicilioDer").click( function() {
			ubicarDomicilioIntegrante();
		});
		
		$("#continuarAUmf").click(function() {
			validarDomicilio();
		});
		
	}
);

function validarDomicilio() {
	
	$("form#formRegistro").habilitarContenido(false);
	var oForm = $("form#formRegistro").toObject();
	var url = '/portalDerechohabiente-web/wizard/registro/validaciones';
	fnHideErrores("form#formRegistro");
	
	$.blockUI();
	$.postJSON(url, oForm, function(data2) {
		
		if(!data2.correcto)  {
			habilitarDesabilitarCamposDatosBasicos(false,false);
			$.unblockUI();
			mostrarMensajeError(data2.mensaje);
		} else {
			$.blockUI();
			$("#formRegistro").habilitarContenido(false);
			$("#formRegistro").attr("action","/portalDerechohabiente-web/wizard/registro/siguiente");
			$("#formRegistro").submit();
		}
	}).error(function(data){
		habilitarDesabilitarCamposDatosBasicos(false,false);
		$.unblockUI();
		fnProcesarErrores(data, "form#formRegistro");
	});
}


function habilitarDesabilitarCamposDatosBasicos(habilitar,envio) {
	if(habilitar) {
		$("#formRegistro").habilitarContenido(false);
	} else {
		$("#formRegistro").deshabilitarContenido(false);
	}
}

