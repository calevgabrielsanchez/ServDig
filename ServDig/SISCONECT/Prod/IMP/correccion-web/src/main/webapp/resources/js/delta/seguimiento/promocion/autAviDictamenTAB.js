

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
 * Funcoin que valida que la fecha de autorizacion de aviso de dictamen
 * no sea menor a la fecha de atencion.
 * @param fecIni
 * 
 */
function jsValidaFecAutAviso(fecIni){
	
	if(jsValidaFecha(fecIni)){
		 $("#fechaAvisoDictamen").val(fecIni);
		 $("#labelFecAutDictamen").html('');		
		 if($("form#seguimientoTabForm #fecAtencionOficio").val()==''){
			 if(jsValidaVsfecNotificacion(fecIni)){
				 $("#fechaAvisoDictamen").val(fecIni);
				 $("#labelFecAutDictamen").html('');
				 $('#spnFecAut').show('fast');
			 }else{
				 $("#fechaAvisoDictamen").val("");
				 $("#labelFecAutDictamen").html('<label class="etiquetaError" >La fecha de autorizaci&oacute;n de aviso no puede ser menor a la fecha de notificaci&oacute;n</label>');
			 }
		 }else{
			 if(jsValidaVsfecAtencion(fecIni)){
				 $("#fechaAvisoDictamen").val(fecIni);
				 $("#labelFecAutDictamen").html('');
				 $('#spnFecAut').show('fast');
			 }else{
				 $("#fechaAvisoDictamen").val("");
				 $("#labelFecAutDictamen").html('<label class="etiquetaError" >La fecha de autorizaci&oacute;n de aviso no puede ser menor a la fecha atenci&oacute;n</label>');
			 }
		 }
		 
		 
		
	}else{
		 $("#fechaAvisoDictamen").val("");
		 $("#labelFecAutDictamen").html('<label class="etiquetaError" >La fecha de autorizaci&oacute;n de aviso no puede ser mayor al dia actual</label>');
	}
}

/**
 * Funcion que completa el flujo en cuando a comportamiento de los elemetos HTML
 * cuando finaliza exitosamente el flujo de autorizar dictamen
 */
function completaFlujoAutDictamen(){
	
	$("form#seguimientoTabForm #fecNotificacionOficio").prop('disabled','disabled');
	$("form#seguimientoTabForm #fecAtencionOficio").prop('disabled','disabled');
	$("form#autAviDictamenSegForm #fechaAvisoDictamen").prop('disabled','disabled');
	$("form#autAviDictamenSegForm #numAvisoDictamen").prop('disabled','disabled');
	$("form#autAviDictamenSegForm #fechaInicio").prop('disabled','disabled');
	$("form#autAviDictamenSegForm #fechaFin").prop('disabled','disabled');
	
	
	//Deshabilitar botones 
	$('form#seguimientoTabForm :input#btnGenInvita').prop("disabled", true);
	$('form#seguimientoTabForm :input#btnGuardarSeg').prop("disabled", true);
	$('form#autAviDictamenSegForm :input#btnGuardarAut').prop("disabled", true);
	$('#spnFecAtn').hide();
	$('#spnFecNot').hide();
	$('#spnFecAut').hide();
	$('#spnFecPer').hide();
	
	
	var fechaAvisoDictamen = $("form#autAviDictamenSegForm #fechaAvisoDictamen").val();
	var fechaDicIni = $("form#autAviDictamenSegForm #fechaInicio").val();
	var fechaDicFin = $("form#autAviDictamenSegForm #fechaFin").val();
	
	$("form#seguimientoTabForm #fecAutDict").val(fechaAvisoDictamen);
	$("form#seguimientoTabForm #fecSolCorrIni").val(fechaDicIni);
	$("form#seguimientoTabForm #fecSolCorrFin").val(fechaDicFin);
	
	//estilos
	 $("form#autAviDictamenSegForm #fechaAvisoDictamen").removeClass("red");
	 $("form#autAviDictamenSegForm #numAvisoDictamen").removeClass("red");
	 $("form#autAviDictamenSegForm #fechaInicio").removeClass("red");
	 $("form#autAviDictamenSegForm #fechaFin").removeClass("red");
	 $("form#seguimientoTabForm #fecNotificacionOficio").removeClass("red");
	 $("form#seguimientoTabForm #fecAtencionOficio").removeClass("red");
	 $('form#seguimientoTabForm #observaciones').removeClass("red");
	 
	 
	
	
}

