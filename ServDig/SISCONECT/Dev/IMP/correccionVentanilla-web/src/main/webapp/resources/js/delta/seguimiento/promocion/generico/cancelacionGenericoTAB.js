
/**
 * Funcion donde se inicializa todo el comportamiento y/o funcionalidad que se deberia de cargar en el Ready de Jquery , 
 * se separo del Ready principal para facilitar la carga en diferentes momentos y no sea tan pesada al iniciar la pantalla
 * principal de consulta de promociones. 
 * funcionalidad inicial para la pestaña de cancelacion.
 * @author Gerardo Salazar Vega
 * @version 1.0.1
 */

var jsFechaCancelacionGenCGT= "form#cancelacionGenericoTabForm #fechaCancelacionCGT";
var jsLabelFechaCancelacionGenCGT = "form#cancelacionGenericoTabForm #labelFechaCancelacionCGT";
var jsMotivoCancelacionComboCGT= "form#cancelacionGenericoTabForm #cancelacionGenericoTabVO\\.cveMotivoCancelacion";
var jsFuncionarioAutorizaComboCGT= "form#cancelacionGenericoTabForm #funcionarioAutorizaCGT";
var jsFuncionarioRegistraHidCGT = "form#cancelacionGenericoTabForm #hidFuncionarioRegistra";

	
function initTabCancelacionGenerico(){
	//alert ("max="+jsFechaMaxSeguimiento + ", min="+ jsFechaMinSeguimiento);
	limpiarFormulario("#cancelacionGenericoTabForm");
	
	$(jsFechaCancelacionGenCGT).datepicker( fechaSeguimiento());
//	$(jsFechaCancelacionGenCGT).datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
//	$(jsFechaCancelacionGenCGT).datepicker('option', 'minDate', jsFechaMinSeguimiento);

	// Habilita campos de captura 
	$('form#cancelacionGenericoTabForm input[type=text]').removeAttr("disabled");
	$('form#cancelacionGenericoTabForm input[type=button]').removeAttr("disabled");
	
	ponerEstiloCapturableCancelaGenerico(true);
	habilitaCombosCancelacionCGT();
	$("form#cancelacionGenericoTabForm #labelReferenciaCancelacionCGT").html("");
	$("form#cancelacionGenericoTabForm #labelFuncionarioAutorizaCGT").html("");
	$("form#cancelacionGenericoTabForm #labelCveMotivoCancelacionCGT").html("");
	$("form#cancelacionGenericoTabForm #labelFuncionarioRegistraCGT").html($(jsFuncionarioRegistraHidCGT).val());
	$(jsLabelFechaCancelacionGenCGT).html("");
	$(jsFechaCancelacionGenCGT).val("");
	$("form#cancelacionGenericoTabForm #referenciaCancelacionCGT").val("");
	$('#spnFechaCancelacionCGT').hide();
	cargaFuncionarioAutoriza();
	$(jsMotivoCancelacionComboCGT).val("-1");
}


function ponerEstiloCapturableCancelaGenerico(asignar){
	if (asignar){
		$(jsFechaCancelacionGenCGT).addClass("red");
		$("form#cancelacionGenericoTabForm #referenciaCancelacionCGT").addClass("red");
		$(jsFuncionarioAutorizaComboCGT).addClass("red");
		$(jsMotivoCancelacionComboCGT).addClass("red");
	} else {
		$(jsFechaCancelacionGenCGT).removeClass("red");
		$("form#cancelacionGenericoTabForm #referenciaCancelacionCGT").removeClass("red");
		$(jsFuncionarioAutorizaComboCGT).removeClass("red");
		$(jsMotivoCancelacionComboCGT).removeClass("red");
	}
}


function limpiaFechaCancelacionGenerico(){	
	$(jsFechaCancelacionGenCGT).val("");
	$('#spnFechaCancelacionCGT').hide();	
}


function validaCamposCancelacionGen(){

	var regresaCGT = true;
	var mensaje_requerido="<label class='etiquetaError'>Campo Requerido</label>";
	var mensaje_errorFecha="<label class='etiquetaError'>La fecha de inicio no puede ser mayor a la fecha fin de periodo</label>";
	
	// se inicializan los label
	$("form#cancelacionGenericoTabForm #labelReferenciaCancelacionCGT").html("");
	$("form#cancelacionGenericoTabForm #labelFuncionarioAutorizaCGT").html("");
	$("form#cancelacionGenericoTabForm #labelCveMotivoCancelacionCGT").html("");
	$(jsLabelFechaCancelacionGenCGT).html('');
	
	if($("form#cancelacionGenericoTabForm #referenciaCancelacionCGT").val() == ''){
		$("form#cancelacionGenericoTabForm #labelReferenciaCancelacionCGT").html(mensaje_requerido);
		regresaCGT=false;
	}
	
	if($(jsFechaCancelacionGenCGT).val() == ""){
		$(jsLabelFechaCancelacionGenCGT).html(mensaje_requerido);
		regresaCGT=false;
	} else {
		validaFechaCancelacionGenerico();
	}	
	// Seccion para validar los combos
	var indexMotivoRechazo = $(jsMotivoCancelacionComboCGT).val();
	var indexFuncionario = $(jsFuncionarioAutorizaComboCGT).val();
	if (indexFuncionario == -1){
		$("form#cancelacionGenericoTabForm #labelFuncionarioAutorizaCGT").html(mensaje_requerido);
		regresaCGT=false;
	}
	
	if (indexMotivoRechazo == -1){
		$("form#cancelacionGenericoTabForm #labelCveMotivoCancelacionCGT").html(mensaje_requerido);
		regresaCGT=false;
	}	
	//alert ("validacion campos=" + regresaCGT)
	return regresaCGT;
}

