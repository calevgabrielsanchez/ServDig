

$(document).ready(function() {
	marcarCamposConErroresFromSpanErrors("formComplemento",".error");
	setEventosChecarError("formComplemento",verificarErroresRegreso,"Error");
	
	
});


function finalizarTramite() {
	
	$.blockUI();
	
	var url = '/${mvn.web.app.root}'+'/wizard/tramite/registrar/domicilio/particular/finalizar/solicitud/';
	
	dialogoConfirmarCommon.dialog("option", "buttons", [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	var esAsignacionDomicilio = $('#esAsignaacionDomicilio').val();
	var indActualizacionDomicilioDerechohabiente = $('#indActualizacionDomicilioDerechohabiente').val();
	
	//La finalizacion puede tener 3 distintos flujos
	//1. El flujo normal, propio del wizard cuando se redirige a: .../wizard/tramite/registrar/domicilio/particular/finalizar/solicitud/
	//2. El flujo cuando es una asignacion del mismo domicilio, se redirige a: .../wizard/tramite/registrar/domicilio/particular/asignar/domicilio/
	//3. El flujo cuando es una actualizacion de domicilio, se redirige a: .../wizard/tramite/registrar/domicilio/particular/actualizar/domicilio/
	if( esAsignacionDomicilio != undefined && esAsignacionDomicilio == 'true' ){
		
		//Se valida que cveIdPersonafDom sea diferent de undefined, si no lo es se le asigna valor de -1
		//lo que indica que se trata de una actualizacion de domicilio que proviene del portal del asegurado/derechohabiente
		url = '/${mvn.web.app.root}'+'/wizard/tramite/registrar/domicilio/particular/asignar/';
		
		console.log("Bandera que determina el flujo operativo esAsignacionDomicilio: " + esAsignacionDomicilio);
		console.log("Bandera que determina el flujo operativo indActualizacionDomicilioDerechohabiente: " + indActualizacionDomicilioDerechohabiente);
		asignarDomicilio(url, true);
	}
	else if( indActualizacionDomicilioDerechohabiente != undefined && indActualizacionDomicilioDerechohabiente == 'true' ){
		
		//Se valida que cveIdPersonafDom sea diferent de undefined, si no lo es se le asigna valor de -1
		//lo que indica que se trata de una actualizacion de domicilio que proviene del portal del asegurado/derechohabiente
		url = '/${mvn.web.app.root}'+'/wizard/tramite/registrar/domicilio/particular/actualizar/';
		
		console.log("Bandera que determina el flujo operativo esAsignacionDomicilio: " + esAsignacionDomicilio);
		console.log("Bandera que determina el flujo operativo indActualizacionDomicilioDerechohabiente: " + indActualizacionDomicilioDerechohabiente);
		guardarFinalizarTramiteCommon(url, true);
	}
	else{
		guardarFinalizarTramiteCommon(url, true);
	}
}


