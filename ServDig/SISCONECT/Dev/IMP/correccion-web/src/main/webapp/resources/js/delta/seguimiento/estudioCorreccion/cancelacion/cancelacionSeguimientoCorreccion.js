
/**
 * @author Enrique Duran Jimenez
 * @since 02/08/2012
 * Funcion que inicializa la pestaña de cancelacion
 */
function inicializaCancelacionSegCorreccion(){
	
	inicializaFechasCancelacionSegCorreccion();
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 02/08/2012
 * Funcion inicializa el datepicker de la pantalla de cancelacion del seguimiento de correccion
 */
function inicializaFechasCancelacionSegCorreccion(){
	$("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			jsValidaFecCancelacion();
	    }
	});

	$( "form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$( "form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").datepicker('option', 'minDate', jsFechaMinSeguimiento);
	
	 var cveSolCorr = $("form#cancelacionSeguimientoCorreccionForm #cveSolCorrCancelacionSegCorr").val();
	 var variable = '{"cveSolCorr":' + cveSolCorr + '}';		
	 var variableJson = jQuery.parseJSON(variable);
	 $.postJSON("correccion/buscaCancelacion.do", variableJson, function(data) {	
		   if(data != null){
			   generaResumenCancelacion(data);
			   jsDesBloqueaFormaCancelacion();
			   $("form#cancelacionSeguimientoCorreccionForm #labelFuncionarioReg").html('<label>' + data.funcionarioRegistra + '</label>');
			   $("form#cancelacionSeguimientoCorreccionForm #labelFuncionarioAut").html('<label>' + data.funcionarioAutoriza + '</label>');
			   $("form#cancelacionSeguimientoCorreccionForm #cveSolCorrCancelacionSegCorr").val(data.cveSolCorr);
			   $("form#cancelacionSeguimientoCorreccionForm #cvePresentaCorrCancelacionSegCorr").val(data.cvePresentaCorr);
			   
			   var myselect=document.getElementById("cboMotivos");
				myselect.options.length = 1;
				
				for(var i = 0 ; i < data.motivosCancelacion.length ; i++){
					myselect.add(new Option(data.motivosCancelacion[i][1], data.motivosCancelacion[i][0]));
				}
				if(data.numFolioOficio != null){
					 $("form#cancelacionSeguimientoCorreccionForm #refCancelacion").val(data.numFolioOficio);
				}
				if(data.fecFechaEmiOf != null){
					 $("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").val(data.fecFechaEmiOf);
				}
				if(data.idMotivoCancelacion != null){
					 $("form#cancelacionSeguimientoCorreccionForm #cboMotivos").val(data.idMotivoCancelacion);
				}
				if(data.numFolioOficio != null && data.fecFechaEmiOf != null && data.idMotivoCancelacion != null){
					jsBloqueaFormaCancelacion();
				}
		   }
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){			
			desbloquear();
		});	
	
	
}


/**
 * @author Enrique Duran Jimenez
 * @since 14/08/2012
 * Funcionque valida los campos requeridos al guardar lo datos.
 */