function validaFechaCancelacionGenerico (){
	var jsFechaEmisionCancelacionCan= $("form#cancelacionGenericoTabForm #fechaEmisionOficioGenerico").val();
	var jsFechaNotifCancelacionCan= $("form#cancelacionGenericoTabForm #fechaNotificacionOficioGenerico").val();
	var jsFechaCancelacionCGT= $("form#cancelacionGenericoTabForm #fechaCancelacionCGT").val();
	var mensajeErrorFechaNotif="<label class='etiquetaError'>La fecha de cancelaci&oacute;n no puede ser menor a a fecha de notificaci&oacute;n</label>";
	var mensajeErrorFechaEmision="<label class='etiquetaError'>La fecha de cancelaci&oacute;n no puede ser menor a a fecha de oficio de promoci&oacute;n</label>";
	
	$(jsLabelFechaCancelacionGenCGT).html("");

	if (jsFechaNotifCancelacionCan.length>0) {
		if (!comparaFechas(jsFechaNotifCancelacionCan, jsFechaCancelacionCGT, '-')){
			$(jsLabelFechaCancelacionGenCGT).html(mensajeErrorFechaNotif);
			$(jsFechaCancelacionGenCGT).val("");
			$('#spnFechaCancelacionCGT').hide();
			regresaCGT = false;
		}
	} else if (!comparaFechas(jsFechaEmisionCancelacionCan, jsFechaCancelacionCGT, '-')) {
		$(jsLabelFechaCancelacionGenCGT).html(mensajeErrorFechaEmision);	
		$(jsFechaCancelacionGenCGT).val("");
		$('#spnFechaCancelacionCGT').hide();
		regresaCGT = false;
	} else {
		$('#spnFechaCancelacionCGT').show('fast');
	}	
}

function procesaFormularioCancelacion(funcionValidacion) {
	
	// Esta seguro que quiere cancelar la promoción
	// La promoción ha sido cancelada
	FORMA_ACTUAL = "cancelacionGenericoTabForm";
	var cve_promocion = $("form#cancelacionGenericoTabForm #cvePromocion").val();
	if (cve_promocion.length == 0){
		alert ("No se se encontr\u00f3 la clave de promoci\u00f3n");
		return false;
	}
	
	document.forms["cancelacionGenericoTabForm"].action = jsContextoPromocion+"seguimiento/generico/cancelacionGenerica.do";
	if (confirm("Est\u00e1 seguro que quiere cancelar la promoci\u00f3n ?")){
		if (validaCancelacionFecha() && procesaFormulario(funcionValidacion)) {
			 alert("La promoci\u00f3n ha sido cancelada");
			 deshabilitaCamposCancelacionGenerica();
			 $('form#segimientoConstruccionForm #regPatronalSegConstruccion').prop('disabled','disabled');
			 $('form#segimientoConstruccionForm #btnValidarRegPatronal').prop('disabled','disabled');
			try {
				//recupera el nombre de la funcion generica
				eval($("form#cancelacionGenericoTabForm #functionAuxCancelacion").val());	
			} catch (e) {

			}
			aplicaReglasSaticB();
			reglasSaticA();
			$('#btnValidarRegPatronal').prop('disabled','disabled');
		}
	}
}


function validaCancelacionFecha(){
	if(($('#fechaNotificaSaticb').val()!=undefined && $('#fechaNotificaSaticb').val()!='' && !$('#fechaNotificaSaticb').is('[disabled]'))||($('#fechaNotificacionSaticaSeg').val()!=undefined && $('#fechaNotificacionSaticaSeg').val()!='' && !$('#fechaNotificacionSaticaSeg').is('[disabled]'))){
		alert("Favor de guardar primero la fecha de notificacion");
		return false;
	}else if($('#razonSocialSatica').is(':visible')  && $('#razonSocialSatica').text()==''){
		alert("Debe validar primero el registro patronal");
	}else{	
		return true;
	}
	
}
function cargaFuncionarioAutoriza(){	
	$.postJSON(jsContextoPromocion+"seguimiento/generico/consultaFuncionarioAutoriza.do", null, function(data) {		
		var comboFuncAutCanGen=document.getElementById("funcionarioAutorizaCGT");
		if (comboFuncAutCanGen != null) {
			comboFuncAutCanGen.options.length = 1;
			// alert ("data.length="+data.length);			
			for(var i = 0 ; i < data.length ; i++){
				comboFuncAutCanGen.add(new Option(data[i][1], data[i][0]));
			}
		}
	
	});
}

function deshabilitaCamposCancelacionGenerica(){
	$('form#cancelacionGenericoTabForm input[type=text]').prop('disabled','disabled');
	
	$('#regPatronSatica').prop('disabled','disabled');
	$('#btnValidaRegPatronSaticaMain').prop('disabled','disabled');
	
	
	$('form#cancelacionGenericoTabForm input[type=text]').removeClass("red");
	$('form#cancelacionGenericoTabForm input[type=button]').prop('disabled','disabled');		 
	ponerEstiloCapturableCancelaGenerico(false);
	deshabilitaCombosCancelacionCGT();
	$('#spnFechaCancelacionCGT').hide();			 	
}

function deshabilitaCombosCancelacionCGT(){
	$(jsMotivoCancelacionComboCGT).find('option').each(function() {$(this).prop("disabled", "disabled") });
	$(jsFuncionarioAutorizaComboCGT).find('option').each(function() {$(this).prop("disabled", "disabled") });	
}

function habilitaCombosCancelacionCGT(){
	$(jsMotivoCancelacionComboCGT).find('option').each(function() {$(this).removeAttr("disabled") });
	$(jsFuncionarioAutorizaComboCGT).find('option').each(function() {$(this).removeAttr("disabled") });	
}