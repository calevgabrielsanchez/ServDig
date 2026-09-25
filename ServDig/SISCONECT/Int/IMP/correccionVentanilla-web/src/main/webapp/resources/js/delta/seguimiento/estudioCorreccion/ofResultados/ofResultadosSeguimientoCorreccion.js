
/**
 * @author Enrique Duran Jimenez
 * @since 10/08/2012
 * Funcion que inicializa la pestaña de Oficio de resultados
 */
function inicializaOfResultadosSegCorreccion(){
	
	inicializaFechasOfResultadosSegCorreccion();
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 10/08/2012
 * Funcion inicializa el datepicker de la pantalla de Oficio de Resultados del seguimiento de correccion.
 */
function inicializaFechasOfResultadosSegCorreccion(){
	
	$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").val("");
			jsValidaFecEmsionOR();
	    }
	});
	
	$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").val("");
			jsValidaFecNotificacion();
	    }
	});
	
	$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			jsValidaFecAtencion();
	    }
	});

	$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr," +
			  "form#ofSeguimientoCorreccionForm #fecNotORSegCorr," +
			  "form#ofSeguimientoCorreccionForm #fecAteORSegCorr").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr," +
			  "form#ofSeguimientoCorreccionForm #fecNotORSegCorr," +
			  "form#ofSeguimientoCorreccionForm #fecAteORSegCorr").datepicker('option', 'minDate', jsFechaMinSeguimiento);
	
	 var cveSolCorr = $("form#ofSeguimientoCorreccionForm #cveSolCorrOfResSegCorr").val();
	 var variable = '{"cveSolCorr":' + cveSolCorr + '}';		
	 var variableJson = jQuery.parseJSON(variable);
	 $.postJSON_Sync("correccion/buscaOfResultados.do", variableJson, function(data) {	
		   if(data != null){
			   generaResumenOficioResultados(data);
			   jsDesBloqueaFormaOR();
			   jsBloqueoInicialOR();
			   if(data.cvePresentaCorr != null && data.cvePresentaCorr != ''){
				   $("form#ofSeguimientoCorreccionForm #cvePresentaCorrOfResSegCorr").val(data.cvePresentaCorr);
			   }
			   if(data.fechaAutProrroga != null && data.fechaAutProrroga != ''){
				   $("form#ofSeguimientoCorreccionForm #fecAutProrrogaRO").val(data.fechaAutProrroga); 
			   }
			   if(data.numFolioOficio != null && data.numFolioOficio != ''){
				   $("form#ofSeguimientoCorreccionForm #folioOfiRes").val(data.numFolioOficio);
			   }
			   if(data.fechaEmision != null && data.fechaEmision != ''){
				   $("form#ofSeguimientoCorreccionForm #folioOfiRes").prop('disabled',true);
				   $("form#ofSeguimientoCorreccionForm #folioOfiRes").removeClass("red");
				   
				   $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val(data.fechaEmision);
				   $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").prop('disabled',true);
				   $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").removeClass("red");
				   $("form#ofSeguimientoCorreccionForm #btnLimpiafecEmiORSegCorr").hide();				   
				   
				   $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").prop('disabled',false);
				   $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").addClass("red");
				   $("form#ofSeguimientoCorreccionForm #btnLimpiafecNotORSegCorr").show();
			   }
			   if(data.fechaNotificacion != null && data.fechaNotificacion != ''){
				   $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").val(data.fechaNotificacion);
				   
				   $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").prop('disabled',true);
				   $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").removeClass("red");
				   $("form#ofSeguimientoCorreccionForm #btnLimpiafecNotORSegCorr").hide();
				   jsHabilitaFecAte();
			   }
			   if(data.fechaAtencion != null && data.fechaAtencion != ''){
				   $("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").val(data.fechaAtencion);
				   jsBloqueaFormaOR();
			   }
		   }else{
			   jsDesBloqueaFormaOR();
			   jsBloqueoInicialOR();
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
 * @author Enrique Duran Jimenez
 * @since 13/08/2012
 * Funcion bloque los campos necesarios al iniciar la pantalla
 */
function jsBloqueoInicialOR(){
	// Se bloquean los campos
	$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").prop('disabled',true);
	$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").removeClass("red");
	$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").prop('disabled',true);
	$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").removeClass("red");
	// Se habilitan los campos
	$("form#ofSeguimientoCorreccionForm #folioOfiRes").prop('disabled',false);
	$("form#ofSeguimientoCorreccionForm #folioOfiRes").addClass("red");
	$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").prop('disabled',false);
	$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").addClass("red");
	// BOTONES
	$("form#ofSeguimientoCorreccionForm #btnLimpiafecNotORSegCorr").hide();
	$("form#ofSeguimientoCorreccionForm #btnLimpiafecAteORSegCorr").hide();
	$("form#ofSeguimientoCorreccionForm #btnLimpiafecEmiORSegCorr").show();
	
}


/**
 * @author Enrique Duran Jimenez
 * @since 13/08/2012
 * Funcion que valida si ya se capturaron los campos requeridos y habilita la fecha de Notificacion
 */
function jsValidaRequeridos(){
	
	if($("form#ofSeguimientoCorreccionForm #folioOfiRes").val() != '' && 
	   $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val() != ''){
		
		$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").prop('disabled',false);
		$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").addClass("red");
		$("form#ofSeguimientoCorreccionForm #btnLimpiafecNotORSegCorr").show();
	}
}

/**
 * @author Enrique Duran Jimenez
 * @since 13/08/2012
 * Funcion que valida si ya se capturarola fecha de Emision y habilita la fecha de Notificacion
 */
function jsHabilitaFecAte(){
	
	if($("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").val() != '' ){
				
				$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").prop('disabled',false);
				$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").addClass("red");
				$("form#ofSeguimientoCorreccionForm #btnLimpiafecAteORSegCorr").show();
			}
}


/**
 * @author Enrique Duran Jimenez
 * @since 13/08/2012
 * Funcion que valida que la fecha de notificacion no sea mayor a la fecha de Emision del oficio.
 */
function jsValidaFecNotificacion(){
	
	 $("form#ofSeguimientoCorreccionForm #labelfecFechaNotOf").html('');
	 var fecIni = $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val();
	 var fecFin = $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").val();
	 if(fecIni != '' && fecFin != ''){
		 if(comparaFechas(fecIni, fecFin,"-")){
			 $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").val(fecFin);
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaNotOf").html('');
			 jsHabilitaFecAte();
		 }else{
			 $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").val('');
			 $("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").val('');
			 $("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").prop('disabled','disabled');
			 $("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").removeClass("red");
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaNotOf").html('<label class="etiquetaError">La Fecha de Notificaci&oacute;n no puede ser menor a la Fecha Emisi&oacute;n del Oficio</label>');
		 }
	 }
}

/**
 * @author Enrique Duran Jimenez
 * @since 13/08/2012
 * Funcion que valida que la fecha de Atencion no sea mayor a la fecha de Notificacion del oficio.
 */
function jsValidaFecAtencion(){
	
	 $("form#ofSeguimientoCorreccionForm #labelfecFechaAtencionOf").html('');
	 var fecIni = $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").val();
	 var fecFin = $("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").val();
	 if(fecIni != '' && fecFin != ''){
		 if(comparaFechas(fecIni, fecFin,"-")){
			 $("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").val(fecFin);
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaAtencionOf").html('');
		 }else{
			 $("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").val('');
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaAtencionOf").html('<label class="etiquetaError">La Fecha Atenci&oacute;n no puede ser menor a la Fecha de Notificaci&oacute;n</label>');
		 }
	 }
}


function jsGuardaOfResultados(){
	if(jsValidaRequeridosGuardar() == true){
		var model = $("#ofSeguimientoCorreccionForm").serializeObject(true);
		   bloquear();
		   $.postJSON("correccion/guardarOfResultados.do", model, function(data) {	
			   if(data != null){
				   $("form#ofSeguimientoCorreccionForm #cveRevOficiosOfResSegCorr").val(data.cveRevOficios);
				   if(data.fecFechaEmiOf != null && data.fecFechaEmiOf != ''){
					   $("form#ofSeguimientoCorreccionForm #folioOfiRes").prop('disabled',true);
					   $("form#ofSeguimientoCorreccionForm #folioOfiRes").removeClass("red");
					   $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").prop('disabled',true);
					   $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").removeClass("red");
					   $("form#ofSeguimientoCorreccionForm #btnLimpiafecEmiORSegCorr").hide();
					   $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").prop('disabled',false);
					   $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").addClass("red");
					   $("form#ofSeguimientoCorreccionForm #btnLimpiafecNotORSegCorr").show();
				   }
				   if(data.fecFechaNotOf != null && data.fecFechaNotOf != ''){
					   $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").prop('disabled',true);
					   $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").removeClass("red");
					   $("form#ofSeguimientoCorreccionForm #btnLimpiafecNotORSegCorr").hide();
					   jsHabilitaFecAte();
					    //cuando se tenga fecha de notificacion y cuando tengan chequeaados todos los checks de cedula de validacion se 
						//habilita la seccion de pagos
						//alert("indAutoPrimera : "+ indAutoPrimera);
						//alert("indAutoSegunda : "+ indAutoSegunda);
						if(indAutoSegunda == 2 && indAutoPrimera == 2){
							activaSeccionConsolidaImportes();
						}
				   }
				   if(data.fecFechaAtencionOf != null && data.fecFechaAtencionOf != ''){
					   jsBloqueaFormaOR();
				   }
			   }
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){			
				alert('Los datos se guardaron correctamente');
				desbloquear();
				cveEstatusRecepcion=14;
				ejecutaReglasValidacion(rolUsuario);
			});	
	}	 
}


function jsValidaRequeridosGuardar(){
	var resp = false;
	$("form#ofSeguimientoCorreccionForm #labelNumFolioOficio").html('');
	$("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('');
	
	if($("form#ofSeguimientoCorreccionForm #folioOfiRes").val() == ''){
		$("form#ofSeguimientoCorreccionForm #labelNumFolioOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val() == ''){
		$("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#ofSeguimientoCorreccionForm #folioOfiRes").val() != '' && 
			$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val() != ''){
		resp = true;
	}
	
	return resp;
}

function jsBloqueaFormaOR(){
	$("form#ofSeguimientoCorreccionForm #folioOfiRes").prop('disabled',true);
	$("form#ofSeguimientoCorreccionForm #folioOfiRes").removeClass("red");
	$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").prop('disabled',true);
	$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").removeClass("red");
	$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").prop('disabled',true);
	$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").removeClass("red");
	$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").prop('disabled',true);
	$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").removeClass("red");
	$("form#ofSeguimientoCorreccionForm #btnGuardarOfRes").prop('disabled',true);
	
	$("form#ofSeguimientoCorreccionForm #btnLimpiafecEmiORSegCorr").hide();
	$("form#ofSeguimientoCorreccionForm #btnLimpiafecNotORSegCorr").hide();
	$("form#ofSeguimientoCorreccionForm #btnLimpiafecAteORSegCorr").hide();
	
}

function jsDesBloqueaFormaOR(){
	$("form#ofSeguimientoCorreccionForm #folioOfiRes").prop('disabled',false);
	$("form#ofSeguimientoCorreccionForm #folioOfiRes").addClass("red");
	$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").prop('disabled',false);
	$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").addClass("red");
	$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").prop('disabled',false);
	$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").addClass("red");
	$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").prop('disabled',false);
	$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").addClass("red");
	$("form#ofSeguimientoCorreccionForm #btnGuardarOfRes").prop('disabled',false);
	jsLimpiaFormaOR();
}

function jsLimpiaFormaOR(){
	$("form#ofSeguimientoCorreccionForm #folioOfiRes").val('');
	$("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val('');
	$("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").val('');
	$("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").val('');
	$("form#ofSeguimientoCorreccionForm #cvePresentaCorrOfResSegCorr").val('');
	$("form#ofSeguimientoCorreccionForm #cveRevOficiosOfResSegCorr").val('');
	$("form#ofSeguimientoCorreccionForm #labelNumFolioOficio").html('');
	$("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('');
}

function jsValidaFecEmsionOR(){
	 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('');
	 /** primero valida contra las fechas
	  * 1.- Fecha atencion
	  * 2.- Fecha Notificacion
	  * 3.- Fecha Emision
	  */
	 var fechaEmision = $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val();
	 // Primera Validacion Requerimiento Documentacion
	 var fechaAtencionRD     = $("form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr").val();           // fec Atencion
	 var fechaNotificacionRD = $("form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr").val();          // fec Notificacion
	 var fechaEmisionRD      = $("form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr").val();        // fec Emision
	 // Segunda Validacion Recepcion
	 var fechaPresentaCorr   = $("form#formResumenSeguimiento #lbFechaPreSolCorrVal").html();  				      // fec Presenta Correccion
	 // Tercera Validacion
	 var fechaAutProrroga    = $("form#ofSeguimientoCorreccionForm #fecAutProrrogaRO").val();                // fec Autorización Prorroga
	 // Cuarta Validacion
	 //var fechaAutSolicitud    = $("form#ofSeguimientoCorreccionForm #fecAutSolicitudRO").val();            // fec Autorización Solicitud
	 var fechaAutSolicitud    = '06-08-2012';                                                                 // fec Autorización Solicitud
	 	 
	 if(fechaAtencionRD != undefined && fechaAtencionRD != ''){
		 if(jsValidaFechas(fechaAtencionRD,fechaEmision)){
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val(fechaEmision);
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('');
			 jsValidaRequeridos();
		 }else{
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val('');
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('<label class="etiquetaError">La Fecha Emisi&oacute;n del Oficio  no puede ser menor a la Fecha Autorizaci&oacute;n del Requerimiento de Documentaci&oacute;n</label>');
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
		 }
	 }else if(fechaNotificacionRD != undefined && fechaNotificacionRD != ''){
		 if(jsValidaFechas(fechaNotificacionRD,fechaEmision)){
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val(fechaEmision);
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('');
			 jsValidaRequeridos();
		 }else{
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val('');
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('<label class="etiquetaError">La Fecha Emisi&oacute;n del Oficio  no puede ser menor a la Fecha Notificaci&oacute;n del Requerimiento de Documentaci&oacute;n</label>');
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
		 }
	 }else if(fechaEmisionRD != undefined && fechaEmisionRD != ''){
		 if(jsValidaFechas(fechaEmisionRD,fechaEmision)){
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val(fechaEmision);
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('');
			 jsValidaRequeridos();
		 }else{
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val('');
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('<label class="etiquetaError">La Fecha Emisi&oacute;n del Oficio  no puede ser menor a la Fecha Emisi&oacute;n del Requerimiento de Documentaci&oacute;n</label>');
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
		 }
	 }else if(fechaPresentaCorr != undefined && fechaPresentaCorr != ''){
		 if(jsValidaFechas(fechaPresentaCorr,fechaEmision)){
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val(fechaEmision);
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('');
			 jsValidaRequeridos();
		 }else{
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val('');
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('<label class="etiquetaError">La Fecha Emisi&oacute;n del Oficio  no puede ser menor a la Fecha de Presentaci&oacute;n de la Correcci&oacute;n</label>');
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
		 }
	 }else if(fechaAutProrroga != undefined && fechaAutProrroga != ''){
		 if(jsValidaFechas(fechaAutProrroga,fechaEmision)){
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val(fechaEmision);
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('');
			 jsValidaRequeridos();
		 }else{
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val('');
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('<label class="etiquetaError">La Fecha Emisi&oacute;n del Oficio  no puede ser menor a la Fecha de Autorizaci&oacute;n de Prorroga</label>');
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
		 }
	 }else if(fechaAutSolicitud != undefined && fechaAutSolicitud != ''){
		 if(jsValidaFechas(fechaAutSolicitud,fechaEmision)){
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val(fechaEmision);
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('');
			 jsValidaRequeridos();
		 }else{
			 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val('');
			 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('<label class="etiquetaError">La Fecha Emisi&oacute;n del Oficio  no puede ser menor a la Fecha de Autorizaci&oacute;n de la Solicitud</label>');
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").prop('disabled',true);
			 $("form#ofSeguimientoCorreccionForm #fecNotifReqDocSegCorr").removeClass("red");
		 }
	 }
}

function jsLimpiafecAteORSegCorr(){
	 $("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").val('');
	 $("form#ofSeguimientoCorreccionForm #labelfecFechaAtencionOf").html('');
	 
}

function jsLimpiafecNotORSegCorr(){
	 $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").val('');
	 $("form#ofSeguimientoCorreccionForm #labelfecFechaNotOf").html('');
	 $("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").prop('disabled',true);
	 $("form#ofSeguimientoCorreccionForm #fecAteORSegCorr").removeClass("red");
	 $("form#ofSeguimientoCorreccionForm #btnLimpiafecAteORSegCorr").hide();
	 jsLimpiafecAteORSegCorr();
}

function jsLimpiafecEmiORSegCorr(){
	 $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val('');
	 $("form#ofSeguimientoCorreccionForm #labelfecFechaEmiOf").html('');
	 $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").prop('disabled',true);
	 $("form#ofSeguimientoCorreccionForm #fecNotORSegCorr").removeClass("red");
	 $("form#ofSeguimientoCorreccionForm #btnLimpiafecNotORSegCorr").hide();
	 jsLimpiafecNotORSegCorr();
	 jsLimpiafecAteORSegCorr();
}

function generaResumenOficioResultados(data){
	 $("form#formResumenSeguimiento #lbValFechaEmisionOfRes").text(data.fechaEmision);
	 $("form#formResumenSeguimiento #lbValFechaNotiOfRes").text(data.fechaNotificacion);
	 $("form#formResumenSeguimiento #lbValFechaAtencion").text(data.fechaAtencion);
	
}