

var estatusActualizacionReact=0;
var datosValidosReactiv=true;
/**

 * @since 02/08/2012
 * Funcion que inicializa la pestaña de Reactivación
 */
function inicializaReactivacionSegCorreccion(datos){

	limpiaFormulario();
	$("form#reactivacionSeguimientoCorreccionForm #nombreFuncionario").val(datos.nombreFuncionario);
	$("form#reactivacionSeguimientoCorreccionForm #cveSolCorr").val(datos.cveSolCorr);
	$("form#reactivacionSeguimientoCorreccionForm #folioSolicitud").val(datos.nuFolio);
	estiloCapturableReactivacion(true);
	$("form#reactivacionSeguimientoCorreccionForm #botonReactivar").prop("disabled", "true");
	
	inicializaFechaSolReactivacion();
	inicializaFechaEnvioSol();
	inicializaFechaReactiva();
	
	// CAMBIAR
	var estatusSolicitud = "FISCALIZACION";
	
	
	if(estatusSolicitud=="FISCALIZACION"){
	 var cveSolCorr = $("form#reactivacionSeguimientoCorreccionForm #cveSolCorr").val();
	 var variable = '{"cveSolCorr":' + cveSolCorr + '}';		
	 var variableJson = jQuery.parseJSON(variable);
	 $.postJSON_Sync("correccion/derivfiscal/buscaDerivacionFiscalizacion.do", variableJson, function(data) {	
		 
		   if(data != null){
			   	generaResumenDeriFisca(data);
				if(data.cveRevDerivAFisca != null){
					$("form#reactivacionSeguimientoCorreccionForm #cveRevDerivAFisca").val(data.cveRevDerivAFisca);
				

				}
				if(data.fechaDeriva != null){
					$("form#reactivacionSeguimientoCorreccionForm #fechaDeriva").val(data.fechaDeriva);
				}
				if(data.fechaSolReactiva != null){
					$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val(data.fechaSolReactiva);
					$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").prop("disabled", "disabled");
					$("form#reactivacionSeguimientoCorreccionForm #spnfechaSolReactiva").hide();
					estatusActualizacionReact=1;
				}
				if(data.fechaEnvioSol != null){
					$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val(data.fechaEnvioSol);
					$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").prop("disabled", "disabled");
					$("form#reactivacionSeguimientoCorreccionForm #spnfechaEnvioSol").hide();
				}
				if(data.numOficioEnvio != null){
					$("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").val(data.numOficioEnvio);
					$("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").prop("disabled", "disabled");
					if(data.fechaEnvioSol != null){						
						estatusActualizacionReact=2;
					}
				}
				if(data.fechaReactiva != null){
					$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val(data.fechaReactiva);
					$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").prop("disabled", "disabled");
					$("form#reactivacionSeguimientoCorreccionForm #spnfechaReactiva").hide();	
				}
				if(data.numOficioReactiva != null){
					$("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").val(data.numOficioReactiva);
					$("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").prop("disabled", "disabled");
				}
				if(data.txObservaciones != null){
					$("form#reactivacionSeguimientoCorreccionForm #txObservacionesReactivaSegCorr").val(data.txObservaciones);
					$("form#reactivacionSeguimientoCorreccionForm #txObservacionesReactivaSegCorr").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #txObservacionesReactivaSegCorr").prop("disabled", "disabled");
				}
				
				if(data.numFolioOficio!=null){
					 $("form#devFiscalizacionSeguimientoCorreccionForm #numFolioOficio").val(data.numFolioOficio);
					 $("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").val(data.fechaDeriva);			
					 
				}
				
				
				
				validaListoParaReactivacion();
		   }
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){			
			desbloquear();
		});	
	}
	
}


function limpiaFormulario(){
	$("form#reactivacionSeguimientoCorreccionForm #fechaDeriva").val('');
	$("form#reactivacionSeguimientoCorreccionForm #cveRevDerivAFisca").val('');
	$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val('');
	$("form#reactivacionSeguimientoCorreccionForm #spnfechaSolReactiva").hide();	
	$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val('');
	$("form#reactivacionSeguimientoCorreccionForm #spnfechaEnvioSol").hide();	
	$("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").val('');
	$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val('');
	$("form#reactivacionSeguimientoCorreccionForm #spnfechaReactiva").hide();	
	$("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").val('');
	$("form#reactivacionSeguimientoCorreccionForm #txObservacionesReactivaSegCorr").val('');	
}




