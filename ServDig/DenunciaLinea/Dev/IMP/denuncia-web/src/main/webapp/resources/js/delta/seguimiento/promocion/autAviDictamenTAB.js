$(document).ready(function(){
	 
		 $("#btnGuardarAut").click(
					function(e) {
						
						$("#labelFecAutDictamen").html('');
						$("#labelNumAviso").html('');
						$("#labelFecInicio").html('');
						$("#labelFecFin").html('');
						
						if(jsValidaRequeridosAutAviDictamen()){
							//bloquear();
							
							var cvePromocion = $("form#seguimientoForm #cvePromocion").val();
							var bandera = $("form#promocionCancelacionForm #bandera").val();
							
							var fecAvisoDictamen = $("form#autAviDictamenSegForm #fechaAvisoDictamen").val();
							var numAvisoDictamen = $("form#autAviDictamenSegForm #numAvisoDictamen").val();
							var fechaInicio = $("form#autAviDictamenSegForm #fechaInicio").val();
							var fechaFin = $("form#autAviDictamenSegForm #fechaFin").val();
							
							var fecNotificacionOficio = $("form#seguimientoForm #fecNotificacionOficio").val();
							var fecAtencionOficio = $("form#seguimientoForm #fecAtencionOficio").val();
							
							
							var variable = '{' +
							   '"cvePromocion":"'+cvePromocion+'",'+
							   '"bandera":"'+bandera+'",'+
							   '"fechaAvisoDictamen":"'+fecAvisoDictamen+'",'+
							   '"numAvisoDictamen":"'+numAvisoDictamen+'",'+
							   '"fechaInicio":"'+fechaInicio+'",'+
							   '"fechaFin":"'+fechaFin+'",'+
							   '"fechaNotificacion":"'+fecNotificacionOficio+'",'+
							   '"fechaAtencion":"'+fecAtencionOficio+'"}';
							var objetoJson = jQuery.parseJSON(variable);
							$.postJSON("consulta/actualizaPromocionEXO.do", objetoJson, function(data) {
								alert('El registro se actualizo correctamente');
								//oDgSeguimeinto.dialog("close");
							}).error(function(data){ 
								desbloquear();
								validarSesionExpirada(data);
							}).complete(function(){
								desbloquear();
							});		
							
							completaFlujoAutDictamen();
							
						}else{
							if($("form#autAviDictamenSegForm #fechaAvisoDictamen").val() == ''){
								$("#labelFecAutDictamen").html('<label class="etiquetaError">Campo requerido</label>');
							}
							if($("form#autAviDictamenSegForm #numAvisoDictamen").val() == ''){
								$("#labelNumAviso").html('<label class="etiquetaError">Campo requerido</label>');
							}
							if($("form#autAviDictamenSegForm #fechaInicio").val() == ''){
								$("#labelFecInicio").html('<label class="etiquetaError">Campo requerido</label>');
							}
							if($("form#autAviDictamenSegForm #fechaFin").val() == ''){
								$("#labelFecFin").html('<label class="etiquetaError">Campo requerido</label>');
							}
							
						}				
					
					});// fin btnGuardarAut	 

	
});

	/**
	 * Valida los datos requeridos para el tab de cancelacion
	 * @returns
	 */
function jsValidaRequeridosAutAviDictamen(){

	var fecAvisoDictamen = $("form#autAviDictamenSegForm #fechaAvisoDictamen").val();
	var numAvisoDictamen = $("form#autAviDictamenSegForm #numAvisoDictamen").val();
	var fechaInicio = $("form#autAviDictamenSegForm #fechaInicio").val();
	var fechaFin = $("form#autAviDictamenSegForm #fechaFin").val();
	
	var resultado = false;
	
	if((fecAvisoDictamen!=null & fecAvisoDictamen != '') && (numAvisoDictamen != null & numAvisoDictamen!= '') 
			&& (fechaInicio!=null && fechaInicio!= '') && (fechaFin!=null && fechaFin!= '')){
		return true;
	}
	
	return resultado;
}


/**
 * Metodo que valida que la fecha de autorizacion de aviso de dictamen
 * no sea menor a la fecha de atencion.
 * @param fecIni
 * 
 */
function jsValidaFecAutAviso(fecIni){
	
	if(jsValidaFecha(fecIni)){
		 $("#fechaAvisoDictamen").val(fecIni);
		 $("#labelFecAutDictamen").html('');
		 if(jsValidaVsfecAtencion(fecIni)){
			 $("#fechaAvisoDictamen").val(fecIni);
			 $("#labelFecAutDictamen").html('');
		 }else{
			 $("#fechaAvisoDictamen").val("");
			 $("#labelFecAutDictamen").html('<label class="etiquetaError" >La fecha de autorizaci&oacute;n de aviso no puede ser menor a la fecha atenci&oacute;n</label>');
		 }
	}else{
		 $("#fechaAvisoDictamen").val("");
		 $("#labelFecAutDictamen").html('<label class="etiquetaError" >La fecha de autorizaci&oacute;n de aviso no puede ser mayor al dia actual</label>');
	}
}

function completaFlujoAutDictamen(){
	
	$("form#seguimientoForm #fecNotificacionOficio").prop('disabled','true');
	$("form#seguimientoForm #fecAtencionOficio").prop('disabled','true');
	$("form#autAviDictamenSegForm #fechaAvisoDictamen").prop('disabled','true');
	$("form#autAviDictamenSegForm #numAvisoDictamen").prop('disabled','true');
	$("form#autAviDictamenSegForm #fechaInicio").prop('disabled','true');
	$("form#autAviDictamenSegForm #fechaFin").prop('disabled','true');
	
	
	//Deshabilitar botones 
	$('form#seguimientoForm :input#btnGenInvita').prop("disabled", true);
	$('form#seguimientoForm :input#btnGuardarSeg').prop("disabled", true);
	$('form#autAviDictamenSegForm :input#btnGuardarAut').prop("disabled", true);
	
	
	var fechaAvisoDictamen = $("form#autAviDictamenSegForm #fechaAvisoDictamen").val();
	
	$("form#seguimientoForm #fecAutDict").val(fechaAvisoDictamen);
	
	
}


/**
 * Metodo que valida la fecha que recibe como parametro contra la fecha de atencion
 * @param fecIni , fecha a Evaluar
 * @returns {Boolean}
 */
function jsValidaVsfecAtencion(fecIni){
	var fecAtencionOficio = $("form#seguimientoForm #fecAtencionOficio").val();

	var resp = false;
	if(fecIni != '' && fecAtencionOficio != ''){
		if(jsValidaFechas(fecAtencionOficio,fecIni)){
			 resp = true;
		 }
	}
	
	return resp;
}


