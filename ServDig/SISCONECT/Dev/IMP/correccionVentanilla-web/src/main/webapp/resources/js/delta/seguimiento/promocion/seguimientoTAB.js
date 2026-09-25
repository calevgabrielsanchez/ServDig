
/**
 * funcion que valida los datos requeridos para el tab de seguimiento
 * @returns {Boolean}
 */
function jsValidaRequeridosSeguimiento(){
	
	var fecNotif  = $("#fecNotificacionOficio").val();
	var fecAtenOf = $("#fecAtencionOficio").val();
	var result = false;
	
	//if(fecNotif != '' && fecAtenOf != ''){
	if(fecNotif != ''){
		return true;
	}
	
	return result;
	
}

/**
 * funcion que complementa la logica de los tabs de seguimiento de promocion
 */
function completarFlujoSeguimiento(){
	

	//$("form#promocionCancelacionForm #refCancelacion").prop('disabled','disabled');
	//$("form#promocionCancelacionForm #fecCancelacion").prop('disabled','disabled');
	//$("form#promocionCancelacionForm #idMotivoCancelacion").prop('disabled','disabled');
	
	//Deshabilitar botones 
	$('form#seguimientoTabForm :input#btnGenInvita').prop("disabled", true);
	
	
}


/**
 * Funcion del boton de guardar del tab de seguimiento 
 */
