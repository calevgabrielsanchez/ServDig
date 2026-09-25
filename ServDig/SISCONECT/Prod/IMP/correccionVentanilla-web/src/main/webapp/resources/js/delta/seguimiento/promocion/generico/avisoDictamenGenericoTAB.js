
var jsfechaEmisionDictamenGenerico = "form#autAviDictamenGenericoTabForm #fechaEmisionOficioDictamenGenerico";
var jsfechaNotificacionDictamenGenerico = "form#autAviDictamenGenericoTabForm #fechaNotificacionOficioDictamenGenerico";
var jsfechaAvisoDictamenGenerico = "form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab";
var jsLabelfechaAvisoDictamenGenerico = "form#autAviDictamenGenericoTabForm #labelFecAvisoDictGenericoTab";
var jsLabelFecPeriodoDictGenericoTab = "form#autAviDictamenGenericoTabForm #labelFecPeriodoAvisoDictGenericoTab"
var jsSpanFecAvisoDictGenericoTab  = "form#autAviDictamenGenericoTabForm #spnFecAvisoDictGenericoTab";
var jsSpanFecPeriodoDictGenericoTab  = "form#autAviDictamenGenericoTabForm #spnFecPerDictGenericoTab";
var jsFuncionarioRegistraHidADGT ="form#autAviDictamenGenericoTabForm #hidFuncionarioRegistra";

/**
 * Funcion donde se inicializa todo el comportamiento y/o funcionalidad que se deberia de cargar en el Ready de Jquery , 
 * se separo del Ready principal para facilitar la carga en diferentes momentos y no sea tan pesada al iniciar la pantalla
 * principal de consulta de promociones. 
 * @version 1.0.1
 */
function initTabAvisoDictamenGenerico(){
	limpiarFormulario("#autAviDictamenGenericoTabForm");
	var fechaSeguimiento = $("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").val();
	
	$("form#autAviDictamenGenericoTabForm #labelFuncionarioRegistraADGT").html($(jsFuncionarioRegistraHidADGT).val());
	$( "form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").datepicker( { dateFormat: 'dd-mm-yy' });
	$( "form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").datepicker( { dateFormat: 'dd-mm-yy' });
	$( "form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").datepicker( { dateFormat: 'dd-mm-yy' });
	$("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").datepicker('option', 'minDate', jsFechaMinSeguimiento);
	$("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").datepicker('option', 'beforeShowDay', null);
	$("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").datepicker('option', 'beforeShowDay', null);
	

	//ocultar boton de borrar fecha
	$(jsSpanFecAvisoDictGenericoTab).hide();
	$(jsSpanFecPeriodoDictGenericoTab).hide();	
	$('form#autAviDictamenGenericoTabForm input[type=text]').removeAttr("disabled");
	$('form#autAviDictamenGenericoTabForm input[type=button]').removeAttr("disabled");
	//estilos 
	ponerEstiloCapturableDictamenGenerico(true);
}

function ponerEstiloCapturableDictamenGenerico(asignar){
	if (asignar){
		$("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").addClass("red");
		$("form#autAviDictamenGenericoTabForm #numAvisoDictGenericoTab").addClass("red");
		$("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").addClass("red");
		$("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").addClass("red");
	} else {
		$("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").removeClass("red");
		$("form#autAviDictamenGenericoTabForm #numAvisoDictGenericoTab").removeClass("red");
		$("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").removeClass("red");
		$("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").removeClass("red");		
	}
}

function limpiaFechaAutorizacionAviso(){	
	$("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").val("");
	$(jsLabelfechaAvisoDictamenGenerico).html("");
	$(jsSpanFecAvisoDictGenericoTab).hide();	
}

function limpiaFechasPeriodoDictamenGenerico(){	
	$("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").val("");
	$("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").val("");
	$(jsLabelFecPeriodoDictGenericoTab).html("");
	$(jsSpanFecPeriodoDictGenericoTab).hide();
}

function validaFechaDictamenGenerico (){
	
	var jsFechaEmisionDicGT= $(jsfechaEmisionDictamenGenerico).val();
	var jsFechaNotifDicGT= $(jsfechaNotificacionDictamenGenerico).val();
	var jsFechaDictamenGT= $(jsfechaAvisoDictamenGenerico).val();
	var mensajeErrorFechaNotifDGT="<label class='etiquetaError'>La fecha del Aviso de Dictamen no puede ser menor a la fecha de notificaci&oacute;n</label>";
	var mensajeErrorFechaEmisionDGT="<label class='etiquetaError'>La fecha de Aviso de Dictamen no puede ser menor a la fecha de oficio de promoci&oacute;n</label>";
	
	$(jsLabelfechaAvisoDictamenGenerico).html("");
	$("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").val("");
	$("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").val("");
	var fechaSeguimiento = $("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").val();
	$("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").datepicker('option', 'maxDate', fechaSeguimiento);
	$("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").datepicker('option', 'maxDate', fechaSeguimiento);
	
	if (!comparaFechas(jsFechaNotifDicGT, jsFechaDictamenGT, '-')){
		$(jsLabelfechaAvisoDictamenGenerico).html(mensajeErrorFechaNotifDGT);
		$(jsfechaAvisoDictamenGenerico).val("");
		$(jsSpanFecAvisoDictGenericoTab).hide();
//		regresaCGT = false;
	} else {
		$(jsSpanFecAvisoDictGenericoTab).show('fast');
	}
}

