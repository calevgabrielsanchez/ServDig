var validaDerivDictamen = false;

/**
 * @author Enrique Duran Jimenez
 * @since 01/08/2012
 * Funcion que inicializa la pestaña de Derivar a Dictamen
 */
function inicializaDevDictamenSegCorreccion(data){
//	$("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").val('');
//	$("form#devDictamenSeguimientoCorreccionForm #numFolioOficio").val('');	
	
	$('form#devDictamenSeguimientoCorreccionForm input[type=text]').val('');
	$("form#devDictamenSeguimientoCorreccionForm #nombreFuncionario").val(data.nombreFuncionario);
	$("form#devDictamenSeguimientoCorreccionForm #cveSolCorr").val(data.cveSolCorr);
	inicializaFechasDevDictamenSegCorreccion();
	estiloCapturableDerivDictamen(true);
	$("form#devDictamenSeguimientoCorreccionForm #spnFechaDerivarDict").hide();		
	
	obtenerPeriodosSeleccionDictamen();
	generaResumenDerivacionDictamen(data);
}


/**
 * @author Enrique Duran Jimenez
 * @since 01/08/2012
 * Funcion inicializa el datepicker de la pantalla de Derivar a Dictamen del seguimiento de correccion
 */
function inicializaFechasDevDictamenSegCorreccion(){
	$("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onChange: function(dateText, inst) { 
			
	    }
	});

	$("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").datepicker('option', 'minDate', jsFechaMinSeguimiento);
	
}

function mensajeValidaFechaDictamenSegCorr(nombreFecha){
	var msgErrorDictamen="<label class='etiquetaError'>La fecha de derivaci&oacute;n no puede ser menor a la "+ nombreFecha + "</label>";
	return msgErrorDictamen;
}

function validaFechaDerivDictamen(valorFechaDictamen) {
	var fechaDerivacionDict = valorFechaDictamen;
	var resultValFecha;
	
	$("form#devDictamenSeguimientoCorreccionForm #labelfecAutDicSegCorr").html('');
	
	 if(fechaDerivacionDict != '' ) {
		 resultValFecha = validaListaFechas(fechaDerivacionDict, ARRAY_FECHAS_SEGCORR);
		 if(resultValFecha[0]){
			 $("form#devDictamenSeguimientoCorreccionForm #labelfecAutDicSegCorr").html('');
			 $("form#devDictamenSeguimientoCorreccionForm #spnFechaDerivarDict").show('fast');
		 }else{
			 $("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").val('');
			 $("form#devDictamenSeguimientoCorreccionForm #labelfecAutDicSegCorr").html(mensajeValidaFechaDictamenSegCorr(resultValFecha[1]));
			 $("form#devDictamenSeguimientoCorreccionForm #spnFechaDerivarDict").hide();
		 }
	 }	
}

function obtenerPeriodosSeleccionDictamen(){
	var anioInicioPeriodo = $("form#seguimientoCorreccionForm #fecFechaPeriodoIniSegHdn").val().substring(6);
	var anioFinPeriodo = $("form#seguimientoCorreccionForm #fecFechaPeriodoFinSegHdn").val().substring(6);
	
	   var myselect=document.getElementById("comboAnios");		
	   $('#comboAnios').empty();
		for(var i = anioInicioPeriodo ; i <= anioFinPeriodo ; i++){
			myselect.add(new Option(i, i));
		}
}

function estiloCapturableDerivDictamen(asignar){
	
	if (asignar){		
		$("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").addClass("red");
		$("form#devDictamenSeguimientoCorreccionForm #numFolioOficio").addClass("red");		
		$('form#devDictamenSeguimientoCorreccionForm #spnFechaDerivarDict').show('fast');
		$('form#devDictamenSeguimientoCorreccionForm input[type=text]').removeAttr("disabled");
		$('form#devDictamenSeguimientoCorreccionForm input[type=button]').removeAttr("disabled");
		$("form#devDictamenSeguimientoCorreccionForm #ejercicio").removeAttr("disabled");
	} else {
		$("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").removeClass("red");
		$("form#devDictamenSeguimientoCorreccionForm #numFolioOficio").removeClass("red");
		$('form#devDictamenSeguimientoCorreccionForm #spnFechaDerivarDict').hide();
		$('form#devDictamenSeguimientoCorreccionForm input[type=text]').prop("disabled", "disabled");
		$('form#devDictamenSeguimientoCorreccionForm input[type=button]').prop("disabled", "disabled");		
		$("form#devDictamenSeguimientoCorreccionForm #ejercicio").prop("disabled", "disabled");
		
	}	
}