function jsValidaFormaCancelacion(){
	
	$("form#cancelacionSeguimientoCorreccionForm #labelRefCancelacion").html('');
	$("form#cancelacionSeguimientoCorreccionForm #labelFecCancelacionSegCorr").html('');
	$("form#cancelacionSeguimientoCorreccionForm #labelIdMotivocancelacion").html('');
	var resp = false;
	
	if($("form#cancelacionSeguimientoCorreccionForm #refCancelacion").val() == ''){
		$("form#cancelacionSeguimientoCorreccionForm #labelRefCancelacion").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").val() == ''){
		$("form#cancelacionSeguimientoCorreccionForm #labelFecCancelacionSegCorr").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#cancelacionSeguimientoCorreccionForm #cboMotivos").val() == '-1'){
		$("form#cancelacionSeguimientoCorreccionForm #labelIdMotivocancelacion").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	
	if($("form#cancelacionSeguimientoCorreccionForm #refCancelacion").val() != '' &&
			$("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").val() != '' &&
			$("form#cancelacionSeguimientoCorreccionForm #cboMotivos").val() != '-1'){
		resp = true;
	}
	
	return resp;
}

/**
 * @author Enrique Duran Jimenez
 * @since 15/08/2012
 * Funcion manda la informacion de la forma al controlador.
 */
function jsGuardaCancelacion(){
	if(jsValidaFormaCancelacion() == true){
		var model = $("form#cancelacionSeguimientoCorreccionForm").toObject({mode:'first'})
			bloquear();
		   $.postJSON("correccion/guardarCancelacion.do", model, function(data) {	
			   if(data != null){
				   jsBloqueaFormaCancelacion();
					 $("form#formResumenSeguimiento #lbValFecAutoCancela").text(data.fecFechaEmiOf);
					 $("form#formResumenSeguimiento #lbValFolioVolaCancel").text(data.numFolioOficio);
					 $("form#formResumenSeguimiento #lbValMotivoCancel").text($("#cboMotivos option:selected").text());
			   }
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){			
				alert('Los datos se guardaron correctamente');
				desbloquear();
				
				cveEstatusRecepcion=22;
				ejecutaReglasValidacion(1);
			});	
	}	 
}


function jsBloqueaFormaCancelacion(){
	$("form#cancelacionSeguimientoCorreccionForm #refCancelacion").prop('disabled',true);
	$("form#cancelacionSeguimientoCorreccionForm #refCancelacion").removeClass("red");
	$("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").prop('disabled',true);
	$("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").removeClass("red");
	$("form#cancelacionSeguimientoCorreccionForm #cboMotivos").prop('disabled',true);
	$("form#cancelacionSeguimientoCorreccionForm #cboMotivos").removeClass("red");
	$("form#cancelacionSeguimientoCorreccionForm #btnGuardarCanelacion").prop('disabled',true);
}

function jsDesBloqueaFormaCancelacion(){
	$("form#cancelacionSeguimientoCorreccionForm #refCancelacion").prop('disabled',false);
	$("form#cancelacionSeguimientoCorreccionForm #refCancelacion").addClass("red");
	$("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").prop('disabled',false);
	$("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").addClass("red");
	$("form#cancelacionSeguimientoCorreccionForm #cboMotivos").prop('disabled',false);
	$("form#cancelacionSeguimientoCorreccionForm #cboMotivos").addClass("red");
	$("form#cancelacionSeguimientoCorreccionForm #btnGuardarCanelacion").prop('disabled',false);
	jsLimpiaFormaCancelacion();
}

function jsLimpiaFormaCancelacion(){
	$("form#cancelacionSeguimientoCorreccionForm #refCancelacion").val('');
	$("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").val('');
	$("form#cancelacionSeguimientoCorreccionForm #cboMotivos").val('-1');
	$("form#cancelacionSeguimientoCorreccionForm #cveSolCorrCancelacionSegCorr").val('');
	$("form#cancelacionSeguimientoCorreccionForm #cvePresentaCorrCancelacionSegCorr").val('');
	$("form#cancelacionSeguimientoCorreccionForm #labelFuncionarioReg").html('');
	$("form#cancelacionSeguimientoCorreccionForm #labelFuncionarioAut").html('');
	$("form#cancelacionSeguimientoCorreccionForm #labelRefCancelacion").html('');
	$("form#cancelacionSeguimientoCorreccionForm #labelFecCancelacionSegCorr").html('');
	$("form#cancelacionSeguimientoCorreccionForm #labelIdMotivocancelacion").html('');
}

function jsvalidarAlfaNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
}

function mensajeValidaFecha(nombreFecha){
	var msgErrorCancela="<label class='etiquetaError'>La Fecha de Cancelaci&oacute;n no puede ser menor a la "+ nombreFecha + "</label>";
	return msgErrorCancela;
}

function jsValidaFecCancelacion(){
	var resultValFecha;

		 $("form#cancelacionSeguimientoCorreccionForm #labelFecCancelacionSegCorr").html('');
		 var fecCancelaSegCorr = $("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").val();
		 if(fecCancelaSegCorr != '' ){
			 resultValFecha = validaListaFechas(fecCancelaSegCorr, ARRAY_FECHAS_SEGCORR);
			 if(resultValFecha[0]){
				 $("form#cancelacionSeguimientoCorreccionForm #labelFecCancelacionSegCorr").html('');
				 jsValidaRequeridosConclusion();
			 }else{
				 $("form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr").val('');
				 $("form#cancelacionSeguimientoCorreccionForm #labelFecCancelacionSegCorr").html(mensajeValidaFecha(resultValFecha[1]));
			 }
		 }
	}


function generaResumenCancelacion(data){
	 $("form#formResumenSeguimiento #lbValFecAutoCancela").text(data.fecFechaEmiOf);
	 $("form#formResumenSeguimiento #lbValFolioVolaCancel").text(data.numFolioOficio);
	 
	 
	 for(var i = 0 ; i < data.motivosCancelacion.length ; i++){
		 if(data.motivosCancelacion[i][0]==data.idMotivoCancelacion){
			 $("form#formResumenSeguimiento #lbValMotivoCancel").text(''+data.motivosCancelacion[i][1]);
			 break;
		 	}
		
		}
	 
	 
	 
}