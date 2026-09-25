
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
	var funcionarioAutoriza= $('form#promocionCancelacionForm #funcionarioAutorizaOrd').val();
	var resultado = false;
	if(refCancelacion != '' && fecCancelacion != '' && idMotivoCancelacion != '-1' && funcionarioAutoriza!='-1' && funcionarioAutoriza!='' ){
	
		return true;
	}

	return false;
}

/**
 * Funcion que completa el flujo de cancelacion en cuanto al comportamiento de los componentes HTML
 */
function completaFlujoCancelacion(){
	var fechaCancelacion = $("form#promocionCancelacionForm #fecCancelacion").val();
	
	$("form#seguimientoTabForm #fecCancelaOficio").val(fechaCancelacion);
	
	$("form#seguimientoTabForm #fecCancelaOficio").prop('disabled','true');
	$("form#seguimientoTabForm #fecNotificacionOficio").prop('disabled','true');
	$("form#seguimientoTabForm #fecAtencionOficio").prop('disabled','true');
	$("form#promocionCancelacionForm #refCancelacion").prop('disabled','true');
	$("form#promocionCancelacionForm #fecCancelacion").prop('disabled','true');
	$("form#promocionCancelacionForm #idMotivoCancelacion").prop('disabled','true');
	
	//Deshabilitar botones 
	$('form#seguimientoTabForm :input#btnGenInvita').prop("disabled", 'disabled');
	$('form#seguimientoTabForm :input#btnGuardarSeg').prop("disabled", 'disabled');
	$('form#promocionCancelacionForm :input#btnConfirmarCancel').prop("disabled", 'false');
	$('form#seguimientoTabForm #observaciones').prop("disabled", 'false');
	
	
	//estilos
	 $('form#promocionCancelacionForm :input#refCancelacion').removeClass("red");
	 $('form#promocionCancelacionForm :input#fecCancelacion').removeClass("red");
	 $('form#promocionCancelacionForm :input#idMotivoCancelacion').removeClass("red");
	 $("form#seguimientoTabForm #fecNotificacionOficio").removeClass("red");
	 $("form#seguimientoTabForm #fecAtencionOficio").removeClass("red");
	 $('form#seguimientoTabForm #observaciones').removeClass("red");
	 $('#spnFecCan').hide();
	 $('#spnFecNot').hide();

}

/**
 * Funcion del boton principal del tab de cancelacion, confirmacion de la cancelacion
 */