/**

 * Funcion inicializa el datepicker de la pantalla 
 */
function inicializaFechaSolReactivacion(){
	$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 	
			$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val("");
	    }
	});
	$( "form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$( "form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").datepicker('option', 'minDate', jsFechaMinSeguimiento);	
}

function inicializaFechaEnvioSol(){
	$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) {
			$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val("");
	    }
	});
	$( "form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$( "form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").datepicker('option', 'minDate', jsFechaMinSeguimiento);	
}

function inicializaFechaReactiva(){
	$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onChange: function(dateText, inst) { 			
	    }
	});
	$( "form#reactivacionSeguimientoCorreccionForm #fechaReactiva").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$( "form#reactivacionSeguimientoCorreccionForm #fechaReactiva").datepicker('option', 'minDate', jsFechaMinSeguimiento);	
}




function validaFechaSolReactiva() {
	var mensaje_errorFechaSolReactiva="<label class='etiquetaError'>La fecha de reactivaci&oacute;n no puede ser menor a la fecha de derivaci&oacute;n a fiscalizaci&oacute;n</label>";	
		
	var fechaDerivacionFisca=$("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").val();	
	var fechaSolReactivacion=$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val();	
	
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaSolReactiva").html("");
	if (fechaDerivacionFisca==null || fechaDerivacionFisca==""){
		alert ("Verifique que exista la fecha de derivaci\u00f3n a fiscalizaci\u00f3n");
		$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val("");
		datosValidosReactiv = false;
	} else if (fechaSolReactivacion!="" && !comparaFechas(fechaDerivacionFisca, fechaSolReactivacion, '-')){
		$("form#reactivacionSeguimientoCorreccionForm #labelfechaSolReactiva").html(mensaje_errorFechaSolReactiva);
		$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val("");
		$('#spnfechaSolReactiva').hide();
		datosValidosReactiv = false;
	}  else if(fechaSolReactivacion!=''){
		$('#spnfechaSolReactiva').show('fast');	
	}
}


function validarAlfaNumericoReactivacion(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmn\U00F1opqrstuvwxyzABCDEFGHIJKLMN\U00D1OPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
} 


function validaFechaEnvioSol() {
	var mensaje_errorFechaEnvioSol="<label class='etiquetaError'>La fecha de env&iacute;o no puede ser menor a la fecha de solicitud de reactivaci&oacute;n</label>";	
	
	var fechaSolicitud=$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val();	
	var fechaEnvioSolicitud=$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val();	
	
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaEnvioSol").html("");
	if (fechaSolicitud==""){
		alert ("Verifique que exista la fecha de solicitud");
		$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val("");
		datosValidosReactiv = false;
	} else if (fechaEnvioSolicitud!="" && !comparaFechas(fechaSolicitud, fechaEnvioSolicitud, '-')){
		$("form#reactivacionSeguimientoCorreccionForm #labelfechaEnvioSol").html(mensaje_errorFechaEnvioSol);
		$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val("");
		$('#spnfechaEnvioSol').hide();	
		datosValidosReactiv = false;
	}  else if(fechaEnvioSolicitud!=''){
		$('#spnfechaEnvioSol').show('fast');	
	}
}

function validaFechaReactiva() {
	var mensaje_errorFechaReactivacion="<label class='etiquetaError'>La fecha de reactivaci&oacute;n no puede ser menor a la fecha de env&iacute;o de solicitud</label>";	
	
	var fechaEnvioSolicitud=$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val();	
	var fechaReactivacion=$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val();	
	
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaReactiva").html("");
	if (fechaEnvioSolicitud==""){
		alert ("Verifique que exista la fecha de env\u00edo de solicitud");
		$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val("");
		datosValidosReactiv = false;
	} else if (fechaReactivacion!="" && !comparaFechas(fechaEnvioSolicitud, fechaReactivacion, '-')){
		$("form#reactivacionSeguimientoCorreccionForm #labelfechaReactiva").html(mensaje_errorFechaReactivacion);
		$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val("");
		$('#spnfechaReactiva').hide();	
		datosValidosReactiv = false;
	}  else if(fechaReactivacion!=''){
		$('#spnfechaReactiva').show('fast');	
	}
}

