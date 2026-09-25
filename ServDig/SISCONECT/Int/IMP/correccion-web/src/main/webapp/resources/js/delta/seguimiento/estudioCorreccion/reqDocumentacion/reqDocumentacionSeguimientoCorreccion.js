
/**
 
 * @since 07/08/2012
 * Funcion que inicializa la pestaña de Requerimiento Documentacion
 */
function inicializaReqDocumentacionSegCorreccion(data){
	
	inicializaFechasReqDocumentacionSegCorreccion();
	
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 07/08/2012
 * Funcion inicializa el datepicker de la pantalla de Requerimiento Documentacion del seguimiento de correccion.
 */
function inicializaFechasReqDocumentacionSegCorreccion(data){

	
	var fechaPresentacion = $('form#reqDocumentacionSeguimientoCorreccionForm #fechaElaboraPresenHdn').val();
	
	jsLimpiaFormaRD();
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").datepicker( { 
		dateFormat: 'dd-mm-yy',
		beforeShowDay: inhabiles,
		onSelect: function(dateText, inst) { 
			jsValidaFecAtencionRD();
	    }
	});
	
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").datepicker( { 
		dateFormat: 'dd-mm-yy',
		beforeShowDay: inhabiles,
		onSelect: function(dateText, inst) { 
			jsValidaFecNotificacionRD();
	    }
	});
	
	$("form# #fecEmisionReqDocSegCorr").datepicker( { 
		dateFormat: 'dd-mm-yy',
		beforeShowDay: inhabiles,
		onSelect: function(dateText, inst) { 
			$("form#reqDocumentacionSeguimientoCorreccionForm #labelFecEmi").html('');
			$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").val('');
			$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").val('');			
			jsValidaFecEmisionRD();
	    }
	});
	
	/*if(fechaPresentacion=!null && fechaPresentacion != undefined && fechaPresentacion != ''){
		
	}else{
		
	}*/
	if(fechaPresentacion != null){
		$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").datepicker('option', 'minDate', fechaPresentacion);
	}else{
	
		$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").datepicker('option', 'minDate', jsFechaMaxSeguimiento);
	}
	$( "form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr," +
			"form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr," +
			  "form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$( "form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr," +
			  "form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").datepicker('option', 'minDate', jsFechaMinSeguimiento);
	
	
	
	 var cveSolCorr = $("form#reqDocumentacionSeguimientoCorreccionForm #cveSolCorrReqDocSegCorr").val();
	 var variable = '{"cveSolCorr":' + cveSolCorr + '}';		
	 var variableJson = jQuery.parseJSON(variable);
	 $.postJSON_Sync("correccion/buscaReqDocumentacion.do", variableJson, function(data) {	
		   if(data != null){
			   generaResumenReqDocumenta(data);
			   jsDesBloqueaFormaRD();
			   jsBloqueoInicialRD();
			   if(data.cvePresentaCorr != null){
				   $("form#reqDocumentacionSeguimientoCorreccionForm #cvePresentaCorrReqDocSegCorr").val(data.cvePresentaCorr);
			   }
			   if(data.numFolioOficio != null){
				   $("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").val(data.numFolioOficio);
			   }
			   if(data.fechaEmision != null){
				   $("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").val(data.fechaEmision);
				   jsValidaActivaFecNot();
				// Bloquea campos
				    $("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").prop('disabled',true);
					$("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").removeClass("red");
					$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").prop('disabled',true);
					$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").removeClass("red");
					$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecEmisionReqDocSegCorr").hide();
					// DesBloquea Campos
					$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',false);
					$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").addClass("red");
					$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecNotifReqDocSegCorr").show();
			   }
			   if(data.fechaNotificacion != null){
				   $("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").val(data.fechaNotificacion);
				   jsValidaActivaFecAte();
					$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
					$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
					$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecNotifReqDocSegCorr").hide();
			   }
			   if(data.txObservaciones != null){
				   $("form#reqDocumentacionSeguimientoCorreccionForm #txObservacionesReqDocSegCorr").val(data.txObservaciones);
			   }
			   if(data.fechaAtencion != null){
				   $("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").val(data.fechaAtencion);
				   jsBloqueaFormaRD();
			   }
		   }else{
			   jsDesBloqueaFormaRD();
			   jsBloqueoInicialRD();
		   }
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){			
			desbloquear();
		});	
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 08/08/2012
 * Funcion que valida si ya fueron capturados los campos de Folio y Fecha Emision para deshabilitar la fecha de Notificacion.
 */
function jsValidaActivaFecNot(){
	$("form#reqDocumentacionSeguimientoCorreccionForm #labelNumFolio").html('');
	var folio  = $("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").val();
	var fecEmi = $("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").val();
	if(folio != '' && fecEmi != ''){
		// Habilita la fecha Notificacion
		$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',false);
		$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").addClass("red");
	}
}

