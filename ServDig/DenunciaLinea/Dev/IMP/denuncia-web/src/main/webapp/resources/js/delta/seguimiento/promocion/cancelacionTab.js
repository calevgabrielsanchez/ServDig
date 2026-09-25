$(document).ready(function(){
		
	$("#btnConfirmarCancel").click(
			function(e) {
				
				$("#labelRefCancelacion").html('');
				$("#labelFecCancelacion").html('');
				$("#labelMotivoCancelacion").html('');
				
				if(jsValidaRequeridosCancelacion()){
					bloquear();
					var cvePromocion = $("form#seguimientoForm #cvePromocion").val();
					var bandera = $("form#promocionCancelacionForm #bandera").val();
					var refCancelacion = $("form#promocionCancelacionForm #refCancelacion").val();
					var fecCancelacion = $("form#promocionCancelacionForm #fecCancelacion").val();
					var idMotivoCancelacion = $('form#promocionCancelacionForm #idMotivoCancelacion').val();
					//tab seguimiento
					var fecNotificacionOficio = $("form#seguimientoForm #fecNotificacionOficio").val();
					 
					var variable = '{' +
					   '"cvePromocion":"'+cvePromocion+'",'+
					   '"bandera":"'+bandera+'",'+
					   '"nuVolanteCancela":"'+refCancelacion+'",'+
					   '"fechaCancelacion":"'+fecCancelacion+'",'+
					   '"fechaNotificacion":"'+fecNotificacionOficio+'",'+
					   '"idMotivoCancelacion":"'+idMotivoCancelacion+'"}';
					
					var objetoJson = jQuery.parseJSON(variable);
					
					//wrapper.aoData = object;
					$.postJSON("consulta/actualizaPromocionEXO.do", objetoJson, function(data) {
						alert('El registro se actualizo correctamente');
						//oDgSeguimeinto.dialog("close");
					}).error(function(data){ 
						desbloquear();
						validarSesionExpirada(data);
					}).complete(function(){
						desbloquear();
					});		
					
					completaFlujoCancelacion();
					
				}else{
					
					if($("form#promocionCancelacionForm #refCancelacion").val() == ''){
						$("#labelRefCancelacion").html('<label class="etiquetaError">Campo requerido</label>');
					}
					if($("form#promocionCancelacionForm #fecCancelacion").val() == ''){
						$("#labelFecCancelacion").html('<label class="etiquetaError">Campo requerido</label>');
					}
					if($('form#promocionCancelacionForm #idMotivoCancelacion').val() == '-1'){
						$("#labelMotivoCancelacion").html('<label class="etiquetaError">Campo requerido</label>');
					}
				}				
			
			});// fin btnConfirmarCancel


});
/**
 * Valida los datos minimos requeridos para el Tab de cancelacion del 
 * seguimiento de la promocion
 * 
 * @returns
 */
function jsValidaRequeridosCancelacion(){

	var refCancelacion = $("form#promocionCancelacionForm #refCancelacion").val();
	var fecCancelacion = $("form#promocionCancelacionForm #fecCancelacion").val();
	var idMotivoCancelacion = $('form#promocionCancelacionForm #idMotivoCancelacion').val();
	//var idMotivoCancelacion = document.getElementById("idMotivoCancelacion").value;
	
	
	var resultado = false;
	if(refCancelacion != '' && fecCancelacion != '' && idMotivoCancelacion != '-1'){
	
		return true;
	}

	return false;
}





function obtenDatosIniciales(){
	

	var wrapper = new Object();
	wrapper.aoData = aoData;
	
	var oForm = $("#promocionCancelacionForm").toObject({mode:'first'});
	
	wrapper.oForm = oForm;
	
	$.postJSON("consulta/obtenFuncionariosCanc.do", wrapper, function(data) {
		$("form#promocionCancelacionForm #funcionarioReg").val(data.cveUsuario);
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
	//Código para el complete			
	});	
	
}


function completaFlujoCancelacion(){
	var fechaCancelacion = $("form#promocionCancelacionForm #fecCancelacion").val();
	
	$("form#seguimientoForm #fecCancelaOficio").val(fechaCancelacion);
	
	$("form#seguimientoForm #fecCancelaOficio").prop('disabled','true');
	$("form#seguimientoForm #fecNotificacionOficio").prop('disabled','true');
	$("form#seguimientoForm #fecAtencionOficio").prop('disabled','true');
	$("form#promocionCancelacionForm #refCancelacion").prop('disabled','true');
	$("form#promocionCancelacionForm #fecCancelacion").prop('disabled','true');
	$("form#promocionCancelacionForm #idMotivoCancelacion").prop('disabled','true');
	
	//Deshabilitar botones 
	$('form#seguimientoForm :input#btnGenInvita').prop("disabled", true);
	$('form#seguimientoForm :input#btnGuardarSeg').prop("disabled", true);
	$('form#promocionCancelacionForm :input#btnConfirmarCancel').prop("disabled", true);
	
	
	


	
}


