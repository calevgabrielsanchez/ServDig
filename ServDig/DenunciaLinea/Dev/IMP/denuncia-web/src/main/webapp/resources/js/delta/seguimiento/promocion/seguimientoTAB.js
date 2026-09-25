$(document).ready(function(){
		
	$("#btnGuardarSeg").click(
			function(e) {
				
				$("#labelFecNotif").html('');
				$("#labelFecAtencionOficio").html('');
				if(jsValidaRequeridosSeguimiento()){
					bloquear();
					var cvePromocion = $("form#seguimientoForm #cvePromocion").val();
					var fecNotificacionOficio = $("form#seguimientoForm #fecNotificacionOficio").val();
					var fecAtencionOficio = $("form#seguimientoForm #fecAtencionOficio").val();
					var observaciones = $("form#seguimientoForm #observaciones").val();
					if(observaciones.length > 199){
						observaciones = observaciones.substring(0,199);
					}
					
					var variable = '{' +
					   '"cvePromocion":"'+cvePromocion+'",'+
					   '"fechaNotificacion":"'+fecNotificacionOficio+'",'+
					   '"fechaAtencion":"'+fecAtencionOficio+'",'+
					   '"txObservaciones":"'+observaciones+'"}';
					var variableJson = jQuery.parseJSON(variable);
					$.postJSON("consulta/actualizaPromocionEXO.do", variableJson, function(data) {
						alert('El registro se actualizo correctamente');
						//oDgSeguimeinto.dialog("close");
					}).error(function(data){ 
						desbloquear();
						validarSesionExpirada(data);
					}).complete(function(){
						desbloquear();
					});	
						
					completarFlujoSeguimiento();
				}else{
					if($("#fecNotificacionOficio").val() == ''){
						$("#labelFecNotif").html('<label class="etiquetaError">Campo requerido</label>');
					}
					if($("#fecAtencionOficio").val() == ''){
						$("#labelFecAtencionOficio").html('<label class="etiquetaError">Campo requerido</label>');
					}
				}
			
				
			});

});

function jsValidaRequeridosSeguimiento(){
	
	var fecNotif  = $("#fecNotificacionOficio").val();
	var fecAtenOf = $("#fecAtencionOficio").val();
	var result = false;
	
	if(fecNotif != '' && fecAtenOf != ''){
		return true;
	}
	
	return result;
	
}

function completarFlujoSeguimiento(){
	
	$("form#seguimientoForm #fecNotificacionOficio").prop('disabled','true');
	$("form#seguimientoForm #fecAtencionOficio").prop('disabled','true');
	
	$("form#promocionCancelacionForm #refCancelacion").prop('disabled','true');
	$("form#promocionCancelacionForm #fecCancelacion").prop('disabled','true');
	$("form#promocionCancelacionForm #idMotivoCancelacion").prop('disabled','true');
	
	$("form#autAviDictamenSegForm #fechaAvisoDictamen").prop('disabled','true');
	$("form#autAviDictamenSegForm #numAvisoDictamen").prop('disabled','true');
	$("form#autAviDictamenSegForm #fechaInicio").prop('disabled','true');
	$("form#autAviDictamenSegForm #fechaFin").prop('disabled','true');
	
	//Deshabilitar botones 
	$('form#seguimientoForm :input#btnGenInvita').prop("disabled", true);
	$('form#seguimientoForm :input#btnGuardarSeg').prop("disabled", true);
	$('form#autAviDictamenSegForm :input#btnGuardarAut').prop("disabled", true);
	
}
	
	