function onClickBtnConfirmarCancel(){
	
	$("#labelRefCancelacion").html('');
	$("#labelFecCancelacion").html('');
	$("#labelMotivoCancelacion").html('');
	$("#labelFuncionarioAutorizaOrdinario").html('');
	
	if(jsValidaRequeridosCancelacion()){
		bloquear();
		var cvePromocion = $("form#seguimientoForm #cvePromocion").val();
		var bandera = $("form#promocionCancelacionForm #bandera").val();
		var refCancelacion = $("form#promocionCancelacionForm #refCancelacion").val();
		var fecCancelacion = $("form#promocionCancelacionForm #fecCancelacion").val();
		var idMotivoCancelacion = $('form#promocionCancelacionForm #idMotivoCancelacion').val();
		var funcionarioAutoriza= $('form#promocionCancelacionForm #funcionarioAutorizaOrd').val();
		//tab seguimiento
		var fecNotificacionOficio = $("form#seguimientoTabForm #fecNotificacionOficio").val();
		//cveFuncionarioAutoriza
		var variable = '{' +
		   '"cvePromocion":"'+cvePromocion+'",'+
		   '"bandera":"'+bandera+'",'+
		   '"nuVolanteCancela":"'+refCancelacion+'",'+
		   '"fechaCancelacion":"'+fecCancelacion+'",'+
		   '"fechaNotificacion":"'+fecNotificacionOficio+'",'+
		   '"cveFuncionarioAutoriza":"'+funcionarioAutoriza+'",'+
		   '"idMotivoCancelacion":"'+idMotivoCancelacion+'"}';
		
		var objetoJson = jQuery.parseJSON(variable);
		
		$.postJSON(jsContextoPromocion + "/consulta/actualizaPromocionEXO.do", objetoJson, function(data) {
			alert('El registro se actualizo correctamente');
			$("#funcionarioAutorizaOrd").prop('disabled','disabled');
			//oDgSeguimeinto.dialog("close");
		}).error(function(data){ 
			desbloquear();
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();
			aplicaReglasSaticB();
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
		if($('form#promocionCancelacionForm #funcionarioAutorizaOrd').val() == '-1'){
			$("#labelFuncionarioAutorizaOrdinario").html('<label class="etiquetaError">Campo requerido</label>');
		}
		
		
		
	}				
}


/**
 * Funcion que valida que la fecha de cancelacion no sea menor a la fecha
 * del oficio de la promocion
 * @param fecIni
 * 
 */
function jsValidaFecCancelacion(fecIni){
	if($("form#seguimientoTabForm #fecNotificacionOficio").val()==''){
		//alert("NO existe fecha notif- validacion actual");
		
		if(jsValidaFecha(fecIni)){
			 $("#fecCancelacion").val(fecIni);
			 $("#labelFecCancelacion").html('');
			 if(jsValidaVsfecOficio(fecIni)){
				 $("#fecCancelacion").val(fecIni);
				 $("#labelFecCancelacion").html('');
				 $('#spnFecCan').show('fast');
			 }else{
				 $("#fecCancelacion").val("");
				 $("#labelFecCancelacion").html('<label class="etiquetaError" >La fecha de cancelaci&oacute;n no puede ser menor a la fecha oficio de promoci&oacute;n</label>');
			 }
		}else{
			 $("#fecCancelacion").val("");
			 $("#labelFecCancelacion").html('<label class="etiquetaError" >La fecha de cancelaci&oacute;n no puede ser mayor al dia actual</label>');
		}
		
	}else{
		
		//alert("SI existe fecha notif- validacion x fecha Notificacion");
		
		if(jsValidaFecha(fecIni)){
			 $("#fecCancelacion").val(fecIni);
			 $("#labelFecCancelacion").html('');
			 if(jsValidaVsfecNotificacionOrd(fecIni)){
				 $("#fecCancelacion").val(fecIni);
				 $("#labelFecCancelacion").html('');
				 $('#spnFecCan').show('fast');
			 }else{
				 $("#fecCancelacion").val("");
				 $("#labelFecCancelacion").html('<label class="etiquetaError" >La fecha de cancelaci&oacute;n no puede ser menor a la fecha de Notificaci&oacute;n</label>');
			 }
		}//else{
			// $("#fecCancelacion").val("");
			// $("#labelFecCancelacion").html('<label class="etiquetaError" >La fecha de cancelaci&oacute;n no puede ser mayor al dia actual</label>');
		//}
		
	}
	
	
}

/**
 * Funcion que limpia la fecha de cancelacion  en caso de que no el Text 
 * no se encuentre deshabilitado
 */
function limpiaFechaCancelacion(){
	if(!$("#fecCancelacion").prop("disabled")){
		$("form#promocionCancelacionForm #fecCancelacion").val("");
		$('#spnFecCan').hide('fast');
	}
}

/**
 * Funcion que valida la fecha que recibe como parametro contra la fecha de
 * notificacion
 * @param fecIni , fecha a Evaluar
 * @returns {Boolean}
 */
function jsValidaVsfecNotificacionOrd(fecIni){
	var fecNotificacionOrd = $("form#seguimientoTabForm #fecNotificacionOficio").val();
	var resp = false;
	if(fecIni != '' && fecNotificacionOrd != ''){
		if(jsValidaFechas(fecNotificacionOrd,fecIni)){
			 resp = true;
		 }
	}
	return resp;
}

function cargaFuncionarioAutorizaOrdinario(){
	$.postJSON(jsContextoPromocion+"seguimiento/generico/consultaFuncionarioAutoriza.do", null, function(data) {
		
		var comboFuncAutCanGen=document.getElementById("funcionarioAutorizaOrd");
		if (comboFuncAutCanGen != null) {
			comboFuncAutCanGen.options.length = 1;
			// alert ("data.length="+data.length);
			
			for(var i = 0 ; i < data.length ; i++){
				comboFuncAutCanGen.add(new Option(data[i][1], data[i][0]));
			}
		}
	
	});
}