/**
 * @author Enrique Duran Jimenez
 * @since 08/08/2012
 * Funcion que valida si ya fue capturada la fecha de Notificacion y habilita la fecha de atencion
 */
function jsValidaActivaFecAte(){
	var fecNot  = $("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").val();
	if(fecNot != ''){
		// Habilita la fecha Atencion
		$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecAtenReqDocSegCorr").show();
		$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").prop('disabled',false);
		$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").addClass("red");
	}
}

/**
 * @author Enrique Duran Jimenez
 * @since 08/08/2012
 * Funcion que valida que la fecha de notificacion no sea mayor a la fecha de Emision del oficio.
 */
function jsValidaFecNotificacionRD(){
	
	 $("form#reqDocumentacionSeguimientoCorreccionForm #labelFecNotif").html('');
	 var fecIni = $("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").val();
	 var fecFin = $("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").val();
	 
	 if(fecIni != '' && fecFin != ''){
		 if(comparaFechas(fecIni, fecFin,"-")){
			 $("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").val(fecFin);
			 $("form#reqDocumentacionSeguimientoCorreccionForm #labelFecNotif").html('');
			 jsValidaActivaFecAte();
		 }else{
			 jsLimpiafecNotifReqDocSegCorr();
			 $("form#reqDocumentacionSeguimientoCorreccionForm #labelFecNotif").html('<label class="etiquetaError">La Fecha de Notificaci&oacute;n no puede ser menor a la Fecha Emisi&oacute;n del Oficio</label>');
		 }
	 }
	 $("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").val("");
}

/**
 * @author Enrique Duran Jimenez
 * @since 08/08/2012
 * Funcion que valida que la fecha de Atencion no sea mayor a la fecha de Notificacion del oficio.
 */
function jsValidaFecAtencionRD(){
	
	 $("form#reqDocumentacionSeguimientoCorreccionForm #labelFecAte").html('');
	 var fecIni = $("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").val();
	 var fecFin = $("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").val();
	 if(fecIni != '' && fecFin != ''){
		 if(comparaFechas(fecIni, fecFin,"-")){
			 $("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").val(fecFin);
			 $("form#reqDocumentacionSeguimientoCorreccionForm #labelFecAte").html('');
		 }else{
			 $("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").val('');
			 $("form#reqDocumentacionSeguimientoCorreccionForm #labelFecAte").html('<label class="etiquetaError">La Fecha Atenci&oacute;n no puede ser menor a la Fecha de Notificaci&oacute;n</label>');
		 }
	 }
}

/**
 * @author Enrique Duran Jimenez
 * @since 08/08/2012
 * Funcion manda la informacion de la forma al controlador.
 */
function jsGuardaReqDoc(){
	if(jsValidaRequeridosRD() == true){
		var model = $("#reqDocumentacionSeguimientoCorreccionForm").serializeObject(true);
		   bloquear();
		   $.postJSON("correccion/guardarReqDocumentacion.do", model, function(data) {	
			   if(data != null){
				   $("form#reqDocumentacionSeguimientoCorreccionForm #cveRevOficiosReqDocSegCorr").val(data.cveRevOficios);
				   if(data.fecFechaEmiOf != null && data.fecFechaEmiOf != ''){
					   // Bloquea campos
					    $("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").prop('disabled',true);
						$("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").removeClass("red");
						$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").prop('disabled',true);
						$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").removeClass("red");
						$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecEmisionReqDocSegCorr").hide();
						// DesBloquea Campos
						$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',false);
						$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").addClass("red");
				   }
				   if(data.fecFechaNotOf != null && data.fecFechaNotOf != ''){
						$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
						$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
						$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecNotifReqDocSegCorr").hide();
						jsValidaActivaFecAte();

						
				   }
				   if(data.fecFechaAtencionOf != null && data.fecFechaAtencionOf != ''){
					   jsBloqueaFormaRD();
				   }
			   }
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){			
				alert('Los datos se guardaron correctamente');
				desbloquear();
				ejecutaReglasValidacion();
			});	
	}	 
}


function jsvalidarAlfaNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 

function jsBloqueaFormaRD(){
	$("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").prop('disabled',true);
	$("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").removeClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").prop('disabled',true);
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").removeClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").prop('disabled',true);
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").removeClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #txObservacionesReqDocSegCorr").prop('disabled',true);
	$("form#reqDocumentacionSeguimientoCorreccionForm #txObservacionesReqDocSegCorr").removeClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #btnGuardarReqDoc").prop('disabled',true);
	// los botones de fechas
	$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecEmisionReqDocSegCorr").hide();
	$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecNotifReqDocSegCorr").hide();
	$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecAtenReqDocSegCorr").hide();
}

