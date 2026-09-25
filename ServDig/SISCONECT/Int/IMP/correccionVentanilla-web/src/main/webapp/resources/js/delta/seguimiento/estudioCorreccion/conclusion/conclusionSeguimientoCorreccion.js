
/**

 * Funcion que inicializa la pestaña de Conclusion
 */
function inicializaConclusionSegCorreccion(){
	
	inicializaFechasConclusionsSegCorreccion();
	
}

/**

 * Funcion inicializa el datepicker de la pantalla de Conclusion del seguimiento de correccion.
 */
function inicializaFechasConclusionsSegCorreccion(){
	
	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").val("");
			jsValidaFecFolioConclusion();
	    }
	});
	
	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").datepicker('option','minDate',fechaPresentacionSeguimientoCorreccion);
	
	$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			jsValidaFecNotificacionConclusion();
	    }
	});
	

//	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion," +
//			  "form#conclusionSeguimientoCorreccionForm #fecNotConclusion").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
//	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion," +
//			  "form#conclusionSeguimientoCorreccionForm #fecNotConclusion").datepicker('option', 'minDate', jsFechaMinSeguimiento);
	
	 var cveSolCorr = $("form#conclusionSeguimientoCorreccionForm #cveSolCorrConclusionSegCorr").val();
	 var variable = '{"cveSolCorr":' + cveSolCorr + '}';		
	 var variableJson = jQuery.parseJSON(variable);
	 $.postJSON_Sync("correccion/buscaConclusion.do", variableJson, function(data) {	
		   if(data != null){
			   generaResumentConclusion(data);
			   jsDesBloqueaFormaConclusion();
			   jsBloqueoInicialConclusion();
			   if(data.cvePresentaCorr != null){
				   $("form#conclusionSeguimientoCorreccionForm #cvePresentaCorrConclusionSegCorr").val(data.cvePresentaCorr);
			   }
			   if(data.numFolioOficio != null){
				   $("form#conclusionSeguimientoCorreccionForm #folioConclusion").val(data.numFolioOficio);
			   }
			   if(data.fechaEmision != null){
				   $("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val(data.fechaEmision);
				   
				   $("form#conclusionSeguimientoCorreccionForm #folioConclusion").prop('disabled',true);
				   $("form#conclusionSeguimientoCorreccionForm #folioConclusion").removeClass("red");
				   $("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").prop('disabled',true);
				   $("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").removeClass("red");
				   $("form#conclusionSeguimientoCorreccionForm #btnLimpiafecEmiConclusion").hide();
				   $("form#conclusionSeguimientoCorreccionForm #labelfecFechaNotConclusion").html('');
				   
				   $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").prop('disabled',false);
				   $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").addClass("red");
				   $("form#conclusionSeguimientoCorreccionForm #btnLimpiafecNotConclusion").show();
				   
			   }
			   if(data.fechaNotificacion != null){
				   $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").val(data.fechaNotificacion);
				   jsBloqueaFormaConclusion();
				  
				  
			   }
		   }else{
			   jsDesBloqueaFormaConclusion();
			   jsBloqueoInicialConclusion();
		   }
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){			
			desbloquear();
			
		});	
	
}


function jsvalidarAlfaNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
}


/**
 * 
 * 
 * Funcion bloque los campos necesarios al iniciar la pantalla
 */
function jsBloqueoInicialConclusion(){
	// Se bloquean los campos
	$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").prop('disabled',true);
	$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").removeClass("red");
	// Se habilitan los campos
	$("form#conclusionSeguimientoCorreccionForm #folioConclusion").prop('disabled',false);
	$("form#conclusionSeguimientoCorreccionForm #folioConclusion").addClass("red");
	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").prop('disabled',false);
	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").addClass("red");
	
	$("form#conclusionSeguimientoCorreccionForm #btnLimpiafecEmiConclusion").show();
	$("form#conclusionSeguimientoCorreccionForm #btnLimpiafecNotConclusion").hide();
	
}