function validaCamposDictamenGenerico(){

	var regresa = true;
	var mensaje_requerido="<label class='etiquetaError'>Campo Requerido</label>";
	var mensaje_errorFecha="<label class='etiquetaError'>La fecha de inicio no puede ser mayor a la fecha fin de periodo</label>";
	var fechaIni=$("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").val();
	var fechaFin=$("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").val();
	
	// se inicializan los label
	$("form#autAviDictamenGenericoTabForm #labelFecAvisoDictGenericoTab").html('');
	$("form#autAviDictamenGenericoTabForm #labelnumAvisoDictGenericoTab").html('');	
	$(jsLabelFecPeriodoDictGenericoTab).html('');	
	
	if($("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").val() == ''){
		$("form#autAviDictamenGenericoTabForm #labelFecAvisoDictGenericoTab").html(mensaje_requerido);
		regresa=false;
	}
	if($("form#autAviDictamenGenericoTabForm #numAvisoDictGenericoTab").val() == ''){
		$("form#autAviDictamenGenericoTabForm #labelnumAvisoDictGenericoTab").html(mensaje_requerido);
		regresa=false;
	}	
	if($("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").val() == ''){
		$(jsLabelFecPeriodoDictGenericoTab).html(mensaje_requerido);
		regresa=false;
	}	
	if($("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").val() == ''){
		$(jsLabelFecPeriodoDictGenericoTab).html(mensaje_requerido);
		regresa=false;
	}	
//	if (fechaIni!='' && fechaFin!=''){
//		validaFechasPeriodoDicGT ();
//	}

	return regresa;
}


function validaFechasPeriodoDicGT (paramFechaPeriodo){
	var mensaje_errorFechaPeriodoIni="<label class='etiquetaError'>La fecha de inicio no puede ser mayor a la fecha fin de periodo</label>";
	var mensaje_errorFechaPeriodoFin="<label class='etiquetaError'>La fecha de fin no puede ser menor a la fecha de inicio de periodo</label>";
	var fechaIni=$("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").val();
	var fechaFin=$("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").val();
	var nomFecIni= "fecIniPeriodoDictGenericoTab";
	var nomFecFin= "fecFinPeriodoDictGenericoTab";
	
	$(jsLabelFecPeriodoDictGenericoTab).html("");	
	if (fechaIni!='' && fechaFin!='' && !comparaFechas(fechaIni, fechaFin, '-')){		
		if (paramFechaPeriodo == nomFecIni) {
			$(jsLabelFecPeriodoDictGenericoTab).html(mensaje_errorFechaPeriodoIni);
			$("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").val("");
		} else {
			$(jsLabelFecPeriodoDictGenericoTab).html(mensaje_errorFechaPeriodoFin);
			$("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").val("");
		}
		
	} else if (fechaIni!='' || fechaFin!='') {
		$(jsSpanFecPeriodoDictGenericoTab).show("fast");
	}
}

function procesaFormularioDictamenGenerico(funcionValidacion){
	FORMA_ACTUAL = "autAviDictamenGenericoTabForm" ;
	var cve_promocion = $("form#autAviDictamenGenericoTabForm #cvePromocion").val();
	if (cve_promocion.length == 0){
		alert ("No se se encontro la clave de promocion");
		return false;
	}
	document.forms["autAviDictamenGenericoTabForm"].action = jsContextoPromocion+"seguimiento/generico/guardaAvisoDictamen.do";
	if (procesaFormulario(funcionValidacion)){
		alert ("La informaci\u00f3n ha sido guardada");
		$('form#autAviDictamenGenericoTabForm input[type=text]').prop("disabled", "disabled");
		$('form#autAviDictamenGenericoTabForm input[type=button]').prop("disabled", "disabled");	
		$("#regPatronalSegConstruccion").prop("disabled", "disabled");
		ponerEstiloCapturableDictamenGenerico(false);
		$(jsSpanFecAvisoDictGenericoTab).hide();
		$(jsSpanFecPeriodoDictGenericoTab).hide();	
		$('form#segimientoConstruccionForm #regPatronalSegConstruccion').prop('disabled','disabled');
		$('form#segimientoConstruccionForm #btnValidarRegPatronal').prop('disabled','disabled');
		try {
			//recupera el nombre de la funcion generica
			eval($("form#autAviDictamenGenericoTabForm #functionAuxAutAvisoDict").val());	
		} catch (e) {

		}
		aplicaReglasSaticB();
		$("#spnFecNotSaticb").hide();
	}
}