function estiloCapturableReactivacion(asignar){
	
	if (asignar){		
		$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").addClass("red");
		$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").addClass("red");
		$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").addClass("red");
		$("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").addClass("red");	
		$("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").addClass("red");		
		$("form#reactivacionSeguimientoCorreccionForm #txObservacionesReactivaSegCorr").addClass("red");

		$('form#reactivacionSeguimientoCorreccionForm input[type=text]').removeAttr("disabled");
		$("form#reactivacionSeguimientoCorreccionForm #botonGuardar").removeAttr("disabled");
	} else {
		$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").removeClass("red");
		$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").removeClass("red");
		$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").removeClass("red");
		$("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").removeClass("red");	
		$("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").removeClass("red");		
		$("form#reactivacionSeguimientoCorreccionForm #txObservacionesReactivaSegCorr").removeClass("red");	
		$('form#reactivacionSeguimientoCorreccionForm #spnfechaSolReactiva').hide();
		$('form#reactivacionSeguimientoCorreccionForm #spnfechaEnvioSol').hide();
		$('form#reactivacionSeguimientoCorreccionForm #spnfechaReactiva').hide();
		$('form#reactivacionSeguimientoCorreccionForm input[type=text]').prop("disabled", "disabled");
		$('form#reactivacionSeguimientoCorreccionForm input[type=button]').prop("disabled", "disabled");			
	}	
}

function reactivacionLimpiaFechaSolReactiva(){	
	$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val("");
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaSolReactiva").html("");
	$("form#reactivacionSeguimientoCorreccionForm #spnfechaSolReactiva").hide();	
}

function reactivacionLimpiaFechaEnvioSol(){	
	$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val("");
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaEnvioSol").html("");
	$("form#reactivacionSeguimientoCorreccionForm #spnfechaEnvioSol").hide();	
}

function reactivacionLimpiaFechaReactivacion(){	
	$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val("");
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaReactiva").html("");
	$("form#reactivacionSeguimientoCorreccionForm #spnfechaReactiva").hide();	
}



function validaCampoRequerido(forma, campo, etiqueta) {
	var mensaje_requerido = "<label class='etiquetaError'>Campo Requerido</label>";
	var rutaForm = "form#" + forma + " #";
	var v_campo = rutaForm + campo;
	var v_label = rutaForm + etiqueta;

	if ($(v_campo).val() == '') {
		$(v_label).html(mensaje_requerido);
		datosValidosReactiv = false;
	}
}



/**
 * Funcion donde se valida los campos requeridos para la  
 * actualizaci�n de datos de reactivacion
 *  
 * @author Sa&uacute;l Rosales Piedragil
 * @version 1.0.0
 */
function validaCamposActualizacion(){
	datosValidosReactiv = true;
	var nForma="reactivacionSeguimientoCorreccionForm";
	var fechaSolReact=$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val();
	var fechaEnvio=$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val();
	var fechaReactivacion=$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val();

	// se inicializan los label
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaSolReactiva").html('');
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaEnvioSol").html('');	
	$("form#reactivacionSeguimientoCorreccionForm #labelNumOficioEnvio").html('');	
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaReactiva").html('');	
	$("form#reactivacionSeguimientoCorreccionForm #labelNumOficioReactiva").html('');	
	if(estatusActualizacionReact==0){
	   validaCampoRequerido(nForma, "fechaSolReactiva", "labelfechaSolReactiva");
	   if(fechaSolReact.length>0){
		   validaFechaSolReactiva();
		}
		return datosValidosReactiv;
	}
	if(estatusActualizacionReact==1){
	  validaCampoRequerido(nForma, "fechaEnvioSol", "labelfechaEnvioSol");
	  validaCampoRequerido(nForma, "numOficioEnvio", "labelNumOficioEnvio");
	   if(fechaEnvio.length>0){
		   validaFechaEnvioSol();
		}
		return datosValidosReactiv;
	}
	if(estatusActualizacionReact==2){
	  validaCampoRequerido(nForma, "fechaReactiva", "labelfechaReactiva");
	  validaCampoRequerido(nForma, "numOficioReactiva", "labelNumOficioReactiva");
	   if(fechaReactivacion.length>0){
		   validaFechaReactiva();
		}
		return datosValidosReactiv;
	}
	if(fechaSolReact.length>0){
	   validaFechaSolReactiva();
	}
	if(fechaEnvio.length>0){
		validaFechaEnvioSol();
	}
	if(fechaReactivacion.length>0){
		validaFechaReactiva();
	}
	return datosValidosReactiv;
}