/**
 * Funcion que valida la fecha que recibe como parametro contra la fecha de notificacion
 * @param fecIni , fecha a Evaluar
 * @returns {Boolean}
 */
function jsValidaVsfecNotificacion(fecIni){
	var fecNotificacion = $("form#seguimientoTabForm #fecNotificacionOficio").val();

	var resp = false;
	if(fecIni != '' && fecNotificacion != ''){
		if(jsValidaFechas(fecNotificacion,fecIni)){
			 resp = true;
		 }
	}
	
	return resp;
}

/**
 * Funcion que valida la fecha que recibe como parametro contra la fecha de atencion
 * @param fecIni , fecha a Evaluar
 * @returns {Boolean}
 */
function jsValidaVsfecAtencion(fecIni){
	var fecAtencionOficio = $("form#seguimientoTabForm #fecAtencionOficio").val();

	var resp = false;
	if(fecIni != '' && fecAtencionOficio != ''){
		if(jsValidaFechas(fecAtencionOficio,fecIni)){
			 resp = true;
		 }
	}
	
	return resp;
}

/**
 * Funcion que se ejecuta al guardar el TAB de autorizacion de dictamen en el seguimiento de la promocion
 */
function onClickBtnGuardarAut(){
	
	$("#labelFecAutDictamen").html('');
	$("#labelNumAviso").html('');
	$("#labelFecFin").html('');
	jsValidaFecAutAviso($("#fechaAvisoDictamen").val());
	if(jsValidaRequeridosAutAviDictamen()){
		//bloquear();
		
		var cvePromocion = $("form#seguimientoForm #cvePromocion").val();
		var bandera = $("form#autAviDictamenSegForm #bandera").val();
		
		var fecAvisoDictamen = $("form#autAviDictamenSegForm #fechaAvisoDictamen").val();
		var numAvisoDictamen = $("form#autAviDictamenSegForm #numAvisoDictamen").val();
		var fechaInicio = $("form#autAviDictamenSegForm #fechaInicio").val();
		var fechaFin = $("form#autAviDictamenSegForm #fechaFin").val();
		
		var fecNotificacionOficio = $("form#seguimientoTabForm #fecNotificacionOficio").val();
		var fecAtencionOficio = $("form#seguimientoTabForm #fecAtencionOficio").val();
		
		
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
		//alert("aut Dict");
		$.postJSON(jsContextoPromocion + "/consulta/actualizaPromocionEXO.do", objetoJson, function(data) {
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
		/*if($("form#autAviDictamenSegForm #fechaInicio").val() == ''){
			$("#labelFecInicio").html('<label class="etiquetaError">Campo requerido</label>');
		}*/
		if($("form#autAviDictamenSegForm #fechaFin").val() == '' || $("form#autAviDictamenSegForm #fechaInicio").val() == ''){
			$("#labelFecFin").html('<label class="etiquetaError">Campo requerido</label>');
		}
		
	}				


}

/**
 * Funcion que limpia las fechas del periodo, en caso de que no esten 
 * deshabilitadas
 */
function limpiaFechasPeriodo(){
	
	if(!$("form#autAviDictamenSegForm #fechaFin").prop("disabled")){
		$("form#autAviDictamenSegForm #fechaFin").val(""); 
		$("form#autAviDictamenSegForm #fechaInicio").val("");
		$('#spnFecPer').hide('fast');
	}
	
}

/**
 * Funcion que limpia la fecha de Autorizacion de dictamen en caso de que no este
 * deshabilitada
 */
function limpiaFechasAutDict(){
	if(!$("form#autAviDictamenSegForm #fechaAvisoDictamen").prop("disabled")){
		$("form#autAviDictamenSegForm #fechaAvisoDictamen").val(""); 
		$('#spnFecAut').hide('fast');
	}
	
	
}

function jsFlujoAut(){
	
	$('#spnFecPer').show('fast');
}