function jsBloqueoInicialRD(){
	// Se bloquean los campos
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").prop('disabled',true);
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").removeClass("red");
	// Se habilitan los campos
	$("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").prop('disabled',false);
	$("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").addClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").prop('disabled',false);
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").addClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #txObservacionesReqDocSegCorr").prop('disabled',false);
	$("form#reqDocumentacionSeguimientoCorreccionForm #txObservacionesReqDocSegCorr").addClass("red");
	// los botones de fechas
	$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecEmisionReqDocSegCorr").show();
	$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecNotifReqDocSegCorr").hide();
	$("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecAtenReqDocSegCorr").hide();
}

function jsDesBloqueaFormaRD(){
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',false);
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").addClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").prop('disabled',false);
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").addClass("red");
	// Se habilitan los campos
	$("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").prop('disabled',false);
	$("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").addClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").prop('disabled',false);
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").addClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #txObservacionesReqDocSegCorr").prop('disabled',false);
	$("form#reqDocumentacionSeguimientoCorreccionForm #txObservacionesReqDocSegCorr").addClass("red");
	$("form#reqDocumentacionSeguimientoCorreccionForm #btnGuardarReqDoc").prop('disabled',false);
	jsLimpiaFormaRD();
}

function jsLimpiaFormaRD(){
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").val('');
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").val('');
	$("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").val('');
	$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").val('');
	$("form#reqDocumentacionSeguimientoCorreccionForm #txObservacionesReqDocSegCorr").val('');
	$("form#reqDocumentacionSeguimientoCorreccionForm #cveRevOficiosReqDocSegCorr").val('');
	$("form#reqDocumentacionSeguimientoCorreccionForm #cvePresentaCorrReqDocSegCorr").val('');
	$("form#reqDocumentacionSeguimientoCorreccionForm #labelNumFolio").html('');
	$("form#reqDocumentacionSeguimientoCorreccionForm #labelFecEmi").html('');
}

function jsValidaRequeridosRD(){
	var resp = false;
	$("form#reqDocumentacionSeguimientoCorreccionForm #labelNumFolio").html('');
	$("form#reqDocumentacionSeguimientoCorreccionForm #labelFecEmi").html('');
	
	if($("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").val() == ''){
		$("form#reqDocumentacionSeguimientoCorreccionForm #labelNumFolio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").val() == ''){
		$("form#reqDocumentacionSeguimientoCorreccionForm #labelFecEmi").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#reqDocumentacionSeguimientoCorreccionForm #numFolioOficioReqDocSegCorr").val() != '' && 
			$("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").val() != ''){
		resp = true;
	}
	
	return resp;
}

function jsValidaFecEmisionRD(){
	
	 $("form#reqDocumentacionSeguimientoCorreccionForm #labelFecEmi").html('');
	 var fecIni = $("form#reqDocumentacionSeguimientoCorreccionForm #fecAutCedulaRevRD").val();
	 var fecFinal = $("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").val(fecFinal);
			 $("form#reqDocumentacionSeguimientoCorreccionForm #labelFecEmi").html('');
			 $("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecNotifReqDocSegCorr").show();
				
			 jsValidaActivaFecNot();
		 }else{
			 $("form#reqDocumentacionSeguimientoCorreccionForm #labelFecEmi").html('<label class="etiquetaError">La Fecha Emisi&oacute;n del Oficio  no puede ser menor a la Fecha Autorizaci&oacute;n Cedula Revisi&oacute;n </label>');
			 jsLimpiafecEmisionReqDocSegCorr();
			 $("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecNotifReqDocSegCorr").hide();
		 }
	 }
}

function jsLimpiafecEmisionReqDocSegCorr(){
	 $("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").val('');
	 $("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
	 $("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
	 jsLimpiafecNotifReqDocSegCorr();
}

function jsLimpiafecNotifReqDocSegCorr(){
	 $("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").val('');
	 $("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").val('');
	 $("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").prop('disabled',true);
	 $("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").removeClass("red");
	 $("form#reqDocumentacionSeguimientoCorreccionForm #btnLimpiafecAtenReqDocSegCorr").hide();
}

function jsLimpiafecAtenReqDocSegCorr(){
	 $("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").val('');
}

function generaResumenReqDocumenta(data){
	 $("form#formResumenSeguimiento #lbValFechaEmisioOfReq").text(data.fechaEmision);
	 $("form#formResumenSeguimiento #lbValFechaNotOfReq").text(data.fechaNotificacion);
	 $("form#formResumenSeguimiento #lbValFechaAtencOfReq").text(data.fechaAtencion);
}