/**
 * Funcion donde se valida los campos requeridos para el tab  
 * reactivaci�n.
 *  
 * @author Sa�l Rosales Piedragil
 * @version 1.0.0
 */
function validaCamposReactivacion(){
	validaCamposReactiv = true;
	var nForma="reactivacionSeguimientoCorreccionForm";
	var fechaDerivacionFiscValidar=$("form#reactivacionSeguimientoCorreccionForm #fecDerivFisSegCorr").val();

	// se inicializan los label
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaSolReactiva").html('');
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaEnvioSol").html('');	
	$("form#reactivacionSeguimientoCorreccionForm #labelNumOficioEnvio").html('');	
	$("form#reactivacionSeguimientoCorreccionForm #labelfechaReactiva").html('');	
	$("form#reactivacionSeguimientoCorreccionForm #labelNumOficioReactiva").html('');	
	
	validaCampoRequerido(nForma, "fechaSolReactiva", "labelfechaSolReactiva");
	validaCampoRequerido(nForma, "fechaEnvioSol", "labelfechaEnvioSol");
	validaCampoRequerido(nForma, "numOficioEnvio", "labelNumOficioEnvio");
	validaCampoRequerido(nForma, "fechaReactiva", "labelfechaReactiva");
	validaCampoRequerido(nForma, "numOficioReactiva", "labelNumOficioReactiva");

	return validaCamposReactiv;
}

