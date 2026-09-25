var validaDerivFiscGen = false;
/**
 * @author Enrique Duran Jimenez
 * @since 02/08/2012
 * Funcion que inicializa la pestaña de Derivar a Dictamen
 */
function inicializaDevFiscalizacionSegCorreccion(data){

	$("form#devFiscalizacionSeguimientoCorreccionForm #numFolioOficio").val('');
	$("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").val('');
	$("form#devFiscalizacionSeguimientoCorreccionForm #nombreFuncionario").val(data.nombreFuncionario);
	$("form#devFiscalizacionSeguimientoCorreccionForm #cveSolCorr").val(data.cveSolCorr);
	$("form#devFiscalizacionSeguimientoCorreccionForm #cvePresentaCorr").val(data.cvePresentaCorr);
	
	estiloCapturableDerivFiscalizacion(true);
	$("form#devFiscalizacionSeguimientoCorreccionForm #spnfecDerivFisSegCorr").hide();		
	inicializaFechasDevFiscalizacionSegCorreccion();
	
}

function validarAlfaNumerico(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
} 



/**
 * @author Enrique Duran Jimenez
 * @since 02/08/2012
 * Funcion inicializa el datepicker de la pantalla de Derivar a Dictamen del seguimiento de correccion
 */
function inicializaFechasDevFiscalizacionSegCorreccion(){
	$("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onchange: function(dateText, inst) { 
			
	    }
	});

	$( "form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$( "form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").datepicker('option', 'minDate', $('form#reqDocumentacionSeguimientoCorreccionForm #fechaElaboraPresenHdn').val());
	
}




function estiloCapturableDerivFiscalizacion(asignar){
	
	if (asignar){		
		$("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").addClass("red");
		$("form#devFiscalizacionSeguimientoCorreccionForm #numFolioOficio").addClass("red");		
		$('form#devFiscalizacionSeguimientoCorreccionForm #spnfecDerivFisSegCorr').show('fast');
		$('form#devFiscalizacionSeguimientoCorreccionForm input[type=text]').removeAttr("disabled");
		$('form#devFiscalizacionSeguimientoCorreccionForm input[type=button]').removeAttr("disabled");
	} else {
		$("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").removeClass("red");
		$("form#devFiscalizacionSeguimientoCorreccionForm #numFolioOficio").removeClass("red");		
		$('form#devFiscalizacionSeguimientoCorreccionForm #spnfecDerivFisSegCorr').hide();
		$('form#devFiscalizacionSeguimientoCorreccionForm input[type=text]').prop("disabled", "disabled");
		$('form#devFiscalizacionSeguimientoCorreccionForm input[type=button]').prop("disabled", "disabled");	
		
	}	
}

function limpiafecDerivFisSegCorr(){	
	$("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").val("");
	$("form#devFiscalizacionSeguimientoCorreccionForm #labelfecDerivFisSegCorr").html("");
	$("form#devFiscalizacionSeguimientoCorreccionForm #spnfecDerivFisSegCorr").hide();	
}

function mensajeValidaFechaDFisSegCorr(nombreFecha){
	var msgErrorCancela="<label class='etiquetaError'>La fecha de derivaci&oacute;n no puede ser menor a la "+ nombreFecha + "</label>";
	return msgErrorCancela;
}

function validaFechaDerivacionAFisc () {
	var fechaDerivacionFisc=$("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").val();	
	var resultValFecha;
	
	$("form#devFiscalizacionSeguimientoCorreccionForm #labelfecDerivFisSegCorr").html("");
	
	if (fechaDerivacionFisc!=""){
		 resultValFecha = validaListaFechas(fechaDerivacionFisc, ARRAY_FECHAS_SEGCORR);
		 if(!resultValFecha[0]){
			 $("form#devFiscalizacionSeguimientoCorreccionForm #labelfecDerivFisSegCorr").html(mensajeValidaFechaDFisSegCorr(resultValFecha[1]));
			 $("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").val("");
			 $('#spnfecDerivFisSegCorr').hide();
			 validaDerivFiscGen = false;
		 } else {
			 $('#spnfecDerivFisSegCorr').show('fast');
		 }
	} else {
		validaDerivFiscGen = false;
	} 
}

function validaCampoReqDerivFisc(forma, campo, etiqueta) {
	var mensaje_requerido = "<label class='etiquetaError'>Campo Requerido</label>";
	var rutaForm = "form#" + forma + " #";
	var v_campo = rutaForm + campo;
	var v_label = rutaForm + etiqueta;

	if ($(v_campo).val() == '') {
		$(v_label).html(mensaje_requerido);
		validaDerivFiscGen = false;
	}
}

/**
 * Funcion donde se valida los campos requeridos para el tab  
 * derivar a dictamen.
 *  
 * @author Saúl Rosales Piedragil
 * @version 1.0.0
 */
function validaCamposDerivacionFiscal(){
	 validaDerivFiscGen = true;
	var nForma="devFiscalizacionSeguimientoCorreccionForm";
	var rpValido = false;
	
	
	// se inicializan los label
	$("form#devFiscalizacionSeguimientoCorreccionForm #labelfecDerivFisSegCorr").html('');
	$("form#devFiscalizacionSeguimientoCorreccionForm #labelNumFolioOficio").html('');	

	validaFechaDerivacionAFisc();
	validaCampoReqDerivFisc(nForma, "numFolioOficio", "labelNumFolioOficio");
	validaCampoReqDerivFisc(nForma, "fecDerivFisSegCorr", "labelfecDerivFisSegCorr");

	return validaDerivFiscGen;
}


function procesaFormularioDerivacionfiscal(funcionValidacion) {
	FORMA_ACTUAL = "devFiscalizacionSeguimientoCorreccionForm;";
	
	var cve_solicitud = $("form#devFiscalizacionSeguimientoCorreccionForm #cveSolCorr").val();
	if (cve_solicitud.length == 0){
		alert ("No se se encontro la clave de solicitud");
		return false;
	}
	document.forms["devFiscalizacionSeguimientoCorreccionForm"].action = getAppContextParaJS() +"/seguimiento/correccion/derivfiscal/registrarDerivacionFiscalizacion.do";
	if (confirm("Esta seguro que la informacion es correcta?")) {
	
		if (procesaFormulario(funcionValidacion)) {	
			estiloCapturableDerivFiscalizacion(false);			
			cveEstatusRecepcion=24;//estatus de derivacion a fisca			
			ejecutaReglasValidacion(1);
			
//			setTabDesHabilitado('seguimientoCorreccionCancelacion');
//			setTabDesHabilitado('seguimientoCorreccionDerivarDic');
//			setTabDesHabilitado('seguimientoCorreccionDerivarFis');
//			setTabDesHabilitado('seguimientoCorreccionDerivarSub');
//			setTabDesHabilitado('seguimientoCorreccionOfiResultados');
//			setTabDesHabilitado('seguimientoCorreccionCedRevision');
//			setTabDesHabilitado('seguimientoCorreccionReqDocumentacion');
//			setTabDesHabilitado('seguimientoCorreccionCedValidacion');
//			
//			setTabHabilitado('seguimientoCorreccionReactivar');
			alert("La informaci\u00f3n ha sido guardada");
			 $("form#formResumenSeguimiento #lbValFecDerFisca").text($("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").val());
			 $("form#formResumenSeguimiento #lbValReferenciaFizca").text($("form#devFiscalizacionSeguimientoCorreccionForm #numFolioOficio").val());
			 
		}
	}
}