function onClickBtnGuardarSeg(){

	
	$("#labelFecNotif").html('');
	$("#labelFecAtencionOficio").html('');
	if(jsValidaRequeridosSeguimiento()){
		bloquear();
		var cvePromocion = $("form#seguimientoForm #cvePromocion").val();
		var fecNotificacionOficio = $("form#seguimientoTabForm #fecNotificacionOficio").val();
		var fecAtencionOficio = $("form#seguimientoTabForm #fecAtencionOficio").val();
		var observaciones = $("form#seguimientoTabForm #observaciones").val();
		if(observaciones.length > 199){
			observaciones = observaciones.substring(0,199);
		}
		
		var variable = '{' +
		   '"cvePromocion":"'+cvePromocion+'",'+
		   '"fechaNotificacion":"'+fecNotificacionOficio+'",'+
		   '"fechaAtencion":"'+fecAtencionOficio+'",'+
		   '"txObservaciones":"'+observaciones+'"}';
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON(jsContextoPromocion + "/consulta/actualizaPromocionEXO.do", variableJson, function(data) {
			alert('El registro se actualizo correctamente');
			$("#fecNotificacionOficio").attr("disabled","disabled");
			$('form#seguimientoTabForm :input#btnGenInvita').prop("disabled", false);
			
			if($("#fecAtencionOficio").val()!="" && $("#fecAtencionOficio").val()!=null){
				$("#fecAtencionOficio").attr("disabled","disabled");
			}
			
			
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
		/*if($("#fecAtencionOficio").val() == ''){
			$("#labelFecAtencionOficio").html('<label class="etiquetaError">Campo requerido</label>');
		}*/
	}

	

	
}


/**
 * Funcion que valida que la fecha de Notificacion no sea menor que la fehca de oficio
 * @param fecIni
 */



function jsValidaFecNotif(fecIni){
	$("#labelFecAtencionOficio").html('');
	//alert("fechaini: "+ fecIni );
	if(jsValidaFecha(fecIni)){
		 $("#fecNotificacionOficio").val(fecIni);
		 $("#labelFecNotif").html('');
		 if(jsValidaVsfecOficio(fecIni)){
			 $("#fecNotificacionOficio").val(fecIni);
			 $("#fecAtencionOficio").val('');
			 $("#labelFecNotif").html('');
		 }else{
			 $("#fecNotificacionOficio").val("");
			 $("#labelFecNotif").html('<label class="etiquetaError" >La fecha de notificaci&oacute;n del oficio no puede ser menor a la fecha oficio de promoci&oacute;n</label>');
		 }
	}else{
		 $("#fecNotificacionOficio").val("");
		 $("#labelFecNotif").html('<label class="etiquetaError" >La fecha de notificaci&oacute;n del oficio no puede ser mayor al dia actual</label>');
	}
}
	
/**
 * Funcion del boton (X)  de limpiar fecha de notificacion, 
 * al borrar la fecha regresa al estado anterior de los tabs
 */
function limpiaFechaNotificacion(){
		
		if(!$("#fecNotificacionOficio").prop("disabled")){
		
			$("#fecNotificacionOficio").val("");
			$("#fecAtencionOficio").val("");
			
			var rolUsuario =  $("form#seguimientoForm #rolUsuarioOrdHdn").val();
			
			$("#tab_container_href").tabs( "enable", 0);//tab seguimiento
			$('#seguimientoTAB').show('slow');
			
			if(rolUsuario == JEFE_OF_CORRECCION || rolUsuario == JEFE_OF_CORR_Y_DIC || 
					rolUsuario == JEFE_DEP_AUD_PAT){
				
				$("#tab_container_href").tabs( "enable", 1);
				$("#tab_container_href").tabs( "disable" , 2 );
				$('#cancelacionTAB').show('slow');
				$('#autDictamenTAB').hide('slow');
			}else{
				$("#tab_container_href").tabs( "disable", 1);
				$("#tab_container_href").tabs( "disable" , 2 );
				$('#cancelacionTAB').hide('slow');
				$('#autDictamenTAB').hide('slow');
			}
			
			
			$("form#seguimientoTabForm #fecAtencionOficio").prop('disabled','disabled');
			$("form#seguimientoTabForm #fecAtencionOficio").removeClass("red");
			$("form#autAviDictamenSegForm #fechaAvisoDictamen").val("");
			$("form#autAviDictamenSegForm #numAvisoDictamen").val("");
			$("form#autAviDictamenSegForm #fechaInicio").val("");
			$("form#autAviDictamenSegForm #fechaFin").val("");
			
			$('#spnFecAtn').hide();
			$('#spnFecNot').hide();
			$('#spnFecPer').hide();
			$('#spnFecAut').hide();
			$('#spnFecCan').hide(); 
		}
	}
	
/**
 * Funcion del boton (X)  de limpiar fecha de Atencion, 
 * al borrar la fecha regresa al estado anterior de los tabs
 */
function limpiaFechaAtencion(){
		
		if(!$("#fecAtencionOficio").prop("disabled")){

			$("#fecAtencionOficio").val("");
			
			var rolUsuario =  $("form#seguimientoForm #rolUsuarioOrdHdn").val();
			$("#tab_container_href").tabs( "enable", 0);//tab seguimiento
			$('#seguimientoTAB').show('slow');
			if(rolUsuario == JEFE_OF_CORRECCION || rolUsuario == JEFE_OF_CORR_Y_DIC || 
					rolUsuario == JEFE_DEP_AUD_PAT){
				
				$("#tab_container_href").tabs( "enable", 1);
			//	$("#tab_container_href").tabs( "disable" , 2 ) como la fecha de atencion no es requerida se permite mantener habilitada
				
				$('#cancelacionTAB').show('slow');
			//	$('#autDictamenTAB').hide('slow');
			}else{
				$("#tab_container_href").tabs( "disable", 1);
				//$("#tab_container_href").tabs( "disable" , 2 )
				
				$('#cancelacionTAB').hide('slow');
			//	$('#autDictamenTAB').hide('slow');
			}
			
			$("form#promocionCancelacionForm #refCancelacion").prop("value", "");
			 $("form#promocionCancelacionForm #fecCancelacion").val("");
			 $("form#promocionCancelacionForm #idMotivoCancelacion").val("-1");
			 $("form#autAviDictamenSegForm #fechaAvisoDictamen").val("");
			 $("form#autAviDictamenSegForm #numAvisoDictamen").val("");
			 $("form#autAviDictamenSegForm #fechaInicio").val("");
			 $("form#autAviDictamenSegForm #fechaFin").val("");
			 
			 $("form#promocionCancelacionForm #refCancelacion").addClass("red");
			 $("form#promocionCancelacionForm #fecCancelacion").addClass("red");
			 $("form#promocionCancelacionForm #idMotivoCancelacion").addClass("red");
				 
			 $("#labelFecAtencionOficio").html('');
			 $('form#seguimientoTabForm :input#btnGenInvita').removeAttr('disabled');
			 $('#spnFecAtn').hide();
			 $('#spnFecPer').hide();
			 $('#spnFecAut').hide();
			 $('#spnFecCan').hide();
				
		}
		 
	}
	
	
	/**
	 * Funcion que establece el comportamiento de la pantalla y de los tabs en el segumiento de promocion
	 * @param elementoActual
	 * @param elementoSiguiente
	 */
	
	function complementaPantalla(elementoActual,elementoSiguiente){
	
		var rolUsuario =  $("form#seguimientoForm #rolUsuarioOrdHdn").val();
		
		if(elementoActual=='fecNotificacionOficio'){
			
			var valFechaNotOfi = $('form#seguimientoTabForm input#fecNotificacionOficio').val();
			if(valFechaNotOfi != null && valFechaNotOfi != ''){
				activa(elementoSiguiente);
				$("form#invitacionAntecedenteForm #fechaNotificacionInv").val(valFechaNotOfi);
				//alert("valFechaNotOfi: "+ valFechaNotOfi);
				//alert("fec inivitacion: "+ $("form#invitacionAntecedenteForm #fechaNotificacionInv").val());
			
				//No se habilita hasta que se haya guardado la promocion
				//$('form#seguimientoTabForm :input#btnGenInvita').prop("disabled", false);
				$('#spnFecNot').show("fast");
				 
			
			}	 
			
		}else if(elementoActual=='fecAtencionOficio'){
			var valFecAtnOficio = $('form#seguimientoTabForm input#fecAtencionOficio').val();
			
			if(valFecAtnOficio!=null && valFecAtnOficio != ''){
				
				if(rolUsuario == JEFE_OF_CORRECCION || rolUsuario == JEFE_OF_CORR_Y_DIC || 
						rolUsuario == JEFE_DEP_AUD_PAT){
					
					$("#tab_container_href").tabs("disable", 1);
					$("#tab_container_href").tabs( "enable", 2);
					$('#autDictamenTAB').show('slow'); 
					$('#cancelacionTAB').hide('slow');
					
				}else{
					$("#tab_container_href").tabs("disable", 1);
					$("#tab_container_href").tabs( "disable", 2);
					$('#autDictamenTAB').hide('slow'); 
					$('#cancelacionTAB').hide('slow');
				}
					
				
				$('form#seguimientoTabForm :input#btnGenInvita').prop("disabled", true);
				 
				$("form#autAviDictamenSegForm #fechaAvisoDictamen").addClass("red");
				$("form#autAviDictamenSegForm #numAvisoDictamen").addClass("red");
				$("form#autAviDictamenSegForm #fechaInicio").addClass("red");
				$("form#autAviDictamenSegForm #fechaFin").addClass("red");
				$('#spnFecAtn').show("fast");
				
			}
		}
	}
	
	/**
	 * Funcion que valida que la fecha de atencion no sea menor que la fecha de Notificacion de oficio
	 * @param fecIni
	 */
	function jsValidaFechaAtencionOficio(fecIni){
		
		if(jsValidaFecha(fecIni)){
			 $("#fecAtencionOficio").val(fecIni);
			 $("#labelFecAtencionOficio").html('');
			 if(jsValidaVsfecNotif(fecIni)){
				 $("#fecAtencionOficio").val(fecIni);
				 $("#labelFecAtencionOficio").html('');
				 $('#spnFecCan').hide();
			 }else{
				 $("#fecAtencionOficio").val("");
				 $("#labelFecAtencionOficio").html('<label class="etiquetaError" >La fecha de atenci&oacute;n del oficio no puede ser menor a la fecha de notificaci&oacute;n del oficio</label>');
			 }
		}else{
			 $("#fecAtencionOficio").val("");
			 $("#labelFecAtencionOficio").html('<label class="etiquetaError" >La fecha de atenci&oacute;n del oficio no puede ser mayor al dia actual</label>');
		}
	}
	
	/**
	 * Funcion que valida que la fecha de notificacion no sea menor que la fecha
	 * que recibe
	 * @param fecIni
	 * @returns {Boolean}
	 */
	function jsValidaVsfecNotif(fecIni){
		var fecNotificacion = $("#fecNotificacionOficio").val();
		var resp = false;
		
		if(fecIni != '' && fecNotificacion != ''){
			if(jsValidaFechas(fecNotificacion,fecIni)){
				 resp = true;
			 }
		}
		
		return resp;
	}
	
//inicia seccion de modal de invitacions	
	//opcion2
	function muestraInvitacion(){
		//var id = $("form#seguimientoForm #cvePromocion").val();
		//jsMuestraInvOrd(id);
		var registroPatronalOrd = $("form#seguimientoForm #regPatronalOrdinarioHdn").val();
		//alert("registroPatronalOrd: " + registroPatronalOrd);
		validarRegPatronalOrdinario(registroPatronalOrd)
	
	}
	
	function jsMuestraInvOrd(obj){
		var id = obj;
		bloquear();
	
		var sPromocion = '{' +
		   '"cveTemp":"'+id+'",'+
		   '"tipoPrograma":"promocion"}';	
		var promocion = jQuery.parseJSON(sPromocion);
	
		var url = getAppContextParaJS()+"/catalogo/invitacion/invitacionAntecedente.do";
		$.postJSON(url,promocion,function(data) {
			jsLimpiarGuardar();
			$("form#invitacionAntecedenteForm #fechaIncialinv,form#invitacionAntecedenteForm #fechaFinalInv").datepicker('option', 'beforeShowDay',null );
			
			$("form#invitacionAntecedenteForm #labelFolioAntecedente").html('<label>' + data.folioAntecedente + '</label>');
			$("form#invitacionAntecedenteForm #labelRegPatronal").html('<label>' + data.regPatronal + '</label>');
			$("form#invitacionAntecedenteForm #labelNomRazonSocial").html('<label>' + data.razonSocial + '</label>');
			$("form#invitacionAntecedenteForm #cveDeteccion").val(data.cveDeteccion);
			$("form#invitacionAntecedenteForm #cvePromocion").val(data.cvePromocion);
			$("form#invitacionAntecedenteForm #tipoPrograma").val(data.tipoPrograma);
			$("form#invitacionAntecedenteForm #fechaIncialinv").val(data.fechaIncial);
			$("form#invitacionAntecedenteForm #fechaFinalInv").val(data.fechaFinal);
			oDgInvitacionAntecedente.dialog('open');
			
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();
		});			
	}
	
	
function jsLimpiarGuardar(){
		
		$("form#invitacionAntecedenteForm #cveDeteccion").val();
		$("form#invitacionAntecedenteForm #cvePromocion").val();
		$("form#invitacionAntecedenteForm #nuOficioinv").val();
		$("form#invitacionAntecedenteForm #fechaEmisionFec").val();
		$("form#invitacionAntecedenteForm #fechaIncialinv").val();
		$("form#invitacionAntecedenteForm #fechaFinalInv").val();
		
	}
	
function jsValidaInvitacion(){
	$("form#invitacionAntecedenteForm #labelFecEmision").html('');
	$("form#invitacionAntecedenteForm #labelOficio").html('');
	
	var regresa = false;
	if($("form#invitacionAntecedenteForm #nuOficioinv").val() == ''){
		$("form#invitacionAntecedenteForm #labelOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#invitacionAntecedenteForm #fechaEmisionFec").val() == ''){
		$("form#invitacionAntecedenteForm #labelFecEmision").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#invitacionAntecedenteForm #nuOficioinv").val() != '' && $("form#invitacionAntecedenteForm #fechaEmisionFec").val() !=''){
		regresa = true;
	}
	
	return regresa;
}

function jsLimpiaFormaInvitacion(){
	$("form#invitacionAntecedenteForm #nuOficioinv").val('');
	$("form#invitacionAntecedenteForm #fechaEmisionFec").val('');
	$("form#invitacionAntecedenteForm #fechaIncialinv").val('');
	$("form#invitacionAntecedenteForm #fechaFinalInv").val('');
}

function validarRegPatronalOrdinario(registroPatronal) {
	var respuesta = false;
	registroPatronal = registroPatronal.substring(0,10);
	if(registroPatronal != '' && registroPatronal.length == 10){							 
		 bloquear();
			$.postJSON(jsContextoPromocion+"seguimiento/generico/validaPatron.do",registroPatronal,function(data){ 
				
				if(data == null){

					alert("El registro patronal no es v&aacute;lido");
					respuesta = false;
				}
				else if(data != null && data.cveRespuestaWS <= JSERROR_WS){
						alert(data.descRespuestaWS );
						respuesta = false;
					 } else if(data.razonSocial != null){
						 nombre = data.razonSocial;
						 respuesta=true;
						 //seccion de invitacion 
						 var id = $("form#seguimientoForm #cvePromocion").val();
						 jsMuestraInvOrd(id);
						 
					 }					

			}).error(function(data){ 
				alert('Ocurri\u00F3 un error al consultar al patr\u00F3n, intentelo nuevamente por favor');
				validarSesionExpirada(data);
				//alert("respuesta1:: " + respuesta);
				return respuesta;
			}).complete(function(){
				//alert("respuesta2= " + respuesta);
				desbloquear();	
				return respuesta;
			});
	 }
	
	
	
}
	