function validaListoParaReactivacion(){
	if( ($("form#reactivacionSeguimientoCorreccionForm #cveRevDerivAFisca").val().length>0)
		&&	($("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val().length>0)
		&&	($("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val().length>0)
		&&	($("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").val().length>0)
		&&	($("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val().length>0)
		&&	($("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").val().length>0) ){
		$("form#reactivacionSeguimientoCorreccionForm #botonReactivar").removeAttr("disabled");
	}
}


function guardarDatosReactivacion() {	
	var cve_derivacion = $("form#reactivacionSeguimientoCorreccionForm #cveRevDerivAFisca").val();
	if (cve_derivacion.length==null || cve_derivacion.length==0){
		alert ("No se encontró la clave de derivación");
		return false;
	}
    if(validaCamposActualizacion()){
	if (confirm("Est&acute; seguro que la informaci&oacute;n es correcta?")) {
	  var objForma = $("form#reactivacionSeguimientoCorreccionForm").toObject({mode:'first'});
	  $.postJSON("correccion/derivfiscal/actualizaDerivacionFiscalizacion.do", objForma, function(data) {	
		   if(data != null){

				if(data.cveRevDerivAFisca != null){
					$("form#reactivacionSeguimientoCorreccionForm #cveRevDerivAFisca").val(data.cveRevDerivAFisca);
				}
				if(data.fechaSolReactiva != null){
					$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val(data.fechaSolReactiva);
					$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").prop("disabled", "disabled");
					$("form#reactivacionSeguimientoCorreccionForm #spnfechaSolReactiva").hide();	
					estatusActualizacionReact=1;
				}
				if(data.fechaEnvioSol != null){
					$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val(data.fechaEnvioSol);
					$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").prop("disabled", "disabled");
					$("form#reactivacionSeguimientoCorreccionForm #spnfechaEnvioSol").hide();	
				}
				if(data.numOficioEnvio != null){
					$("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").val(data.numOficioEnvio);
					$("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").prop("disabled", "disabled");
					if(data.fechaEnvioSol != null){						
						estatusActualizacionReact=2;
					}
				}
				if(data.fechaReactiva != null){
					$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val(data.fechaReactiva);
					$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").prop("disabled", "disabled");
					$("form#reactivacionSeguimientoCorreccionForm #spnfechaReactiva").hide();	
				}
				if(data.numOficioReactiva != null){
					$("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").val(data.numOficioReactiva);
					$("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").prop("disabled", "disabled");
				}
				if(data.txObservaciones != null){
					$("form#reactivacionSeguimientoCorreccionForm #txObservacionesReactivaSegCorr").val(data.txObservaciones);
					$("form#reactivacionSeguimientoCorreccionForm #txObservacionesReactivaSegCorr").removeClass("red");	
					$("form#reactivacionSeguimientoCorreccionForm #txObservacionesReactivaSegCorr").prop("disabled", "disabled");
				}
				validaListoParaReactivacion();
		   }
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){			
			desbloquear();
		});	
    }
    }
}



function reactivar() {	
	var cve_derivacion = $("form#reactivacionSeguimientoCorreccionForm #cveRevDerivAFisca").val();
	if (cve_derivacion.length==null || cve_derivacion.length==0){
		alert ("No se encontro la clave de derivacion");
		return false;
	}
	var mensajeConfirmar = "Est&aacute; seguro que desea reactivar el folio de la correcci&oacute;n " +
	        $("form#reactivacionSeguimientoCorreccionForm #folioSolicitud").val() + "?";
	if(validarReactivacion()){
		if (confirm(mensajeConfirmar)) {
			  $("form#reactivacionSeguimientoCorreccionForm #botonGuardar").removeAttr("disabled");
			   var objForma = $("form#reactivacionSeguimientoCorreccionForm").toObject({mode:'first'});
			   $.postJSON("correccion/derivfiscal/registrarReactivacion.do", objForma, function(data) {	
				   if(data != null){
						if(data.error!=null && data.error.length>0){
							alert('Error al procesar:' + data.error);
						}else{
							$("form#reactivacionSeguimientoCorreccionForm #botonGuardar").prop("disabled", "true");
							$("form#reactivacionSeguimientoCorreccionForm #botonReactivar").prop("disabled", "true");
							ejecutaReglasValidacion(1);
							
//							setTabDesHabilitado('seguimientoCorreccionDerivarFis');		
		//
//							setTabHabilitado('seguimientoCorreccionCedRevision');
//							setTabHabilitado('seguimientoCorreccionCedValidacion');
//							setTabHabilitado('seguimientoCorreccionReqDocumentacion');
//							setTabHabilitado('seguimientoCorreccionOfiResultados');
//							setTabHabilitado('seguimientoCorreccionCancelacion');
//							setTabHabilitado('seguimientoCorreccionConclusion');
//							setTabHabilitado('seguimientoCorreccionResumen');
							
							alert('Los datos se guardaron correctamente');
						}
				   }
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){					
				//	alert('Los datos se guardaron correctamente');
					desbloquear();
				});	
			}
	}else{
		alert("Faltan datos por capturar");
	}

}

function validarReactivacion(){
	var flag=true;
	if($("form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva").val()==""){
		flag=false;
	}
	
	if($("form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol").val()==""){
		flag=false;
	}
	
	if($("form#reactivacionSeguimientoCorreccionForm #fechaReactiva").val()==""){
		flag=false;
	}
	
	if($("form#reactivacionSeguimientoCorreccionForm #numOficioEnvio").val()==""){
		flag=false;
	}
	
	if($("form#reactivacionSeguimientoCorreccionForm #numOficioReactiva").val()==""){
		flag=false;
	}
	
	return flag;
}


function generaResumenDeriFisca(data){
	 $("form#formResumenSeguimiento #lbValFecDerFisca").text(data.fechaDeriva);
	 $("form#formResumenSeguimiento #lbValReferenciaFizca").text(data.numFolioOficio);
	
}