/**
 * @author Enrique Duran Jimenez
 * @since 14/08/2012
 * Funcion que valida si ya se capturaron los campos requeridos y habilita la fecha de Notificacion
 */
function jsValidaRequeridosConclusion(){
	$("form#conclusionSeguimientoCorreccionForm #labelNumFolioConclusion").html('');
	$("form#conclusionSeguimientoCorreccionForm #labelfecFechaEmiConclusion").html('');
	
	if($("form#conclusionSeguimientoCorreccionForm #folioConclusion").val() != '' && 
	   $("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val() != ''){
		
		$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").prop('disabled',false);
		$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").addClass("red");
		$("form#conclusionSeguimientoCorreccionForm #btnLimpiafecNotConclusion").show();
	}
}



/**
 * @author Enrique Duran Jimenez
 * @since 14/08/2012
 * Funcion que valida que la fecha de notificacion no sea mayor a la fecha de Emision del oficio.
 */
function jsValidaFecNotificacionConclusion(){
	
	 $("form#conclusionSeguimientoCorreccionForm #labelfecFechaNotConclusion").html('');
	 var fecIni = $("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val();
	 var fecFin = $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").val();
	 if(fecIni != '' && fecFin != ''){
		 if(comparaFechas(fecIni, fecFin,"-")){
			 $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").val(fecFin);
			 $("form#conclusionSeguimientoCorreccionForm #labelfecFechaNotConclusion").html('');
			 jsHabilitaFecAte();
		 }else{
			 $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").val('');
			 $("form#conclusionSeguimientoCorreccionForm #labelfecFechaNotConclusion").html('<label class="etiquetaError">La Fecha de Notificaci&oacute;n no puede ser menor a la Fecha Emisi&oacute;n del Oficio</label>');
		 }
	 }
}


/**

 * Funcion manda la informacion de la forma al controlador.
 */
function jsGuardaConclusion(){
	if(jsValidaRequeridosConclusionGuardar() == true){
		var model = $("#conclusionSeguimientoCorreccionForm").serializeObject(true);
		   bloquear();
		   $.postJSON("correccion/guardarConclusion.do", model, function(data) {	
			   if(data != null){
				   $("form#conclusionSeguimientoCorreccionForm #cveRevOficiosConclusionSegCorr").val(data.cveRevOficios);
				   if(data.fecFechaEmiOf != null && data.fecFechaEmiOf != ''){
					    $("form#conclusionSeguimientoCorreccionForm #folioConclusion").prop('disabled',true);
						$("form#conclusionSeguimientoCorreccionForm #folioConclusion").removeClass("red");
						$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").prop('disabled',true);
						$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").removeClass("red");
						$("form#conclusionSeguimientoCorreccionForm #btnLimpiafecEmiConclusion").hide();
						
						$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").prop('disabled',false);
						$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").addClass("red");
						$("form#conclusionSeguimientoCorreccionForm #btnLimpiafecNotConclusion").show();
				   }
				   if(data.fecFechaNotOf != null && data.fecFechaNotOf != ''){
					   jsBloqueaFormaConclusion();
				   }
			   }
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){			
				alert('Los datos se guardaron correctamente');
				bloquearTodo();
				setTabHabilitado('seguimientoCorreccionResumen');
				desbloquear();
			});	
	}	 
}


function jsValidaRequeridosConclusionGuardar(){
	var resp = false;
	$("form#conclusionSeguimientoCorreccionForm #labelNumFolioConclusion").html('');
	$("form#conclusionSeguimientoCorreccionForm #labelfecFechaEmiConclusion").html('');
	
	if($("form#conclusionSeguimientoCorreccionForm #folioConclusion").val() == ''){
		$("form#conclusionSeguimientoCorreccionForm #labelNumFolioConclusion").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val() == ''){
		$("form#conclusionSeguimientoCorreccionForm #labelfecFechaEmiConclusion").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#conclusionSeguimientoCorreccionForm #folioConclusion").val() != '' && 
			$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val() != ''){
		resp = true;
	}
	
	return resp;
}

function jsBloqueaFormaConclusion(){
	$("form#conclusionSeguimientoCorreccionForm #folioConclusion").prop('disabled',true);
	$("form#conclusionSeguimientoCorreccionForm #folioConclusion").removeClass("red");
	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").prop('disabled',true);
	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").removeClass("red");
	$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").prop('disabled',true);
	$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").removeClass("red");
	$("form#conclusionSeguimientoCorreccionForm #btnGuardarConclusion").prop('disabled',true);
	$("form#conclusionSeguimientoCorreccionForm #btnLimpiafecEmiConclusion").hide();
	$("form#conclusionSeguimientoCorreccionForm #btnLimpiafecNotConclusion").hide();
	
}

function jsDesBloqueaFormaConclusion(){
	$("form#conclusionSeguimientoCorreccionForm #folioConclusion").prop('disabled',false);
	$("form#conclusionSeguimientoCorreccionForm #folioConclusion").addClass("red");
	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").prop('disabled',false);
	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").addClass("red");
	$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").prop('disabled',false);
	$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").addClass("red");
	$("form#conclusionSeguimientoCorreccionForm #btnGuardarConclusion").prop('disabled',false);
	jsLimpiaFormaConclusion();
}

function jsLimpiaFormaConclusion(){
	$("form#conclusionSeguimientoCorreccionForm #folioConclusion").val('');
	$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val('');
	$("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").val('');
	$("form#conclusionSeguimientoCorreccionForm #cvePresentaCorrConclusionSegCorr").val('');
	$("form#conclusionSeguimientoCorreccionForm #cveRevOficiosConclusionSegCorr").val('');
	$("form#conclusionSeguimientoCorreccionForm #labelNumFolioConclusion").html('');
	$("form#conclusionSeguimientoCorreccionForm #labelfecFechaEmiConclusion").html('');
}

function jsValidaFecFolioConclusion(){
	
	 $("form#conclusionSeguimientoCorreccionForm #labelfecFechaEmiConclusion").html('');
	 var fecIni = fechaPresentacionSeguimientoCorreccion;
	 //var fecIni = '06-08-2012';
	 var fecFinal = $("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val();
	 if(fecIni != '' && fecFinal != ''){
		 if($("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val() >= $('form#reqDocumentacionSeguimientoCorreccionForm #fechaElaboraPresenHdn').val()){
			 if(jsValidaFechas(fecIni,fecFinal)){
				 $("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val(fecFinal);
				 $("form#conclusionSeguimientoCorreccionForm #labelfecFechaEmiConclusion").html('');
				 jsValidaRequeridosConclusion();
			 }else{
				 $("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val('');
				 $("form#conclusionSeguimientoCorreccionForm #labelfecFechaEmiConclusion").html('<label class="etiquetaError">La Fecha Emisi&oacute;n del Oficio  no puede ser menor a la Fecha Autorizaci&oacute;n Cedula Validaci&oacute;n </label>');
				 $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").prop('disabled',true);
				 $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").removeClass("red");
				 $("form#conclusionSeguimientoCorreccionForm #btnLimpiafecNotConclusion").hide();
			 }
	 	}else{
	 		alert("La fecha de emision del oficio debe ser mayor a la fecha de presentacion");
	 		$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val('');
	 	}
	 }
}

function jsLimpiafecNotConclusion(){
	 $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").val('');
}

function jsLimpiafecEmiConclusion(){
	
	 $("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").val('');
	 $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").prop('disabled',true);
	 $("form#conclusionSeguimientoCorreccionForm #fecNotConclusion").removeClass("red");
	 $("form#conclusionSeguimientoCorreccionForm #btnLimpiafecNotConclusion").hide();
	 jsLimpiafecNotConclusion();
}

function generaResumentConclusion(data){
	
	 $("form#formResumenSeguimiento #lbValFechaEmiOfConc").text(data.fechaEmision);
	 $("form#formResumenSeguimiento #lbValFechaNotiOfiConclu").text(data.fechaNotificacion);
}