function limpiaFechaDerivacionDict(){	
	$("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").val("");
	$("form#devDictamenSeguimientoCorreccionForm #labelfecAutDicSegCorr").html("");
	$("form#devDictamenSeguimientoCorreccionForm #spnFechaDerivarDict").hide();	
}

function validaFechaEmisionDerivDict () {
	var mensaje_errorFechaEmision="<label class='etiquetaError'>La fecha de derivacion no puede ser menor a la fecha de emision</label>";	

	var fechaDerivacionDict=$("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").val();	
	
	/*
TO DO
	*/
	
	if(fechaDerivacionDict.length>0){
	   $("form#devDictamenSeguimientoCorreccionForm #spnFechaDerivarDict").show('fast');	
	}
}

function validarAlfaNumerico(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
} 

function validaCampoReqDerivDictamen(forma, campo, etiqueta) {
	var mensaje_requerido = "<label class='etiquetaError'>Requerido</label>";
	var rutaForm = "form#" + forma + " #";
	var v_campo = rutaForm + campo;
	var v_label = rutaForm + etiqueta;

	if ($(v_campo).val() == '') {
		$(v_label).html(mensaje_requerido);
		validaDerivDictamen = false;
	}
}


function validaSelEjercicioDerivDictamen() {
	var mensaje_requerido = "<label class='etiquetaError'>Requerido</label>";
	if ($("form#devDictamenSeguimientoCorreccionForm #comboAnios").val()<1) {
		$("form#devDictamenSeguimientoCorreccionForm #labelEjercicio").html(mensaje_requerido);
		validaDerivDictamen = false;
	}
}

/**
 * Funcion donde se valida los campos requeridos para el tab  
 * derivar a dictamen.
 *  
 * @author Saúl Rosales Piedragil
 * @version 1.0.0
 */
function validaCamposDerivacionDict(){
	validaDerivDictamen = true;
	var nForma="devDictamenSeguimientoCorreccionForm";
	var fechaDerivacionDictValidar=$("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").val();
	var rpValido = false;
	
	// se inicializan los label
	$("form#devDictamenSeguimientoCorreccionForm #labelfecAutDicSegCorr").html('');
	$("form#devDictamenSeguimientoCorreccionForm #labelFolioDerivarSubdel").html('');	
	$("form#devDictamenSeguimientoCorreccionForm #labelEjercicio").html('');	
	
	validaCampoReqDerivDictamen(nForma, "numFolioOficio", "labelNumFolioOficio");
	validaCampoReqDerivDictamen(nForma, "fecAutDicSegCorr", "labelfecAutDicSegCorr");
	validaSelEjercicioDerivDictamen();
	if (fechaDerivacionDictValidar.length > 0){
		validaFechaEmisionDerivDict();
	}
	return validaDerivDictamen;
}


function procesaFormularioDerivacionDict() {	
	var cve_solicitud = $("form#devDictamenSeguimientoCorreccionForm #cveSolCorr").val();
	if (cve_solicitud.length == 0){
		alert ("No se se encontro la clave de solicitud");
		return false;
	}

	if (confirm("Los datos son correctos ?")) {
		
	  if(validaCamposDerivacionDict()){
	   var objForma = $("form#devDictamenSeguimientoCorreccionForm").toObject({mode:'first'});
	   objForma.cvePresentaCorr=cvePresentacion;
	   $.postJSON("correccion/derivdictamen/registrarDerivacionDictamen.do", objForma, function(data) {	
		   if(data != null){
			   estiloCapturableDerivDictamen(false);
			   alert(data.exito);
			   cveEstatusRecepcion=25;
			   ejecutaReglasValidacion(1);
			   $("form#formResumenSeguimiento #lbValFecAutoDerivDicta").text(data.fecFechaEmiOf);
			   $("form#formResumenSeguimiento #lbValFolioAvisoDerDicta").text(data.numFolioOficio);
		   }
		}).error(function(data){ 
			alert('Error al procesar los datos');
			validarSesionExpirada(data);
		}).complete(function(){		
			desbloquear();
			
		});	
	  }
	}
}


function generaResumenDerivacionDictamen(data){
	var sVarSeg = '{"cvePresentaCorr":"'+data.cvePresentaCorr+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	
	$.postJSON_Sync("correccion/derivdictamen/buscarDerivacionDictamen.do", clase, function(data) {
		
		if(data != null){
			$("form#formResumenSeguimiento #lbValFecAutoDerivDicta").text(data.fechaEmision);
			$("form#formResumenSeguimiento #lbValFolioAvisoDerDicta").text(data.numFolioOficio);
			
			$("form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr").val(data.fechaEmision);
			$("form#devDictamenSeguimientoCorreccionForm #numFolioOficio").val(data.numFolioOficio);
		}
		
		}
	);	
}